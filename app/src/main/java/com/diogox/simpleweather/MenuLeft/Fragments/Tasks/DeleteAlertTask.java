package com.diogox.simpleweather.MenuLeft.Fragments.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.Api.Models.Database.AppDb;
import com.diogox.simpleweather.MenuLeft.Fragments.Adapters.AlertAdapter;

import java.util.List;

public class DeleteAlertTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private List<Alert> alertList;
    private AlertAdapter alertAdapter;
    private Alert alertToDelete;

    public DeleteAlertTask(Context context, List<Alert> alerts, AlertAdapter adapter, Alert alert) {
        this.context       = context;
        this.alertList     = alerts;
        this.alertAdapter  = adapter;
        this.alertToDelete = alert;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        while (!isCancelled()) {

            AppDb.getInstance(context).alertDAO().deleteAlert(alertToDelete);
            break;

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        int index = alertList.indexOf(alertToDelete);

        alertList.remove(alertToDelete);
        alertAdapter.notifyItemRemoved(index);

        Toast.makeText(context, "Eliminado!!!", Toast.LENGTH_SHORT).show();

        if (alertList.isEmpty())
            Toast.makeText(context, "Não existem mais alertas", Toast.LENGTH_SHORT).show();
    }
}
