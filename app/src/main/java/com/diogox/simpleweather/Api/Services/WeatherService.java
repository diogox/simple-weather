package com.diogox.simpleweather.Api.Services;

import com.diogox.simpleweather.Api.Models.CityWeather;
import com.diogox.simpleweather.Api.Models.Queries.CityZipCodeQuery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Call<CityWeather> getWeatherByCityId(@Query("id") String cityId, @Query("appId") String appId);

    @GET("weather")
    Call<CityWeather> getWeatherByCityCoordinates(@Query("lat") String cityLat, @Query("lon") String cityLon, @Query("appId") String appId);

    @GET("weather")
    Call<CityWeather> getWeatherByCityZipCode(@Query("zip") CityZipCodeQuery cityZipCode, @Query("appId") String appId);
}
