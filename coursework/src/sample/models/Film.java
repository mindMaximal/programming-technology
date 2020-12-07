package sample.models;

public class Film extends Movie {
    public enum Type {
        documentary("документальный"),
        historical("исторический"),
        fiction("фантастика"),
        scientific("научный"),
        music("музыка"),
        musical("мюзикл"),
        concert("концерт"),
        none("неопределен");

        private String title;

        Type(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return title;
        }
    };

    private Double timing;
    private int awardsCount;
    private Type type;

    public Film () {};

    public Film(String name, Double rating, Double timing, int awardsCount, Type type) {
        super(name, rating);
        this.timing = timing;
        this.awardsCount = awardsCount;
        this.type = type;
    }

    public Double getTiming() {
        return timing;
    }

    public int getAwardsCount() {
        return awardsCount;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String getDescription() {
        String timingString = timing == null ? "" : String.format(", хронометраж %s", timing);
        return String.format("Фильм типа %s%s. Он получил наград: %d", type, timingString, awardsCount);
    }
}
