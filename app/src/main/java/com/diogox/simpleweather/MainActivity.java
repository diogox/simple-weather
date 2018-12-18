package com.diogox.simpleweather;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.MenuLeft.Fragments.AlertFragment;
import com.diogox.simpleweather.MenuLeft.Fragments.HomeFragment;
import com.diogox.simpleweather.MenuLeft.Fragments.MapFragment;
import com.diogox.simpleweather.MenuRight.CityViewModel;
import com.diogox.simpleweather.MenuRight.DrawerCityAdapter;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.left_drawer) NavigationView mLeftDrawer;
    @BindView(R.id.right_drawer) NavigationView mRightDrawer;
    @BindView(R.id.cities_drawer_btn) ImageButton mRightDrawerBtn;
    @BindView(R.id.menu_btn) ImageButton mMenuBtn;
    private CityViewModel mCityViewModel;

    private List<City> cityList = new LinkedList<>();
    private DrawerCityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);

        mLeftDrawer.setNavigationItemSelectedListener(this);
        mRightDrawer.setNavigationItemSelectedListener(this);

        View drawerRight = mRightDrawer.getHeaderView(0);

        cityAdapter = new DrawerCityAdapter(mRightDrawer.getContext(), cityList);
        RecyclerView recyclerView = findViewById(R.id.drawerCityList);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRightDrawer.getContext()));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mRightDrawer.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        HomeFragment homeFragment = new HomeFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, homeFragment);
        fragmentTransaction.commit();

        // Cities view model
        mCityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);

        // Listen for changes
        mCityViewModel.getAllCities().observe(this, cities -> cityAdapter.setData(cities));

    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {

            // Close left drawer
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (mDrawer.isDrawerOpen(GravityCompat.END)) {

            // Close right drawer
            mDrawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_principal) { // Home

            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_map) { // Map

            MapFragment mapFragment = new MapFragment();
            fragmentTransaction.replace(R.id.fragment_container, mapFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_alert) { // Alerts

            AlertFragment alertFragment = new AlertFragment();
            fragmentTransaction.replace(R.id.fragment_container, alertFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_settings) { // Settings activity

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {

            Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.menu_btn)
    public void onClickMenuBtn() {
        mDrawer.openDrawer(GravityCompat.START);
    }

    @OnClick(R.id.cities_drawer_btn)
    public void onClickCitiesBtn() {
        mDrawer.openDrawer(GravityCompat.END);
    }
}
