package com.diogox.simpleweather.Api.Services;

import com.diogox.simpleweather.Api.Models.Places.AutocompleteResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesService {

    @GET("autocomplete/json")
    Call<AutocompleteResults> searchCitiesByName(@Query("input") String cityQuery, @Query("types") String placeType, @Query("key") String apiKey);
}
