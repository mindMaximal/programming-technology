package sample.models;

public class Telecast extends Movie {
    private Double duration;
    private String airtime;

    public Telecast () {};

    public Telecast(String name, Double rating, Double duration, String airtime) {
        super(name, rating);
        this.duration = duration;
        this.airtime = airtime;
    }

    public Double getDuration() {
        return duration;
    }

    @Override
    public String getDescription() {
        return String.format("Телеперадача длительностью %.2f и его эфирное время: %s", duration, airtime);
    }
}
