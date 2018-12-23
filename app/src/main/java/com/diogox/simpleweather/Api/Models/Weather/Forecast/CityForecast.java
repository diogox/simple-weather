package com.diogox.simpleweather.Api.Models.Weather.Forecast;

import java.util.List;

public class CityForecast {

    private String cod;
    private float message;
    private int cnt;
    private List<WeatherForecast> list;
    private CityW city;

    public String getCod() {
        return cod;
    }

    public float getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<WeatherForecast> getList() {
        return list;
    }

    public CityW getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "CityForecast{" +
                "cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                ", city=" + city +
                '}';
    }
}
