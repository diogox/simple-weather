package com.diogox.simpleweather.Models;

import retrofit2.Retrofit;

public class Weather {
    private static Weather mInstance;
    public WeatherService mService;

    private Weather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build();

        mService = retrofit.create(WeatherService.class);
    }

    public static Weather getInstance() {

        if (mInstance == null) {
            return mInstance;
        }

        mInstance =  new Weather();
        return mInstance;
    }
}
