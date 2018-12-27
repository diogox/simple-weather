package com.diogox.simpleweather.MenuLeft.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diogox.simpleweather.Api.Models.Weather.City.CityWeather;
import com.diogox.simpleweather.Api.Models.Weather.Forecast.CityForecast;
import com.diogox.simpleweather.Api.Models.Weather.Forecast.WeatherForecast;
import com.diogox.simpleweather.Api.Services.WeatherService;
import com.diogox.simpleweather.Api.WeatherClient;
import com.diogox.simpleweather.R;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityViewFragment extends Fragment {

    @BindView(R.id.city_img) ImageView mCityImg;
    @BindView(R.id.cityCurrentWeatherIcon) ImageView mCityCurrentWeatherIcon;
    @BindView(R.id.cityCurrentWeatherValue) TextView mCityCurrentWeather;
    @BindView(R.id.cityLowTemperatureValue) TextView mCityLowTemperature;
    @BindView(R.id.cityHighTemperatureValue) TextView mCityHighTemperature;
    @BindView(R.id.min_temp_unit) TextView mMinTemUnit;
    @BindView(R.id.max_temp_unit) TextView mMaxTemUnit;
    @BindView(R.id.citySunsetCountdown) TextView mCitySunsetCountdown;
    @BindView(R.id.citySunriseCountdown) TextView mCitySunriseCountdown;
    @BindView(R.id.cityPressureValue) TextView mCityPressure;
    @BindView(R.id.cityWindValue) TextView mCityWindValue;
    @BindView(R.id.wind_speed_unit) TextView mWindCityUnit;
    @BindView(R.id.cityMapBtn) ImageButton mCityMapBtn;
    @BindView(R.id.alertCityBtn) ImageButton mAlertCityBtn;
    @BindView(R.id.cityHumidityValue) TextView mCityHumidityValue;
    @BindView(R.id.cityInfoTimeBar) BubbleSeekBar mCityInfoTimeBar;

    private Context context;
    private View mView;
    private String mCityName;

    private String mLatitude;
    private String mLongitude;
    private String mImageURL;

    private SharedPreferences mPreferences;

    private int actualHour;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, mView);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        actualHour = new Date().getHours();

        mCityInfoTimeBar.setProgress(actualHour);
        mCityInfoTimeBar.setEnabled(false);

        // Get info passed into the fragment, if there is any.
        try {
            Bundle args = getArguments();
            mCityName = args.getString("name");
            mLatitude = args.getString("lat");
            mLongitude = args.getString("lon");
            mImageURL = args.getString("imgUrl");

            getWeatherForecast(mLatitude, mLongitude);
            getActualCity(mLatitude, mLongitude);

            Glide.with(mView)
                    .load(mImageURL)
                    .into(mCityImg);
        } catch (NullPointerException npe) {
            Log.wtf("CityView", "Couldn't retrieve arguments!");
        }

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Glide.with(mView)
                .load(mImageURL)
                .into(mCityImg);
    }

    public String getCityName() {
        return mCityName;
    }
    public String getCityLat() {
        return mLatitude;
    }
    public String getCityLon() {
        return mLongitude;
    }

    /**
     * Previsão metereológica
     *
     * @param latitude
     * @param longitude
     */
    private void getWeatherForecast(String latitude, String longitude) {

        Call<CityForecast> call = WeatherClient.weatherService().getWeatherForecast(
                latitude,
                longitude,
                "pt",
                WeatherService.MY_API_KEY
        );
        call.enqueue(new Callback<CityForecast>() {
            @Override
            public void onResponse(Call<CityForecast> call, Response<CityForecast> response) {

                if (response.isSuccessful()) {

                    CityForecast weather = response.body();

                    // Disponibilizar as informações
                    setCityInformationForecast(weather);
                }
            }

            @Override
            public void onFailure(Call<CityForecast> call, Throwable t) { }
        });

    }

    /**
     * Apresentar a informação da cidade obtida
     */
    private void setCityInformation(CityWeather cityWeather) {

        String icon = cityWeather.getWeather().get(0).getIcon();
        Glide.with(mView)
                .load("https://openweathermap.org/img/w/" + icon + ".png")
                .into(mCityCurrentWeatherIcon);

        mCityCurrentWeather.setText(cityWeather.getWeather().get(0).getDescription());

        switch (mPreferences.getString("si_temperature", "K")) {

            case "C":
                float tempMinC = cityWeather.getMain().getTemp_min() - 273.15f;
                float tempMaxC = cityWeather.getMain().getTemp_max() - 273.15f;
                mCityLowTemperature.setText(String.format("%.2f", tempMinC));
                mCityHighTemperature.setText(String.format("%.2f", tempMaxC));
                mMinTemUnit.setText("ºC");
                mMaxTemUnit.setText("ºC");
                break;

            case "F":
                float tempMinF = (cityWeather.getMain().getTemp_min() * (9/5)) + 32;
                float tempMaxF = (cityWeather.getMain().getTemp_max() * (9/5)) + 32;
                mCityLowTemperature.setText(String.format("%.2f", tempMinF));
                mCityHighTemperature.setText(String.format("%.2f", tempMaxF));
                mMinTemUnit.setText("ºF");
                mMaxTemUnit.setText("ºF");
                break;

            case "K":
                mCityLowTemperature.setText(String.format("%.2f", cityWeather.getMain().getTemp_min()));
                mCityHighTemperature.setText(String.format("%.2f", cityWeather.getMain().getTemp_max()));
                mMinTemUnit.setText("ºK");
                mMaxTemUnit.setText("ºK");
                break;

        }

        Date sunsetDate = new Date(cityWeather.getSys().getSunset() * 1000L);
        mCitySunsetCountdown.setText(new SimpleDateFormat("HH:mm").format(sunsetDate));

        Date sunriseDate = new Date(cityWeather.getSys().getSunrise() * 1000L);
        mCitySunriseCountdown.setText(new SimpleDateFormat("HH:mm").format(sunriseDate));

        mCityPressure.setText(String.format("%.2f", cityWeather.getMain().getPressure()));

        switch (mPreferences.getString("si_velocity", "MS")) {

            case "KMH":
                float speedKMH = cityWeather.getWind().getSpeed() * 3.6f;
                mCityWindValue.setText(String.format("%.2f", speedKMH));
                mWindCityUnit.setText("Km/h");
                break;

            case "MS":
                mCityWindValue.setText(String.format("%.2f", cityWeather.getWind().getSpeed()));
                mWindCityUnit.setText("m/s");
                break;
        }

        mCityHumidityValue.setText(String.format("%.2f", cityWeather.getMain().getHumidity()));
    }

    private void setCityInformationForecast(CityForecast weather) {

        List<WeatherForecast> list = weather.getList();

        mCityInfoTimeBar.setEnabled(true);
        mCityInfoTimeBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                if (progress == actualHour) {

                    getActualCity(mLatitude, mLongitude);

                } else {

                    if (progress < actualHour) {

                        Toast.makeText(
                                context,
                                "Não há previsões para as horas anteriores",
                                Toast.LENGTH_SHORT).show();
                        mCityInfoTimeBar.setProgress(actualHour);

                    } else {

                        WeatherForecast forecast = null;

                        for (int i = 0; i < list.size(); i++) {
                            int hour = new Date(list.get(i).getDt() * 1000L).getHours();

                            int day = new Date().getDay();
                            int dayForecast = new Date(list.get(i).getDt() * 1000L).getDay();

                            if ((hour == progress) && (day == dayForecast)) {
                                forecast = list.get(i);
                                break;
                            }
                        }

                        if (forecast != null)
                            setForecastInformation(forecast);
                    }

                }
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

    }

    private void getActualCity(String latitude, String longitude) {

        Call<CityWeather> call = WeatherClient.weatherService().getWeatherByCityCoordinates(
                latitude,
                longitude,
                "pt",
                WeatherService.MY_API_KEY
        );
        call.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {

                if (response.isSuccessful()) {

                    CityWeather cityWeather = response.body();

                    // Disponibilizar as informações
                    setCityInformation(cityWeather);
                } else {

                }

            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {

            }
        });

    }

    private void setForecastInformation(WeatherForecast forecast) {

        String icon = forecast.getWeather().get(0).getIcon();
        Glide.with(mView)
                .load("https://openweathermap.org/img/w/" + icon + ".png")
                .into(mCityCurrentWeatherIcon);

        mCityCurrentWeather.setText(forecast.getWeather().get(0).getDescription());

        switch (mPreferences.getString("si_temperature", "K")) {

            case "C":
                float tempMinC = forecast.getMain().getTemp_min() - 273.15f;
                float tempMaxC = forecast.getMain().getTemp_max() - 273.15f;
                mCityLowTemperature.setText(String.format("%.2f", tempMinC));
                mCityHighTemperature.setText(String.format("%.2f", tempMaxC));
                mMinTemUnit.setText("ºC");
                mMaxTemUnit.setText("ºC");
                break;

            case "F":
                float tempMinF = (forecast.getMain().getTemp_min() * (9/5)) + 32;
                float tempMaxF = (forecast.getMain().getTemp_max() * (9/5)) + 32;
                mCityLowTemperature.setText(String.format("%.2f", tempMinF));
                mCityHighTemperature.setText(String.format("%.2f", tempMaxF));
                mMinTemUnit.setText("ºF");
                mMaxTemUnit.setText("ºF");
                break;

            case "K":
                mCityLowTemperature.setText(String.format("%.2f", forecast.getMain().getTemp_min()));
                mCityHighTemperature.setText(String.format("%.2f", forecast.getMain().getTemp_max()));
                mMinTemUnit.setText("ºK");
                mMaxTemUnit.setText("ºK");
                break;

        }

        mCityPressure.setText(String.format("%.2f", forecast.getMain().getPressure()));

        switch (mPreferences.getString("si_velocity", "MS")) {

            case "KMH":
                float speedKMH = forecast.getWind().getSpeed() * 3.6f;
                mCityWindValue.setText(String.format("%.2f", speedKMH));
                mWindCityUnit.setText("Km/h");
                break;

            case "MS":
                mCityWindValue.setText(String.format("%.2f", forecast.getWind().getSpeed()));
                mWindCityUnit.setText("m/s");
                break;
        }

        mCityHumidityValue.setText(String.format("%.2f", forecast.getMain().getHumidity()));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", mCityName);
        outState.putString("lon", mLongitude);
        outState.putString("lat", mLatitude);
        outState.putString("imgUrl", mImageURL);
    }

}
