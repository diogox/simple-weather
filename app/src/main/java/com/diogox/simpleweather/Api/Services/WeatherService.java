package com.diogox.simpleweather.Api.Services;

import com.diogox.simpleweather.Api.Models.Weather.CityWeather;
import com.diogox.simpleweather.Api.Models.Queries.CityZipCodeQuery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    String MY_API_KEY = "03a4cb43f61a800aa6c7956fba0fe54b";

    @GET("weather")
    Call<CityWeather> getWeatherByCityId(@Query("id") String cityId, @Query("appId") String appId);

    @GET("weather")
    Call<CityWeather> getWeatherByCityCoordinates(
            @Query("lat") double cityLat,
            @Query("lon") double cityLon,
            @Query("appid") String appid
    );

    @GET("weather")
    Call<CityWeather> getWeatherByCityZipCode(@Query("zip") CityZipCodeQuery cityZipCode, @Query("appId") String appId);
}
