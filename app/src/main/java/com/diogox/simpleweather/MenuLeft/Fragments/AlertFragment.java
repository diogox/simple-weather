package com.diogox.simpleweather.MenuLeft.Fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.MenuRight.CityViewModel;
import com.diogox.simpleweather.R;
import com.like.LikeButton;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.button_choose_city) Button chooseCity;
    @BindView(R.id.spinner_parameter) Spinner spinnerParameter;
    @BindView(R.id.spinner_parameter_value) Spinner spinnerParameterValue;
    @BindView(R.id.button_create_alert) Button createAlert;

    private Context context;
    private View mView;
    private CityViewModel mCityViewModel;

    private City citySelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_alert, container, false);

        ButterKnife.bind(this, mView);

        chooseCity.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                R.array.weather_params,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParameter.setAdapter(adapter);
        spinnerParameter.setOnItemSelectedListener(this);

        return mView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_choose_city) {

            // Cities view model
            mCityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);

            List<City> cityList = mCityViewModel.getCities();

            String[] cityNames = new String[cityList.size()];

            for (int i = 0; i < cityNames.length; i++) {
                cityNames[i] = cityList.get(0).getName();
            }

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            mBuilder.setTitle("Escolha a cidade:");
            mBuilder.setSingleChoiceItems(cityNames, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    citySelected = cityList.get(i);
                    dialog.dismiss();

                }
            });
            mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = mBuilder.create();
            dialog.show();

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:
                ArrayAdapter<CharSequence> tempAdapter = ArrayAdapter.createFromResource(
                        context,
                        R.array.weather_values_temp,
                        android.R.layout.simple_spinner_item
                );
                tempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerParameterValue.setAdapter(tempAdapter);
                break;

            case 1:
                ArrayAdapter<CharSequence> precipAdapter = ArrayAdapter.createFromResource(
                        context,
                        R.array.weather_values_precip,
                        android.R.layout.simple_spinner_item
                );
                precipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerParameterValue.setAdapter(precipAdapter);
                break;

            case 2:
                ArrayAdapter<CharSequence> windAdapter = ArrayAdapter.createFromResource(
                        context,
                        R.array.weather_values_wind,
                        android.R.layout.simple_spinner_item
                );
                windAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerParameterValue.setAdapter(windAdapter);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
