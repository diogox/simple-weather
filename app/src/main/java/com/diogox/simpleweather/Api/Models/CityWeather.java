package com.diogox.simpleweather.Api.Models;

import java.util.List;

public class CityWeather {

    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private float visibility;
    private Wind wind;
    private Clouds clouds;
    private float dt;
    private Sys sys;
    private float id;
    private String name;
    private float cod;


    public Coordinates getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public float getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public float getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return "CityWeather{" +
                "coor=" + coord +
                ", weather=" + weather +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}
