package com.diogox.simpleweather;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.Api.Models.Database.Alerts.AlertType;
import com.diogox.simpleweather.Api.Models.Database.AppDb;
import com.diogox.simpleweather.Api.Models.Weather.City.CityWeather;
import com.diogox.simpleweather.Api.Services.WeatherService;
import com.diogox.simpleweather.Api.WeatherClient;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

class AlertJob extends Job {
    public static final String TAG = "alert_job";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        //PersistableBundleCompat extras = params.getExtras();

        List<Alert> alerts = AppDb.getInstance(getContext()).alertDAO().getAlerts();
        for (Alert alert : alerts) {

            String cityLat = alert.getCity().getLat();
            String cityLon = alert.getCity().getLon();

            // Query API
            Call<CityWeather> forecast = WeatherClient.weatherService().getWeatherByCityCoordinates(cityLat, cityLon, "pt", WeatherService.MY_API_KEY);

            try {
                Response<CityWeather> response = forecast.execute();

                // Check boundaries
                AlertType paramType = alert.getValueType();
                double minValue = alert.getMinValueTrigger();
                double maxValue = alert.getMaxValueTrigger();

                boolean isWithinBoundaries = false;
                if (AlertType.Temperature == paramType) {

                    double current = response.body().getMain().getTemp();
                    isWithinBoundaries = checkParameterBoundaries(current, minValue, maxValue);
                } else if (AlertType.Humidity == paramType) {

                    double current = response.body().getMain().getHumidity();
                    isWithinBoundaries = checkParameterBoundaries(current, minValue, maxValue);
                } else if (AlertType.WindSpeed == paramType) {

                    double current = response.body().getWind().getSpeed();
                    isWithinBoundaries = checkParameterBoundaries(current, minValue, maxValue);
                } else if (AlertType.Pressure == paramType) {

                    double current = response.body().getMain().getPressure();
                    isWithinBoundaries = checkParameterBoundaries(current, minValue, maxValue);
                }

                if (!isWithinBoundaries) {

                    String message = "Um alerta definido para a " + alert.getValueType().toString() +
                            " foi ativo para a cidade " + alert.getCity().getName();

                    Intent serviceIntent = new Intent(getContext(), AlertService.class);
                    serviceIntent.putExtra("message", message);

                    getContext().startService(serviceIntent);

                    // Remove alert?
                    AppDb.getInstance(getContext()).alertDAO().deleteAlert(alert);
                }

            } catch (IOException e) {
                return Result.FAILURE;
            }
        }

        return Result.SUCCESS;
    }

    private boolean checkParameterBoundaries(double value, double minValue, double maxValue) {

        if (value < minValue || value > maxValue) {
            return false;
        }

        return true;
    }

    public static void scheduleJob() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(AlertJob.TAG);
        if (!jobRequests.isEmpty()) {
            return;
        }

        PersistableBundleCompat extras = new PersistableBundleCompat();

        new JobRequest.Builder(AlertJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(7))
                .setUpdateCurrent(true) // calls cancelAllForTag(NoteSyncJob.TAG) for you
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
    }
}
