package com.diogox.simpleweather.MenuLeft.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.MenuLeft.Fragments.Adapters.AlertAdapter;
import com.diogox.simpleweather.MenuLeft.Fragments.Tasks.InsertAlertsTask;
import com.diogox.simpleweather.NewAlertActivity;
import com.diogox.simpleweather.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertFragment extends Fragment {

    @BindView(R.id.fa_new_alert) FloatingActionButton newAlert;

    private RecyclerView recyclerView;
    public static AlertAdapter mAlertAdapter;
    public static List<Alert> mAlerts = new LinkedList<>();

    private Context context;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_alert, container, false);

        ButterKnife.bind(this, mView);

        recyclerView = mView.findViewById(R.id.alerts_list);

        mAlerts.clear();
        mAlertAdapter = new AlertAdapter(context, mAlerts);
        recyclerView.setAdapter(mAlertAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
        );
        recyclerView.addItemDecoration(itemDecoration);

        getAlerts();

        newAlert.setOnClickListener(v -> {
            Intent i = new Intent(context, NewAlertActivity.class);
            startActivity(i);
        });

        return mView;
    }

    private void getAlerts() {

        for (int i = 0; i < mAlerts.size(); i++) {
            mAlerts.remove(i);
            mAlertAdapter.notifyItemRemoved(i);
        }

        InsertAlertsTask insertAlertsTask = new InsertAlertsTask(context, mAlerts, mAlertAdapter);
        insertAlertsTask.execute();

    }

}
