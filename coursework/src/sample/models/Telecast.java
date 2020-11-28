package sample.models;

public class Telecast extends Movie {
    private Double duration;
    private String airtime;

    public Telecast () {};

    public Telecast(String name, int rating, Double duration, String airtime) {
        super(name, rating);
        this.duration = duration;
        this.airtime = airtime;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    @Override
    public String getDescription() {
        return String.format("Телеперадача длительностью %.2f и его эфирное время: %s", duration, airtime);
    }
}
