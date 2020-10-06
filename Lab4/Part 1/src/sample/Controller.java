package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Label lblBlack;
    @FXML
    public Label lblWhite;

    @FXML
    public ListView<String> lstBlack;
    @FXML
    public ListView<String> lstWhite;

    ObservableList<String> lstBlackItems = FXCollections.observableArrayList();
    ObservableList<String> lstWhiteItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Привязываем данные к компонентам
        lstBlack.setItems(lstBlackItems);
        lstWhite.setItems(lstWhiteItems);

        //Захватываем
        lblBlack.setOnDragDetected(this::onDragDetected);
        lblWhite.setOnDragDetected(this::onDragDetected);

        //Перетягиваем между списками
        lstBlack.setOnDragDetected(this::onDragDetected);
        lstWhite.setOnDragDetected(this::onDragDetected);

        //Двигаем над списком
        lstBlack.setOnDragOver(this::onListViewDragOver);
        lstWhite.setOnDragOver(this::onListViewDragOver);

        //Отпускаем мышь
        lstBlack.setOnDragDropped(this::onListViewDragDropped);
        lstWhite.setOnDragDropped(this::onListViewDragDropped);
    }

    private void onListViewDragDropped(DragEvent dragEvent) {
        ListView<String> targetListView = (ListView) dragEvent.getGestureTarget();

        if (dragEvent.getGestureSource() instanceof Label) {
            Label sourceLabel = (Label) dragEvent.getGestureSource();
            targetListView.getItems().add(sourceLabel.getText());
        } else if (dragEvent.getGestureSource() instanceof ListView) {
            ListView sourceListView = (ListView) dragEvent.getGestureSource();

            String lastItem = (String)sourceListView.getItems().get(sourceListView.getItems().size() - 1);

            // удаляем последний элемент из списка источника
            sourceListView.getItems().remove(sourceListView.getItems().size() - 1);

            // добавляем в список, над котором отпустили мышку
            targetListView.getItems().add(lastItem);
        }
    }

    public void onListViewDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() == lblBlack && dragEvent.getSource() == lstBlack
                || dragEvent.getGestureSource() == lblWhite && dragEvent.getSource() == lstWhite) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }

        //Тянем со списка
        if (dragEvent.getGestureSource() instanceof ListView) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void onDragDetected(MouseEvent mouseEvent) {
        Node sourceNode = (Node) mouseEvent.getSource();
        Dragboard db = sourceNode.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString("");
        db.setContent(content);

        mouseEvent.consume();
    }
}
