package cards;

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

/*
    SPADES HEARTS CLUBS   DIAMONDS
    Пики   Червы  Крести  Буби
 */

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Card currentCard;

    @FXML
    public Label currentCardLabel;

    @FXML
    public ListView<Card> lstSpades;
    @FXML
    public ListView<Card> lstHeart;
    @FXML
    public ListView<Card> lstClubs;
    @FXML
    public ListView<Card> lstDiamonds;

    ObservableList<Card> lstSpadesItems = FXCollections.observableArrayList();
    ObservableList<Card> lstHeartsItems = FXCollections.observableArrayList();
    ObservableList<Card> lstClubsItems = FXCollections.observableArrayList();
    ObservableList<Card> lstDiamondsItems = FXCollections.observableArrayList();

    ArrayList<Card> deck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        deck = createDeck();

        Collections.shuffle(deck);

        currentCard = deck.get(deck.size() - 1);
        currentCardLabel.setText(currentCard.getValue());

        lstSpades.setItems(lstSpadesItems);
        lstHeart.setItems(lstHeartsItems);
        lstClubs.setItems(lstClubsItems);
        lstDiamonds.setItems(lstDiamondsItems);

        lstSpades.setCellFactory(param -> new ListCell<Card>() {
            @Override
            protected void updateItem(Card item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getValue() == null) {
                    setText(null);
                } else {
                    setText(item.getValue());
                }
            }
        });

        lstHeart.setCellFactory(param -> new ListCell<Card>() {
            @Override
            protected void updateItem(Card item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getValue() == null) {
                    setText(null);
                } else {
                    setText(item.getValue());
                }
            }
        });

        lstClubs.setCellFactory(param -> new ListCell<Card>() {
            @Override
            protected void updateItem(Card item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getValue() == null) {
                    setText(null);
                } else {
                    setText(item.getValue());
                }
            }
        });

        lstDiamonds.setCellFactory(param -> new ListCell<Card>() {
            @Override
            protected void updateItem(Card item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getValue() == null) {
                    setText(null);
                } else {
                    setText(item.getValue());
                }
            }
        });

        //Захватываем
        currentCardLabel.setOnDragDetected(this::onDragDetected);
        //Двигаем над списком
        lstSpades.setOnDragOver(this::onListViewDragOver);
        lstHeart.setOnDragOver(this::onListViewDragOver);
        lstClubs.setOnDragOver(this::onListViewDragOver);
        lstDiamonds.setOnDragOver(this::onListViewDragOver);
        //Отпускаем мышь
        lstSpades.setOnDragDropped(this::onListViewDragDropped);
        lstHeart.setOnDragDropped(this::onListViewDragDropped);
        lstClubs.setOnDragDropped(this::onListViewDragDropped);
        lstDiamonds.setOnDragDropped(this::onListViewDragDropped);
    }

    private void onDragDetected(MouseEvent mouseEvent) {

        if (deck.isEmpty()) {
            return;
        }

        Node sourceNode = (Node) mouseEvent.getSource();

        Dragboard db = sourceNode.startDragAndDrop(TransferMode.ANY);

        db.setDragView(createSnapshot(sourceNode), mouseEvent.getX(), mouseEvent.getY());

        ClipboardContent content = new ClipboardContent();
        content.putString("");
        db.setContent(content);

        mouseEvent.consume();
    }

    private void onListViewDragOver(DragEvent dragEvent) {

        ListView<Card> targetListView = (ListView) dragEvent.getSource();

        if (currentCard.getSuit() == Card.Suit.SPADES && targetListView == lstSpades || currentCard.getSuit() == Card.Suit.CLUBS && targetListView == lstClubs || currentCard.getSuit() == Card.Suit.HEARTS && targetListView == lstHeart || currentCard.getSuit() == Card.Suit.DIAMONDS && targetListView == lstDiamonds) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    private void onListViewDragDropped(DragEvent dragEvent) {
        ListView<Card> targetListView = (ListView) dragEvent.getGestureTarget();

        Label sourceLabel = (Label) dragEvent.getGestureSource();
        targetListView.getItems().add(currentCard);

        deckUpdate();
    }

    private void deckUpdate() {
        deck.remove(deck.size() - 1);

        if (deck.isEmpty()) {
            currentCard = null;
            currentCardLabel.setText("Колода пуста");
        } else {
            currentCard = deck.get(deck.size() - 1);
            currentCardLabel.setText(currentCard.getValue());
        }
    }

    public ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();

        for (int i = 0; i < Card.Suit.values().length; i++) {
            for (int j = 2; j < 15; j++) {
                deck.add(new Card(j, i));
            }
        }

        return deck;
    }

    private WritableImage createSnapshot(Node node) {
        SnapshotParameters snapshotParams = new SnapshotParameters();
        return node.snapshot(snapshotParams, null);
    }
}
