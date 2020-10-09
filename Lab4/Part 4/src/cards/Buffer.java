package cards;

import javafx.scene.control.Label;

import java.awt.*;
import java.util.ArrayList;

public class Buffer {
    private ArrayList<BufferItem> items = new ArrayList<>();

    public void addItem(Label label, Card card) {

        if (!hasLabel(label)) {
            items.add(new BufferItem(label, card));
        } else {
            for (BufferItem elem : items) {
                if (label == elem.getLabel()) {
                    elem.addCard(card);
                }
            }
        }

        bufferRender();
    }

    private void bufferRender() {
        for (BufferItem item : items) {

            if (item.getDeck().size() != 0) {
                String text = item.getDeck().get(item.getDeck().size() - 1).toString();
                Label label = item.getLabel();

                label.getStyleClass().add("card--active");

                item.getLabel().setText(text);
            }


        }
    }

    public boolean hasLabel(Label label) {
        boolean hasLabel = false;

        for (BufferItem elem : items) {
            hasLabel = elem.checkLabel(label);
        }

        return hasLabel;
    }
}

class BufferItem {

    private final Label label;
    private final Card.Suit suit;
    private final ArrayList<Card> suitDeck = new ArrayList<>();

    public BufferItem(Label label, Card card) {
        this.label = label;
        this.suit = card.getSuit();

        suitDeck.add(card);
        System.out.println("Constructor: " + suitDeck);
    }

    public void addCard(Card card) {
        suitDeck.add(card);
        System.out.println(suitDeck);
    }

    public boolean checkLabel(Label label) {
        return this.label == label;
    }

    public Label getLabel() {
        return this.label;
    }

    public ArrayList<Card> getDeck() {
        return suitDeck;
    }
}

