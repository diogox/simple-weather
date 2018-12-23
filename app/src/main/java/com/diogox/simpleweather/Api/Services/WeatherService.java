package com.diogox.simpleweather.Api.Services;

import com.diogox.simpleweather.Api.Models.Weather.Area.AreaWeather;
import com.diogox.simpleweather.Api.Models.Weather.City.CityWeather;
import com.diogox.simpleweather.Api.Models.Queries.CityZipCodeQuery;
import com.diogox.simpleweather.Api.Models.Weather.Forecast.CityForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    String MY_API_KEY = "03a4cb43f61a800aa6c7956fba0fe54b";

    @GET("weather")
    Call<CityWeather> getWeatherByCityId(@Query("id") String cityId, @Query("lang") String lang, @Query("appId") String appId);

    @GET("weather")
    Call<CityWeather> getWeatherByCityCoordinates(
            @Query("lat") String cityLat,
            @Query("lon") String cityLon,
            @Query("lang") String lang,
            @Query("appid") String appid
    );

    @GET("forecast")
    Call<CityForecast> getWeatherForecast(
            @Query("lat") String cityLat,
            @Query("lon") String cityLon,
            @Query("lang") String lang,
            @Query("appid") String appid
    );

    @GET("find")
    Call<AreaWeather> getWeatherForArea(@Query("lat") String lat, @Query("lon") String lon, @Query("cnt") String cnt, @Query("appId") String appId);
}
