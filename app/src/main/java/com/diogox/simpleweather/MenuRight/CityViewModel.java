package com.diogox.simpleweather.MenuRight;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.diogox.simpleweather.Api.Models.Database.AppDb;
import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.Api.Models.Database.Cities.CityDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CityViewModel extends AndroidViewModel {

    private CityDAO cityDao;
    private ExecutorService executorService;

    public CityViewModel(@NonNull Application application) {
        super(application);
        cityDao = AppDb.getInstance(application).cityDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<City>> getAllCities() {
        return cityDao.getAllCities();
    }

    public void saveCity(City city) {
        executorService.execute(() -> cityDao.insertCity(city));
    }

    public void deleteCity(City city) {
        executorService.execute(() -> cityDao.deleteCity(city));
    }

    public City findCity(City city) {
        return cityDao.findById(city.getId());
    }
}