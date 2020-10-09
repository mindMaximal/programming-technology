package cards;

public class Card {

    public enum  Suit {
        SPADES, //Пики
        HEARTS, //Червы
        CLUBS, //Крести
        DIAMONDS //Буби
    }

    private final int value;
    private final Suit suit;

    public Card() {
        this.value = 2 + (int) (Math.random() * 14);

        int suitRandom = (int) (Math.random() * 4);
        Suit suit;

        switch (suitRandom) {
            case 1:
                suit = Suit.SPADES;
                break;
            case 2:
                suit = Suit.HEARTS;
                break;
            case 3:
                suit = Suit.CLUBS;
                break;
            default:
                suit = Suit.DIAMONDS;
                break;
        }

        this.suit = suit;
    }

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Card(int value, int suit) {

        this.value = value;

        switch (suit) {
            case 1:
                this.suit = Suit.SPADES;
                break;
            case 2:
                this.suit = Suit.HEARTS;
                break;
            case 3:
                this.suit = Suit.CLUBS;
                break;
            default:
                this.suit = Suit.DIAMONDS;
                break;
        }
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {

        String msg = "";

        switch (value) {
            case 2:
                msg = "Два";
                break;
            case 3:
                msg = "Три";
                break;
            case 4:
                msg = "Четыре";
                break;
            case 5:
                msg = "Пять";
                break;
            case 6:
                msg = "Шесть";
                break;
            case 7:
                msg = "Семь";
                break;
            case 8:
                msg = "Восемь";
                break;
            case 9:
                msg = "Девять";
                break;
            case 10:
                msg = "Десять";
                break;
            case 11:
                msg = "Валет";
                break;
            case 12:
                msg = "Дама";
                break;
            case 13:
                msg = "Король";
                break;
            case 14:
                msg = "Туз";
                break;
            default:
                break;
        }

        switch (suit) {
            case SPADES:
                msg += " пики";
                break;
            case HEARTS:
                msg += " черви";
                break;
            case CLUBS:
                msg += " креси";
                break;
            case DIAMONDS:
                msg += " буби";
                break;
            default:
                break;
        }

        return  msg;
    }

}
