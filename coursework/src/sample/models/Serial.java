package sample.models;

public class Serial extends Movie {
    private int seriesCount;
    private int seasonCount;

    public Serial () {};

    public Serial(String name, Double rating, int seriesCount, int seasonCount) {
        super(name, rating);
        this.seriesCount = seriesCount;
        this.seasonCount = seasonCount;
    }

    public int getSeriesCount() {
        return seriesCount;
    }

    public int getSeasonCount() {
        return seasonCount;
    }

    @Override
    public String getDescription() {
        return String.format("Серий: %d, сезонов: %d", seriesCount, seasonCount);
    }
}

