package sample.models;

public class Serial extends Movie {
    private int seriesCount;
    private int seasonCount;

    public Serial () {};

    public Serial(String name, int rating, int seriesCount, int seasonCount) {
        super(name, rating);
        this.seriesCount = seriesCount;
        this.seasonCount = seasonCount;
    }

    public int getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(int seriesCount) {
        this.seriesCount = seriesCount;
    }

    public int getSeasonCount() {
        return seasonCount;
    }

    public void setSeasonCount(int seasonCount) {
        this.seasonCount = seasonCount;
    }

    @Override
    public String getDescription() {
        return String.format("Сериал состоит из %d серий и %d сезонов", seasonCount, seasonCount);
    }
}

