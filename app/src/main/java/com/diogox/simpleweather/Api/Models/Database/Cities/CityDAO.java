package com.diogox.simpleweather.Api.Models.Database.Cities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM city")
    List<City> getAllCities();

    @Query("SELECT * FROM city WHERE name LIKE :name")
    List<City> findByName(String name);

    @Insert(onConflict = IGNORE)
    void insertCity(City city);

    @Insert
    void insertAll(City... data);

    @Delete
    void deleteCity(City city);
}
