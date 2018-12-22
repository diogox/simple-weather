package com.diogox.simpleweather.Api.Models.Weather.City;

public class Sys {

    private float type;
    private float id;
    private float message;
    private String country;
    private long sunrise;
    private long sunset;


    // Getter Methods

    public float getType() {
        return type;
    }

    public float getId() {
        return id;
    }

    public float getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

}
