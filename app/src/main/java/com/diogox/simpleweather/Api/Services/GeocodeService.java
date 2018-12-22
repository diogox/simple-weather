package com.diogox.simpleweather.Api.Services;

import com.diogox.simpleweather.Api.Models.Geocode.GeocodeLocationResults;
import com.diogox.simpleweather.Api.Models.Places.AutocompleteResults;
import com.diogox.simpleweather.Api.Models.Places.CityDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodeService {

    @GET("json")
    Call<GeocodeLocationResults> FindCityByLocation(@Query("latlng") String latLon, @Query("result_type") String resultType, @Query("key") String apiKey);
}
