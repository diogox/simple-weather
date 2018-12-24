package com.diogox.simpleweather.Api.Models.Database.Alerts;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;

import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.google.gson.Gson;

@Entity
public class Alert {
    private @PrimaryKey(autoGenerate = true) long alertId;

    @Embedded
    @NonNull
    private City city;
    private AlertType valueType;
    private double minValueTrigger;
    private double maxValueTrigger;

    public Alert(City city, AlertType valueType, Double minValueTrigger, Double maxValueTrigger) {
        this.city = city;
        this.valueType = valueType;

        if (minValueTrigger != null)
            this.minValueTrigger = minValueTrigger;
        else
            this.minValueTrigger = -999999;

        if (maxValueTrigger != null)
            this.maxValueTrigger = maxValueTrigger;
        else
            this.maxValueTrigger = 999999;
    }

    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public AlertType getValueType() {
        return valueType;
    }

    public void setValueType(AlertType valueType) {
        this.valueType = valueType;
    }

    public double getMinValueTrigger() {
        return minValueTrigger;
    }

    public void setMinValueTrigger(double minValueTrigger) {
        this.minValueTrigger = minValueTrigger;
    }

    public double getMaxValueTrigger() {
        return maxValueTrigger;
    }

    public void setMaxValueTrigger(double maxValueTrigger) {
        this.maxValueTrigger = maxValueTrigger;
    }
}


