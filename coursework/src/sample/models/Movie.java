package sample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties({"description"})
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class Movie {
    private String name;
    private Double rating;
    public Integer id = null;

    public Movie () {};

    public Movie(String name, Double rating) {
        this.name = name;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("Кино: \"%s\" с рейтингом %.2f", name, rating);
    }

    public String getName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public String getDescription() {
        return "";
    }
}
