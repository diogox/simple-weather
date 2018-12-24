package com.diogox.simpleweather.Api.Models.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.Api.Models.Database.Cities.CityDAO;

import java.util.concurrent.Executors;

@Database(entities = {City.class}, version = 3)
public abstract class AppDb extends RoomDatabase {
    private static AppDb INSTANCE;

    public abstract CityDAO cityDAO();

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
