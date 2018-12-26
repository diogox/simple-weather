package com.diogox.simpleweather.MenuLeft.Fragments.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.Api.Models.Database.AppDb;
import com.diogox.simpleweather.MenuLeft.Fragments.Adapters.AlertAdapter;

import java.util.List;

public class NewAlertTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private List<Alert> alertList;
    private AlertAdapter alertAdapter;
    private Alert newAlert;

    public NewAlertTask(Activity activity, List<Alert> alerts, AlertAdapter adapter, Alert alert) {
        this.activity = activity;
        this.alertList = alerts;
        this.alertAdapter = adapter;
        this.newAlert = alert;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        while (!isCancelled()) {

            AppDb.getInstance(activity.getApplicationContext()).alertDAO().insertAlert(newAlert);
            break;

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        int index = alertList.size();

        alertList.add(index, newAlert);
        alertAdapter.notifyItemInserted(index);

        Toast.makeText(activity.getApplicationContext(), "Sucesso!!!", Toast.LENGTH_SHORT).show();

        activity.onBackPressed();
    }
}
