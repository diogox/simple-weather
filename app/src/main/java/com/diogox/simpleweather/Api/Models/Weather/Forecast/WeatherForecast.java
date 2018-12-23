package com.diogox.simpleweather.Api.Models.Weather.Forecast;

import com.diogox.simpleweather.Api.Models.Weather.City.Clouds;
import com.diogox.simpleweather.Api.Models.Weather.City.Weather;
import com.diogox.simpleweather.Api.Models.Weather.City.Wind;

import java.util.List;

public class WeatherForecast {

    private long dt;
    private MainWeather main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    private Syst sys;
    private String dt_txt;


    public long getDt() {
        return dt;
    }

    public MainWeather getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Syst getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "dt=" + dt +
                ", main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", rain=" + rain +
                ", sys=" + sys +
                ", dt_txt='" + dt_txt + '\'' +
                '}';
    }
}
