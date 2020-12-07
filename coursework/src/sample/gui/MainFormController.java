package sample.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    @FXML
    public TableView<Movie> mainTable;
    @FXML
    public ComboBox cmbMovieType;

    ObservableList<Class<? extends Movie>> movieTypes = FXCollections.observableArrayList(
            Movie.class,
            Film.class,
            Serial.class,
            Telecast.class
    );

    MovieModel movieModel = new MovieModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        movieModel.addDataChangedListener(foods -> {
            mainTable.setItems(FXCollections.observableArrayList(foods));
        });

        movieModel.load();

        TableColumn<Movie, String> nameColumn = new TableColumn<>("Название:");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Movie, String> ratingColumn = new TableColumn<>("Рейтинг:");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        TableColumn<Movie, String> descriptionColumn = new TableColumn<>("Описание");
        descriptionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });

        mainTable.getColumns().addAll(nameColumn, ratingColumn, descriptionColumn);

        cmbMovieType.setItems(movieTypes);

        cmbMovieType.getSelectionModel().select(0);

        cmbMovieType.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                if (Movie.class.equals(object)) {
                    return "Все";
                } else if (Film.class.equals(object)) {
                    return "Фильм";
                } else if (Serial.class.equals(object)) {
                    return "Сериал";
                } else if (Telecast.class.equals(object)) {
                    return "Телеперадача";
                }
                return null;
            }

            @Override
            public Class fromString(String string) {
                return null;
            }
        });

        cmbMovieType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.movieModel.setMovieFilter((Class<? extends Movie>) newValue);
        });

    }

    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MovieForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Добавление");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());

        MovieFormController controller = loader.getController();

        controller.movieModel = movieModel;

        stage.showAndWait();
    }

    public void onEditClick(ActionEvent actionEvent) throws IOException {

        if (this.mainTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MovieForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Редактирование");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());

        MovieFormController controller = loader.getController();
        controller.setMovie((Movie) this.mainTable.getSelectionModel().getSelectedItem());
        controller.movieModel = movieModel;

        stage.showAndWait();
    }

    public void onDeleteClick(ActionEvent actionEvent) throws IOException {
        Movie movie = (Movie) this.mainTable.getSelectionModel().getSelectedItem();

        // выдаем подтверждающее сообщение
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(String.format("Точно удалить %s?", movie.getName()));

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            movieModel.delete(movie.id);
        }
    }

    public void onSaveToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());

        if (file != null) {
            movieModel.saveToFile(file.getPath());
        }
    }

    public void onLoadToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(mainTable.getScene().getWindow());

        if (file != null) {
            movieModel.loadFromFile(file.getPath());
        }
    }
}
