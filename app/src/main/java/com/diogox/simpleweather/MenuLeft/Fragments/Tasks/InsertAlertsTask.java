package com.diogox.simpleweather.MenuLeft.Fragments.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.Api.Models.Database.AppDb;
import com.diogox.simpleweather.MenuLeft.Fragments.Adapters.AlertAdapter;

import java.util.List;

public class InsertAlertsTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private List<Alert> activeAlerts;
    private List<Alert> alertList;
    private AlertAdapter alertAdapter;

    public InsertAlertsTask(Context context, List<Alert> alerts, AlertAdapter adapter) {
        this.context = context;
        this.alertList = alerts;
        this.alertAdapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        while (!isCancelled()) {

            activeAlerts = AppDb.getInstance(context).alertDAO().getAlerts();
            break;

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        for (int i = 0; i < activeAlerts.size(); i++) {

            alertList.add(activeAlerts.get(i));
            alertAdapter.notifyItemInserted(i);

        }

        if (alertList.isEmpty())
            Toast.makeText(context, "Não existem alertas definidos", Toast.LENGTH_SHORT).show();
    }
}
