package com.diogox.simpleweather.Api.Models.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.Api.Models.Database.Alerts.AlertConverter;
import com.diogox.simpleweather.Api.Models.Database.Alerts.AlertDAO;
import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.Api.Models.Database.Cities.CityDAO;

@Database(entities = {City.class, Alert.class}, version = 4)
@TypeConverters(AlertConverter.class)
public abstract class AppDb extends RoomDatabase {
    private static AppDb INSTANCE;

    public abstract CityDAO cityDAO();
    public abstract AlertDAO alertDAO();

    public synchronized static AppDb getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDb.class, "WeatherApp.db"
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        }
        return INSTANCE;
    }


}
