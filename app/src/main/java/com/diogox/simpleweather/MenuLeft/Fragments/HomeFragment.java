package com.diogox.simpleweather.MenuLeft.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.diogox.simpleweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private Context context;

    @BindView(R.id.city_img) ImageView mCityImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
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
