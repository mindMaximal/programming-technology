package cards;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

/*
    SPADES HEARTS CLUBS   DIAMONDS
    Пики   Червы  Крести  Буби
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Label bufferLabel1;
    @FXML
    public Label bufferLabel2;
    @FXML
    public Label bufferLabel3;
    @FXML
    public Label bufferLabel4;

    @FXML
    public Button currentCardBtn;
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
    @FXML
    public AnchorPane anchorpane1;
    @FXML
    public AnchorPane anchorpane2;
    @FXML
    public AnchorPane anchorpane3;
    @FXML
    public AnchorPane anchorpane4;
    @FXML
    public AnchorPane anchorpane5;
    @FXML
    public AnchorPane anchorpane6;
    @FXML
    public AnchorPane anchorpane7;

    Card currentCard;
    int currentCardIndex;

    ObservableList<Card> lstSpadesItems = FXCollections.observableArrayList();
    ObservableList<Card> lstHeartsItems = FXCollections.observableArrayList();
    ObservableList<Card> lstClubsItems = FXCollections.observableArrayList();
    ObservableList<Card> lstDiamondsItems = FXCollections.observableArrayList();

    ArrayList<Card> deck;

    Buffer buffer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<Label> bufferLabels = new ArrayList<>();

        bufferLabels.add(bufferLabel1);
        bufferLabels.add(bufferLabel2);
        bufferLabels.add(bufferLabel3);
        bufferLabels.add(bufferLabel4);

        for (Label label : bufferLabels) {
            label.setOnDragOver(this::onBufferDragOver);
            label.setOnDragDropped(this::onBufferDragDropped);
        }

        ArrayList<AnchorPane> cells = new ArrayList<>();

        cells.add(anchorpane1);
        cells.add(anchorpane2);
        cells.add(anchorpane3);
        cells.add(anchorpane4);
        cells.add(anchorpane5);
        cells.add(anchorpane6);
        cells.add(anchorpane7);


        int count = 1;
        for (AnchorPane pane : cells) {

            for (int i = 0; i < count; i++) {
                Label newCard = null;

                if (i == count - 1) {
                     newCard = createCard("Text");
                } else {
                     newCard = createCard();
                }

                pane.getChildren().add(newCard);

                pane.setTopAnchor(newCard,20.00 * i);

                newCard.toFront();
            }

            count++;
        }

        for (AnchorPane pane : cells) {

        }

        buffer = new Buffer();

        deck = createDeck();
        Collections.shuffle(deck);

        currentCardIndex = deck.size() - 1;
        currentCard = deck.get(currentCardIndex);

//        lstSpades.setCellFactory(param -> new ListCell<Card>() {
//            @Override
//            protected void updateItem(Card item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (empty || item == null || item.getValue() == null) {
//                    setText(null);
//                } else {
//                    setText(item.getValue());
//                }
//            }
//        });


        currentCardLabel.setOnDragDetected(this::onDragDetected);



   /*     lstSpades.setOnDragOver(this::onListViewDragOver);
        lstHeart.setOnDragOver(this::onListViewDragOver);
        lstClubs.setOnDragOver(this::onListViewDragOver);
        lstDiamonds.setOnDragOver(this::onListViewDragOver);
        lstSpades.setOnDragDropped(this::onListViewDragDropped);
        lstHeart.setOnDragDropped(this::onListViewDragDropped);
        lstClubs.setOnDragDropped(this::onListViewDragDropped);
        lstDiamonds.setOnDragDropped(this::onListViewDragDropped);*/
    }

    private void onDragDetected(MouseEvent mouseEvent) {

        if (deck.isEmpty()) {
            return;
        }

        Node sourceNode = (Node) mouseEvent.getSource();

        if(((Label) mouseEvent.getSource()).getText().trim().length() == 0) {
            return;
        }

        Dragboard db = sourceNode.startDragAndDrop(TransferMode.ANY);

        db.setDragView(createSnapshot(sourceNode), mouseEvent.getX(), mouseEvent.getY());

        ClipboardContent content = new ClipboardContent();
        content.putString("");
        db.setContent(content);

        mouseEvent.consume();
    }

    private void onBufferDragOver(DragEvent dragEvent) {
        Label target = (Label) dragEvent.getGestureTarget();
        Label source = (Label) dragEvent.getGestureSource();

        boolean draggble = false;

        BufferItem list = buffer.getList((Label) dragEvent.getTarget());

        /*System.out.println("\r\ntarget: " + target);
        System.out.println("source: " + source);
        System.out.println("getTarget: " + dragEvent.getTarget());
        System.out.println("getSource: " + dragEvent.getSource());*/


        //Проверку нужно делать через буффер, так как поля не определены еще
        if (list == null && currentCard.getValue() == 1) {
            draggble = true;
        } else {
            Card.Suit suit = list.getSuit();
            Card lastCard = list.getLastCard();

            System.out.println("currentCard: " + currentCard.getValue());
            System.out.println("Last card: " + lastCard.getValue());

            if (suit == currentCard.getSuit() && currentCard.getValue() - 1 == lastCard.getValue() ) {
                draggble = true;
            }

        }

        if (draggble) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        } else {
            dragEvent.acceptTransferModes(TransferMode.NONE);
        }

        dragEvent.consume();
    }

    private void onBufferDragDropped(DragEvent dragEvent) {
        Label target = (Label) dragEvent.getGestureTarget();
        Label source = (Label) dragEvent.getGestureSource();

        /*System.out.println("target: " + target);
        System.out.println("source: " + source);*/

        clearCurrentCard();

        buffer.addItem(target, currentCard);

        deckUpdate();

        dragEvent.consume();
    }

    public void changeCard(ActionEvent actionEvent) {

        //System.out.println("\r\nCurrent card: " + currentCard + " Index: " + currentCardIndex + " deck size:" + deck.size());

        //System.out.println(deck);

        if (deck.size() > 0) {

            currentCardIndex--;

            if (currentCardIndex < 0) {
                currentCardIndex = deck.size() - 1;
            }

            currentCard = deck.get(currentCardIndex);
            currentCardLabel.setText(currentCard.toString());

            if (!currentCardLabel.getStyleClass().contains("card--active")) {
                currentCardLabel.getStyleClass().add("card--active");
            }

        } else {
            deckEmptyRender();
        }

    }

    private void deckEmptyRender() {
        currentCardBtn.setText("Колода\r\nпуста");
        currentCardBtn.setDisable(true);

        currentCard = null;

        currentCardLabel.getStyleClass().remove("card--active");
        currentCardLabel.setText("");
    }

    private void deckUpdate() {

        if (deck.isEmpty()) {
            deckEmptyRender();
        } else {
            currentCard = deck.get(currentCardIndex);
        }

    }

    private void clearCurrentCard() {

        if (deck.isEmpty()) {
            deckEmptyRender();
        } else {
            currentCard = deck.get(currentCardIndex);
            deck.remove(currentCardIndex);
            currentCardIndex--;

            if (currentCardIndex < 0) {
                currentCardIndex = deck.size() - 1;
            }

            currentCardLabel.getStyleClass().remove("card--active");
            currentCardLabel.setText("");
        }
    }

    public ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();

        for (int i = 0; i < Card.Suit.values().length; i++) {
            for (int j = 1; j < 3; j++) {
                deck.add(new Card(j, i));
            }
        }

        return deck;
    }

    private WritableImage createSnapshot(Node node) {
        SnapshotParameters snapshotParams = new SnapshotParameters();
        return node.snapshot(snapshotParams, null);
    }

    public Label createCard(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("card");
        label.getStyleClass().add("card--active");

        return label;
    }

    public Label createCard() {
        Label label = new Label("");
        label.getStyleClass().add("card");
        label.getStyleClass().add("card-cover");

        return label;
    }

}
