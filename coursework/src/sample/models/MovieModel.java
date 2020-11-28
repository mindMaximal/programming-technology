package sample.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MovieModel {
    ArrayList<Movie> movieList = new ArrayList();
    private int counter = 1;

    Class<? extends Movie> movieFilter = Movie.class;

    public interface DataChangedListener {
        void dataChanged(ArrayList<Movie> movieList);
    }

    private ArrayList<DataChangedListener> dataChangedListeners = new ArrayList<>();

    public void addDataChangedListener(DataChangedListener listener) {
        this.dataChangedListeners.add(listener);
    }

    public DataChangedListener dataChangedListener;

    public void load() {
        movieList.clear();

        this.add(new Film("Movie 1", 5, 55.30, 3, Film.Type.documentary), false);
        this.add(new Serial("Serial 1",8, 10, 2), false);
        this.add(new Telecast("Telecast 1", 7, 50.34, "20:30 - 21:30"), false);

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
                    movieList.stream() // запускаем стрим
                            .filter(food -> movieFilter.isInstance(food)) // фильтруем по типу
                            .collect(Collectors.toList()) // превращаем в список
            );
            listener.dataChanged(filteredList); // подсовывам сюда список
        }
    }

    public void saveToFile(String path) {
        try (Writer writer =  new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<ArrayList<Movie>>() { }) // указали какой тип подсовываем
                    .withDefaultPrettyPrinter() // кстати эта строчка чтобы в файлике все красиво печаталось
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

    public void setFoodFilter(Class<? extends Movie> movieFilter) {
        this.movieFilter = movieFilter;

        this.emitDataChanged();
    }
}
