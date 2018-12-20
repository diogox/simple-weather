package com.diogox.simpleweather.Api.Models.Weather;

public class Sys {

    private float type;
    private float id;
    private float message;
    private String country;
    private float sunrise;
    private float sunset;


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

    public float getSunrise() {
        return sunrise;
    }

    public float getSunset() {
        return sunset;
    }

}
