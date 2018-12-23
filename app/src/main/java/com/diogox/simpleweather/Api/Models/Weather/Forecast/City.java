package com.diogox.simpleweather.Api.Models.Weather.Forecast;

import com.diogox.simpleweather.Api.Models.Weather.City.Coordinates;

public class City {

    private long id;
    private String name;
    private Coordinates coord;
    private String country;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }
}
