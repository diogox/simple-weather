package com.diogox.simpleweather.MenuLeft.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diogox.simpleweather.Api.Models.Weather.CityWeather;
import com.diogox.simpleweather.Api.WeatherClient;
import com.diogox.simpleweather.Api.Services.WeatherService;
import com.diogox.simpleweather.MenuLeft.Location.GPSLocation;
import com.diogox.simpleweather.R;
import com.xw.repo.BubbleSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityViewFragment extends Fragment {

    @BindView(R.id.city_img) ImageView mCityImg;
    @BindView(R.id.cityCurrentWeatherIcon) ImageView mCityCurrentWeatherIcon;
    @BindView(R.id.cityCurrentWeatherValue) TextView mcityCurrentWeather;
    @BindView(R.id.cityLowTemperatureValue) TextView mCityLowTemperature;
    @BindView(R.id.cityHighTemperatureValue) TextView mCityHighTemperature;
    @BindView(R.id.citySunsetCountdown) TextView mCitySunsetCountdown;
    @BindView(R.id.citySunriseCountdown) TextView mCitySunriseCountdown;
    @BindView(R.id.cityPressureValue) TextView mCityPressure;
    @BindView(R.id.cityWindValue) TextView mCityWindValue;
    @BindView(R.id.cityMapBtn) ImageButton mCityMapBtn;
    @BindView(R.id.alertCityBtn) ImageButton mAlertCityBtn;
    @BindView(R.id.cityInfoTimeBar) BubbleSeekBar mCityInfoTimeBar;

    private Context context;
    private String mCityName;
    private GPSLocation gpsLocation;

    private double mLatitude;
    private double mLongitude;

    private CityWeather mCityWeather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();


        // For testing purposes
        mCityName = "Felgueiras";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View mContentView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, mContentView);

        getLocation();

        Glide.with(mContentView)
             .load("http://www.expressofelgueiras.com/wp-content/uploads/2016/05/Cidade-de-Felgueiras-.jpg")
             .into(mCityImg);

        return mContentView;
    }

    /**
     * Obter a localização atual
     */
    private void getLocation() {

        gpsLocation = new GPSLocation(context);

        if (gpsLocation.isCanGetLocation()) {

            mLatitude  = gpsLocation.getLatitude();
            mLongitude = gpsLocation.getLongitude();

        } else {

            gpsLocation.showSettingsAlert();

            mLatitude  = gpsLocation.getLatitude();
            mLongitude = gpsLocation.getLongitude();

        }

        System.out.println("********* LAT: " + mLatitude);
        System.out.println("********* LON: " + mLongitude);

        getActualCity(mLatitude, mLongitude);

    }

    /**
     * Obter a cidade em função da localização atual
     *
     * @param latitude latitude
     * @param longitude longitude
     */
    private void getActualCity(double latitude, double longitude) {

        Call<CityWeather> call = WeatherClient.weatherService().getWeatherByCityCoordinates(
                latitude,
                longitude,
                WeatherService.MY_API_KEY
        );
        call.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {

                if (response.isSuccessful()) {

                    mCityWeather = response.body();
                    System.out.println("******** " + mCityWeather.toString());

                } else {

                    System.out.println("************ " + response.code() + " **************");
                    System.out.println("************ " + response.message() + " **************");

                }

                System.out.println("************ " + response.code() + " **************");

            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {

                System.out.println("************ " + t.getCause() + " **************");
                System.out.println("************ " + t.getMessage() + " **************");

            }
        });

    }

}
