package com.diogox.simpleweather.Api.Models.Database.Cities;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM city")
    LiveData<List<City>> getAllCities();

    @Query("SELECT * FROM city WHERE name LIKE :name")
    LiveData<List<City>> findByName(String name);

    @Query("SELECT * FROM city WHERE id LIKE :id")
    City findById(String id);

    @Insert(onConflict = REPLACE)
    void insertCity(City city);

    @Insert
    void insertAll(List<City> cities);

    @Delete
    void deleteCity(City city);
}
