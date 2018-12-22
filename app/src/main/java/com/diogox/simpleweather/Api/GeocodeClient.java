package com.diogox.simpleweather.Api;

import com.diogox.simpleweather.Api.Models.Geocode.GeocodeLocationResults;
import com.diogox.simpleweather.Api.Services.GeocodeService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeocodeClient {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";
    private static GeocodeClient instance = null;
    private GeocodeService geocodeApi;

    private GeocodeClient() {
        geocodeApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GeocodeService.class);
    }

    public static GeocodeClient getInstance() {
        if (instance == null) {
            instance = new GeocodeClient();
        }
        return instance;
    }

    public Call<GeocodeLocationResults> getCityByLocation(String lat, String lon) {
        String latLon = lat + " " + lon;
        return geocodeApi.FindCityByLocation(latLon, "locality", PlacesClient.API_KEY);
    }
}
