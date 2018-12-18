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
import com.diogox.simpleweather.R;
import com.xw.repo.BubbleSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityViewFragment extends Fragment {

    private Context context;
    private String mCityName;

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

        Glide.with(mContentView)
             .load("http://www.expressofelgueiras.com/wp-content/uploads/2016/05/Cidade-de-Felgueiras-.jpg")
             .into(mCityImg);

        return mContentView;
    }
}
