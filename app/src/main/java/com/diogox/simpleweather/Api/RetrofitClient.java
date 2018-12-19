package com.diogox.simpleweather.Api;

import com.diogox.simpleweather.Api.Services.WeatherService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    /**
     * Url base da API
     */
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private static Retrofit instance = null;

    /**
     * Criar a instancia do objeto retrofit
     *
     * @return instanca do objeto retrofit. A instancia é criada caso ainda não
     * exista (Padrão Singleton)
     */
    private static Retrofit getClient() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    /**
     * Weather interface
     *
     * @return Weather interface
     */
    public static WeatherService weatherService() {
        return RetrofitClient.getClient().create(WeatherService.class);
    }

}
