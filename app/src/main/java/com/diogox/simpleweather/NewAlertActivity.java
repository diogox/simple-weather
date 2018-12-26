package com.diogox.simpleweather;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.Api.Models.Database.Alerts.AlertType;
import com.diogox.simpleweather.Api.Models.Database.AppDb;
import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.MenuRight.CityViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewAlertActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.button_choose_city) Button chooseCity;
    @BindView(R.id.spinner_parameter) Spinner param;
    @BindView(R.id.minParamValue) EditText minParamValue;
    @BindView(R.id.maxParamValue) EditText maxParamValue;
    @BindView(R.id.button_create_alert) Button createAlert;

    private CityViewModel mCityViewModel;

    private City citySelected;
    private AlertType alertType = AlertType.Temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alert);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo alerta");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(NewAlertActivity.this);

        chooseCity.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                NewAlertActivity.this,
                R.array.weather_params,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        param.setAdapter(adapter);
        param.setOnItemSelectedListener(this);

        createAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double minValue;
                try {
                    minValue = Double.valueOf(minParamValue.getText().toString());
                } catch(NumberFormatException nfe) {
                    minValue = null;
                }

                Double maxValue;
                try {
                    maxValue = Double.valueOf(maxParamValue.getText().toString());
                } catch(NumberFormatException nfe) {
                    maxValue = null;
                }

                if (citySelected != null) {

                    // Create alert
                    Alert alert = new Alert(citySelected, alertType, minValue, maxValue);

                    // Add alert
                    AppDb.getInstance(NewAlertActivity.this).alertDAO().insertAlert(alert);

                    // TODO: Go back to list of alerts

                } else {

                    chooseCity.setError("Não definido");
                    Toast.makeText(NewAlertActivity.this, "Não definiu a cidade do alerta", Toast.LENGTH_SHORT).show();

                }

            }
        });

        AppDb.getInstance(NewAlertActivity.this).alertDAO().getAllAlerts().observe(this, (alerts) -> Log.d("ALERTS", "NEW ALERT ADDED!"));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_choose_city) {

            // Cities view model
            mCityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);

            List<City> cityList = mCityViewModel.getCities();

            if (!cityList.isEmpty()) {

                String[] cityNames = new String[cityList.size()];

                for (int i = 0; i < cityNames.length; i++) {
                    cityNames[i] = cityList.get(i).getName();
                }

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewAlertActivity.this);
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

            } else {

                Toast.makeText(NewAlertActivity.this, "Não possui cidades favoritas", Toast.LENGTH_SHORT).show();

            }

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
