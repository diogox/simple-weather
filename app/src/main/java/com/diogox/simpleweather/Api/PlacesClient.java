package com.diogox.simpleweather.Api;

import com.diogox.simpleweather.Api.Models.Places.AutocompleteResults;
import com.diogox.simpleweather.Api.Models.Places.CityDetails;
import com.diogox.simpleweather.Api.Services.PlacesService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesClient {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    public static final String API_KEY = "AIzaSyA9gagzs3MlxKG_tCDjXhTVp9BK3mmiXMc"; // TODO: Add api_key through env variables
    private static PlacesClient instance = null;
    private PlacesService placesApi;

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
        return placesApi.searchCitiesByName(query, "(cities)", API_KEY);
    }

    public Call<CityDetails> getCityDetails(String placeId) {
        return placesApi.getCityDetails(placeId, API_KEY);
    }
}
