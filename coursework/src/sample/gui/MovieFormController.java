package sample.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieFormController implements Initializable {

    @FXML
    public ChoiceBox cmbMovieType;
    @FXML
    public TextField txtMovieName;
    @FXML
    public TextField txtMovieRating;
    @FXML
    public VBox filmPane;
    @FXML
    public TextField txtTiming;
    @FXML
    public TextField txtAwards;
    @FXML
    public ChoiceBox cmbFilmType;
    @FXML
    public GridPane serialPane;
    @FXML
    public TextField txtSeries;
    @FXML
    public TextField txtSeasons;
    @FXML
    public GridPane telecastPane;
    @FXML
    public TextField txtDuration;
    @FXML
    public TextField txtAirtime;

    final String MOVIE_FILM = "Фильм";
    final String MOVIE_SERIAL = "Сериал";
    final String MOVIE_TELECAST = "Телепередача";

    public MovieModel movieModel;

    private Integer id = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cmbMovieType.setItems(FXCollections.observableArrayList(
                MOVIE_FILM,
                MOVIE_SERIAL,
                MOVIE_TELECAST
        ));

        cmbMovieType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updatePanes((String) newValue);
        });

        cmbFilmType.setItems(FXCollections.observableArrayList(
                Film.Type.documentary,
                Film.Type.fiction,
                Film.Type.historical,
                Film.Type.scientific
        ));

        updatePanes("");

    }

    public void updatePanes(String value) {
        this.filmPane.setVisible(value.equals(MOVIE_FILM));
        this.filmPane.setManaged(value.equals(MOVIE_FILM));

        this.serialPane.setVisible(value.equals(MOVIE_SERIAL));
        this.serialPane.setManaged(value.equals(MOVIE_SERIAL));

        this.telecastPane.setVisible(value.equals(MOVIE_TELECAST));
        this.telecastPane.setManaged(value.equals(MOVIE_TELECAST));
    }

    public void onSaveClick(ActionEvent actionEvent) {

        if (this.id != null) {
            Movie movie = getMovie();
            movie.id = this.id;

            this.movieModel.edit(movie);
        } else {
            this.movieModel.add(getMovie());
        }

        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void onCancelClick(ActionEvent actionEvent) {
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public Movie getMovie() {
        Movie result = null;
        String name = this.txtMovieName.getText();
        double rating = Double.parseDouble(this.txtMovieRating.getText());

        switch ((String)this.cmbMovieType.getValue()) {
            case MOVIE_FILM:
                Film.Type type = null;

                switch (this.cmbFilmType.getValue().toString()) {
                    case "фантастика":
                    type = Film.Type.fiction;
                        break;
                    case "документальный":
                        type = Film.Type.documentary;
                        break;
                    case "исторический":
                        type = Film.Type.historical;
                        break;
                    case "научный":
                        type = Film.Type.scientific;
                        break;
                    default:
                        break;
                }

                result = new Film(
                        name,
                        rating,
                        Double.parseDouble(this.txtTiming.getText()),
                        Integer.parseInt(this.txtAwards.getText()),
                        type
                );

                break;
            case MOVIE_SERIAL:
                result = new Serial(
                        name,
                        rating,
                        Integer.parseInt(this.txtSeries.getText()),
                        Integer.parseInt(this.txtSeasons.getText())
                );
                break;
            case MOVIE_TELECAST:
                result = new Telecast(
                        name,
                        rating,
                        Double.parseDouble(this.txtDuration.getText()),
                        this.txtAirtime.getText()
                );
                break;
        }
        return result;
    }

    public void setMovie(Movie movie) {
        this.cmbMovieType.setDisable(movie != null);

        this.id = movie != null ? movie.id : null;

        if (movie != null) {

            this.txtMovieName.setText(movie.getName());
            this.txtMovieRating.setText(String.valueOf(movie.getRating()));

            if (movie instanceof Film) {

                this.txtTiming.setText(String.valueOf(((Film) movie).getTiming()));
                this.txtAwards.setText(String.valueOf(((Film) movie).getAwardsCount()));

                this.cmbMovieType.setValue(MOVIE_FILM);
                this.cmbFilmType.setValue(((Film) movie).getType());

            } else if (movie instanceof Serial) {

                this.cmbMovieType.setValue(MOVIE_SERIAL);
                this.txtSeries.setText(String.valueOf(((Serial) movie).getSeriesCount()));
                this.txtSeasons.setText(String.valueOf(((Serial) movie).getSeasonCount()));

            } else if (movie instanceof Telecast) {

                this.cmbMovieType.setValue(MOVIE_TELECAST);
                this.txtDuration.setText(String.valueOf(((Telecast) movie).getDuration()));
                this.txtAirtime.setText(String.valueOf(((Telecast) movie).getDuration()));
            }
        }
    }
}
