package com.diogox.simpleweather.Api.Models.Database.Cities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;

@Entity
public class City {
    private @PrimaryKey long id;
    private String name;
    private String countryCode;
    private String lat;
    private String lon;
    private boolean isFavorite;

    public City(long id, String name, String countryCode, String lat, String lon) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.lat = lat;
        this.lon = lon;
        this.isFavorite = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public static List<City> populateData() {
        return Arrays.asList(new City[]{
                new City(12341234, "Felgueiras", "PT", "", ""),
                new City(12341235, "Gondomar", "PT", "", ""),
                new City(12341236, "Gondomar", "ES", "", ""),
        });
    }
}
