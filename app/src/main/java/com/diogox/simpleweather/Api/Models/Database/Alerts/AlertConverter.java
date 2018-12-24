package com.diogox.simpleweather.Api.Models.Database.Alerts;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

public class AlertConverter {

    @TypeConverter
    public String fromAlertType(AlertType value) {
        Gson gson = new Gson();
        return value == null ? null : gson.toJson(value);
    }

    @TypeConverter
    public AlertType toAlertType(String value) {
        Gson gson = new Gson();
        return value == null ? null : gson.fromJson(value, AlertType.class);
    }
}
