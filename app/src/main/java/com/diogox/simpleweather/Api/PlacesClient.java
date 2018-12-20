package com.diogox.simpleweather.Api;

import com.diogox.simpleweather.Api.Models.Places.AutocompleteResults;
import com.diogox.simpleweather.Api.Services.PlacesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesClient {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static PlacesClient instance = null;
    private PlacesService placesApi = null;

    private PlacesClient() {
        placesApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlacesService.class);
    }

    public static PlacesClient getInstance() {
        if (instance == null) {
            instance = new PlacesClient();
        }
        return instance;
    }

    public Call<AutocompleteResults> searchCitiesByName(String query) {
        return placesApi.searchCitiesByName(query, "(cities)", "INSERT_API_KEY_HERE"); // TODO: Add api_key through env variables
    }
}
