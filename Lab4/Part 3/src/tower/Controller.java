package tower;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private int counter = 0;
    private int circlesCounter;

    @FXML
    public Label counterLabel;

    @FXML
    public ListView<Circle> lstStart;

    @FXML
    public ListView<Circle> lstBuffer;

    @FXML
    public ListView<Circle> lstEnd;

    ObservableList<Circle> lstStartItems = FXCollections.observableArrayList();
    ObservableList<Circle> lstBufferItems = FXCollections.observableArrayList();
    ObservableList<Circle> lstEndItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstStart.setItems(lstStartItems);
        lstBuffer.setItems(lstBufferItems);
        lstEnd.setItems(lstEndItems);

        lstStart.setCellFactory(param -> new ListCell<Circle>() {
            @Override
            protected void updateItem(Circle item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getValue() == null) {
                    setText(null);
                } else {
                    setText(item.getValue().toString());
                }
            }
        });

        lstBuffer.setCellFactory(param -> new ListCell<Circle>() {
            @Override
            protected void updateItem(Circle item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getValue() == null) {
                    setText(null);
                } else {
                    setText(item.getValue().toString());
                }
            }
        });

        lstEnd.setCellFactory(param -> new ListCell<Circle>() {
            @Override
            protected void updateItem(Circle item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getValue() == null) {
                    setText(null);
                } else {
                    setText(item.getValue().toString());
                }
            }
        });

        lstStartItems.addAll(
                new Circle(1),
                new Circle(2),
                new Circle(3),
                new Circle(4)
        );

        circlesCounter = lstStartItems.size();

        //Захватываем
        lstStart.setOnDragDetected(this::onDragDetected);
        lstBuffer.setOnDragDetected(this::onDragDetected);
        lstEnd.setOnDragDetected(this::onDragDetected);
        //Двигаем над списком
        lstStart.setOnDragOver(this::onListViewDragOver);
        lstBuffer.setOnDragOver(this::onListViewDragOver);
        lstEnd.setOnDragOver(this::onListViewDragOver);
        //Отпускаем мышь
        lstStart.setOnDragDropped(this::onListViewDragDropped);
        lstBuffer.setOnDragDropped(this::onListViewDragDropped);
        lstEnd.setOnDragDropped(this::onListViewDragDropped);
    }

    private void onDragDetected(MouseEvent mouseEvent) {
        Node sourceNode = (Node) mouseEvent.getSource();

        Dragboard db = sourceNode.startDragAndDrop(TransferMode.ANY);

        //db.setDragView(createSnapshot((Node) mouseEvent.getTarget()), mouseEvent.getX(), mouseEvent.getY());

        ClipboardContent content = new ClipboardContent();
        content.putString("");
        db.setContent(content);

        mouseEvent.consume();
    }

    private void onListViewDragOver(DragEvent dragEvent) {

        ListView<Circle> targetListView = (ListView) dragEvent.getGestureTarget();
        ListView<Circle> sourceListView = (ListView) dragEvent.getSource();

        if (dragEvent.getGestureSource() != dragEvent.getSource()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    private void onListViewDragDropped(DragEvent dragEvent) {
        ListView<Circle> targetListView = (ListView) dragEvent.getGestureTarget();
        ListView sourceListView = (ListView) dragEvent.getGestureSource();

        // берем первый элемент в списке

        if (!targetListView.getItems().isEmpty() && !sourceListView.getItems().isEmpty()) {

            Circle lastSourceItem = (Circle) sourceListView.getItems().get(0);
            Circle lastTargetItem = (Circle) targetListView.getItems().get(0);

            String test = lastSourceItem.getValue() + " Target =" + lastTargetItem.getValue();

            System.out.println();

            if (lastSourceItem.getValue() < lastTargetItem.getValue()) {
                // удаляем последний элемент из списка источника
                sourceListView.getItems().remove(sourceListView.getItems().get(0));
                // добавляем в список, над котором отпустили мышку
                targetListView.getItems().add(0, lastSourceItem);

                updateCount();
                checkEndGame();
            }

        } else if (targetListView.getItems().isEmpty() && !sourceListView.getItems().isEmpty()) {
            Circle lastSourceItem = (Circle)sourceListView.getItems().get(0);
            // удаляем последний элемент из списка источника
            sourceListView.getItems().remove(sourceListView.getItems().get(0));
            // добавляем в список, над котором отпустили мышку
            targetListView.getItems().add(0, lastSourceItem);

            updateCount();
            checkEndGame();
        }

    }

    private void checkEndGame() {

        int counter = 0;

        for (int i = 0; i < lstEndItems.size(); i++) {
            if (lstEndItems.get(i).getValue() == i + 1) {
                counter++;
            }
        }

        if (counter == circlesCounter) {
            counterLabel.setText(String.format("Вы выиграли за %d шагов, начните новую игру", this.counter));
            lstStart.setDisable(true);
            lstBuffer.setDisable(true);
            lstEnd.setDisable(true);
        }
    }

    public void StartOver() {
        lstStartItems.clear();
        lstBufferItems.clear();
        lstEndItems.clear();

        updateCount(0);

        lstStartItems.addAll(
                new Circle(1),
                new Circle(2),
                new Circle(3),
                new Circle(4)
        );

        lstStart.setDisable(false);
        lstBuffer.setDisable(false);
        lstEnd.setDisable(false);
    }

    public void updateCount() {
        counter++;
        counterLabel.setText(String.format("Количество шагов: %d", counter));
    }

    public void updateCount(int value) {
        counter = value;
        counterLabel.setText(String.format("Количество шагов: %d", counter));
    }

    private WritableImage createSnapshot(Node node) {
        SnapshotParameters snapshotParams = new SnapshotParameters();
        return node.snapshot(snapshotParams, null);
    }

}
