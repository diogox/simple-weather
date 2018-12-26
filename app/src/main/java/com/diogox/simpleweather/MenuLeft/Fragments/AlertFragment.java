package com.diogox.simpleweather.MenuLeft.Fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.diogox.simpleweather.Api.Models.Database.Alerts.Alert;
import com.diogox.simpleweather.Api.Models.Database.Alerts.AlertType;
import com.diogox.simpleweather.Api.Models.Database.AppDb;
import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.MenuRight.CityViewModel;
import com.diogox.simpleweather.NewAlertActivity;
import com.diogox.simpleweather.R;
import com.like.LikeButton;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertFragment extends Fragment {

    private Context context;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_alert, container, false);

        ButterKnife.bind(this, mView);


        return mView;
    }


}
