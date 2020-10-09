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
        System.out.println( );

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
        dragEvent.acceptTransferModes(TransferMode.ANY);
    }

    private void onBufferDragDropped(DragEvent dragEvent) {
        Label target = (Label) dragEvent.getGestureTarget();
        Label source = (Label) dragEvent.getGestureSource();

        /*System.out.println("target: " + target);
        System.out.println("source: " + source);*/

        clearCurrentCard();

        buffer.addItem(target, currentCard);

        deckUpdate();
    }

    public void changeCard(ActionEvent actionEvent) {

        System.out.println("Current card: " + currentCard + " Index: " + currentCardIndex + " deck size:" + deck.size());

        System.out.println(deck);

        if (deck.size() > 0) {
            currentCardLabel.setText(deck.get(currentCardIndex).toString());
            currentCardIndex--;

            currentCardLabel.getStyleClass().add("card--active");

            if (currentCardIndex == -1) {
                currentCardIndex = deck.size() - 1;
            }

        } else {
            deckEmptyRender();
        }

    }

    private void deckEmptyRender() {
        currentCardBtn.setText("Колода\r\n пуста");
        currentCardBtn.setDisable(true);

        currentCard = null;

        currentCardLabel.getStyleClass().remove("card--active");
        currentCardLabel.setText("");
    }

    private void deckUpdate() {
        deck.remove(currentCardIndex);
        currentCardIndex--;

        if (currentCardIndex == -1) {
            currentCardIndex = deck.size() - 1;
        }

        if (deck.isEmpty()) {
            deckEmptyRender();
        } else {
            currentCard = deck.get(currentCardIndex);
            //currentCardLabel.setText(currentCard.toString());
        }
    }

    private void clearCurrentCard() {
        currentCardLabel.getStyleClass().remove("card--active");
        currentCardLabel.setText("");
    }

    public ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();

        for (int i = 0; i < Card.Suit.values().length; i++) {
            for (int j = 2; j < 3; j++) {
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
