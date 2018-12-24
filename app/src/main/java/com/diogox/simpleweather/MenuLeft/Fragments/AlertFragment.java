package com.diogox.simpleweather.MenuLeft.Fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.Api.Models.Database.Alerts.AlertType;
import com.diogox.simpleweather.Api.Models.Database.AppDb;
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
    @BindView(R.id.spinner_parameter) Spinner param;
    @BindView(R.id.minParamValue) EditText minParamValue;
    @BindView(R.id.maxParamValue) EditText maxParamValue;
    @BindView(R.id.button_create_alert) Button createAlert;

    private Context context;
    private View mView;
    private CityViewModel mCityViewModel;

    private City citySelected;
    private AlertType alertType = AlertType.Temperature;

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
        param.setAdapter(adapter);
        param.setOnItemSelectedListener(this);

        createAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double minValue = Double.valueOf( minParamValue.getText().toString() );
                double maxValue = Double.valueOf( maxParamValue.getText().toString() );

                // Create alert
                Alert alert = new Alert(citySelected, alertType, minValue, maxValue);

                // Add alert
                AppDb.getInstance(context).alertDAO().insertAlert(alert);

                // TODO: Go back to list of alerts
            }
        });

        AppDb.getInstance(context).alertDAO().getAllAlerts().observe(this, (alert) -> Log.d("ALERTS", "NEW ALERT ADDED!"));

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

                // Set param type
                alertType = AlertType.Temperature;
                break;

            case 1:

                // Set param type
                alertType = AlertType.Humidity;
                break;

            case 2:

                // Set param type
                alertType = AlertType.WindSpeed;
                break;

            case 3:

                // Set param type
                alertType = AlertType.Pressure;
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
