package com.diogox.simpleweather.MenuLeft.Fragments.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.R;

import java.util.List;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    private Context mContext;
    private List<Alert> mAlerts;

    public static AlertAdapter adapter;

    public AlertAdapter(Context context, List<Alert> alerts) {
        this.mContext = context;
        this.mAlerts  = alerts;
        adapter       = this;
    }

    public class AlertViewHolder extends RecyclerView.ViewHolder {

        protected TextView nameCity;
        protected TextView typeAlert;
        protected TextView minValue;
        protected TextView maxValue;

        protected AlertViewHolder(View itemView) {
            super(itemView);

            nameCity  = itemView.findViewById(R.id.city_name_alert);
            typeAlert = itemView.findViewById(R.id.type_alert);
            minValue  = itemView.findViewById(R.id.min_value_alert);
            maxValue  = itemView.findViewById(R.id.max_value_alert);
        }

    }

    @Override
    public AlertViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        // Get layout inflater from context
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate layout
        View alertView = inflater.inflate(R.layout.item_recycler_view_alerts, viewGroup, false);

        // Retun a new holder instance
        return new AlertViewHolder(alertView);
    }

    @Override
    public void onBindViewHolder(AlertViewHolder alertViewHolder, int position) {

        final Alert alert = mAlerts.get(position);

        TextView name = alertViewHolder.nameCity;
        name.setText(alert.getCity().getName());

        TextView type = alertViewHolder.typeAlert;
        type.setText(String.valueOf(alert.getValueType()));

        TextView min = alertViewHolder.minValue;
        min.setText(String.valueOf(alert.getMinValueTrigger()));

        TextView max = alertViewHolder.maxValue;
        max.setText(String.valueOf(alert.getMaxValueTrigger()));
    }

    @Override
    public int getItemCount() {
        return mAlerts.size();
    }

}
