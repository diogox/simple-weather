package com.diogox.simpleweather.Api.Models.Database.Cities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

@Entity
public class City {
    private @NonNull @PrimaryKey String id;
    private String name;
    private String countryCode;
    private String lat;
    private String lon;
    private String photoUrl;
    private boolean isFavorite;

    public City(String id, String name, String countryCode, String lat, String lon, String photoUrl) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.lat = lat;
        this.lon = lon;
        this.photoUrl = photoUrl;
        this.isFavorite = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
