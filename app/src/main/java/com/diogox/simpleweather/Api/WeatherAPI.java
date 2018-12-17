package com.diogox.simpleweather.Api;

import com.diogox.simpleweather.Api.Models.CityWeather;
import com.diogox.simpleweather.Api.Services.WeatherService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class WeatherAPI {
    private static WeatherAPI mInstance;
    public WeatherService mService;

    private WeatherAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build();

        mService = retrofit.create(WeatherService.class);
    }

    public static WeatherAPI getInstance() {

        if (mInstance == null) {
            return mInstance;
        }

        mInstance =  new WeatherAPI();
        return mInstance;
    }

    public List<String> FindCitiesByName(String cityName) {
        // TODO: Have android get file from 'bulk.openweathermap.org/sample/city.list.json.gz'
        // Unzip it
        // Read it to a sqlite database
        // All of that at startup (in case the db doesn't already exist)
        // Then, in this method, return the city ids of all cities who match the name

        return new ArrayList<>();
    }
}
