package sample.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MovieModel {
    private ArrayList<Movie> movieList = new ArrayList();
    private int counter = 1;

    private final String URL_MOVIES = "file:kino.json";
    private final String URL_SERIALS = "file:serial.json";

    Class<? extends Movie> movieFilter = Movie.class;

    public interface DataChangedListener {
        void dataChanged(ArrayList<Movie> movieList);
    }

    private ArrayList<DataChangedListener> dataChangedListeners = new ArrayList<>();

    public void addDataChangedListener(DataChangedListener listener) {
        this.dataChangedListeners.add(listener);
    }

    public void load() {
        movieList.clear();

        try {
            ArrayList<JSONObject> data = JSONModel.load(URL_MOVIES, "movies");

            if (!data.isEmpty()) {
                for (JSONObject el : data) {
                    this.add(JSONModel.getDataFromJson(el), false);
                }
            }

            data = JSONModel.load(URL_SERIALS, "tv-series");

            if (!data.isEmpty()) {
                for (JSONObject el : data) {
                    this.add(JSONModel.getDataFromJson(el), false);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*this.add(new Film("Movie 1", 5.00, 55.30, 3, Film.Type.documentary), false);
        this.add(new Serial("Serial 1",8.00, 10, 2), false);*/
        this.add(new Telecast("Telecast 1", 7.00, 50.34, "20:30 - 21:30"), false);

        this.emitDataChanged();
    }

    public void add(Movie movie, boolean emit) {
        movie.id = counter;
        counter += 1;

        this.movieList.add(movie);

        if (emit) {
            this.emitDataChanged();
        }
    }

    public void add(Movie movie) {
        add(movie, true);
    }

    public void delete(int id) {
        for (int i = 0; i< this.movieList.size(); ++i) {

            if (this.movieList.get(i).id == id) {
                this.movieList.remove(i);
                break;
            }
        }

        this.emitDataChanged();
    }

    public void edit(Movie movie) {

        for (int i = 0; i< this.movieList.size(); ++i) {

            if (this.movieList.get(i).id.equals(movie.id)) {
                this.movieList.set(i, movie);
                break;
            }
        }

        this.emitDataChanged();
    }

    private void emitDataChanged() {
        for (DataChangedListener listener : dataChangedListeners) {
            ArrayList<Movie> filteredList = new ArrayList<>(
                    movieList.stream()
                            .filter(food -> movieFilter.isInstance(food))
                            .collect(Collectors.toList())
            );
            listener.dataChanged(filteredList);
        }
    }

    public void saveToFile(String path) {
        try (Writer writer =  new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<ArrayList<Movie>>() { })
                    .withDefaultPrettyPrinter()
                    .writeValue(writer, movieList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String path) {
        try (Reader reader =  new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            movieList = mapper.readerFor(new TypeReference<ArrayList<Movie>>() { })
                    .readValue(reader);

            this.counter = movieList.stream()
                    .map(food -> food.id)
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.emitDataChanged();
    }

    public void setMovieFilter(Class<? extends Movie> movieFilter) {
        this.movieFilter = movieFilter;

        this.emitDataChanged();
    }
}
