package com.diogox.simpleweather.Api.Models.Database.Alerts;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.diogox.simpleweather.Api.Models.Database.Cities.City;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AlertDAO {

    @Query("SELECT * FROM alert")
    LiveData<List<Alert>> getAllAlerts();

    @Query("SELECT * FROM alert")
    List<Alert> getAlerts();

    @Insert(onConflict = REPLACE)
    void insertAlert(Alert alert);

    @Insert
    void insertAll(List<Alert> alerts);

    @Delete
    void deleteAlert(Alert alert);
}
