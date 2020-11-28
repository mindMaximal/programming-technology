package sample.models;

public class Film extends Movie {
    public enum Type {
        documentary("документальный"),
        historical("исторический"),
        fiction("фантастика"),
        scientific("научный");

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

    public Film(String name, int rating, Double timing, int awardsCount, Type type) {
        super(name, rating);
        this.timing = timing;
        this.awardsCount = awardsCount;
        this.type = type;
    }

    public Double getTiming() {
        return timing;
    }

    public void setTiming(Double timing) {
        this.timing = timing;
    }

    public int getAwardsCount() {
        return awardsCount;
    }

    public void setAwardsCount(int awardsCount) {
        this.awardsCount = awardsCount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String getDescription() {
        return String.format("Фильм типа %s, хронометраж %s. Он получил %d наград", type, timing, awardsCount);
    }
}
