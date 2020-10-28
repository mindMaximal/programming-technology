package cards;

import javafx.scene.control.Label;

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

                //System.out.println(items.get(items.size() - 1).toString());

                label.getStyleClass().add("card--active");

                item.getLabel().setText(text);
            }


        }
    }

    public BufferItem getList(Label label) {

        for (BufferItem item : items) {

            if (label == item.getLabel()) {
                return item;
            }

        }

        return null;
    }

    public boolean hasLabel(Label label) {
        boolean hasLabel = false;

        for (BufferItem elem : items) {
            hasLabel = elem.hasLabel(label);
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
        //System.out.println("Constructor: " + suitDeck);
    }

    public void addCard(Card card) {
        suitDeck.add(card);
        System.out.println(suitDeck);
    }

    public boolean hasLabel(Label label) {
        return this.label == label;
    }

    public boolean hasLabel() {
        return this.label != null;
    }

    public Label getLabel() {
        return this.label;
    }

    public ArrayList<Card> getDeck() {
        return suitDeck;
    }

    @Override
    public String toString() {
        return String.format("Suit: %s card: %s", suit, suitDeck.get(suitDeck.size() - 1).toString());
    }

    public Card.Suit getSuit() {
        return this.suit;
    }

    public Card getLastCard() {
        return suitDeck.get(suitDeck.size() - 1);
    }
}

