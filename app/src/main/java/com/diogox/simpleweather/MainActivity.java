package com.diogox.simpleweather;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.diogox.simpleweather.Api.GeocodeClient;
import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.Api.Models.Geocode.GeocodeLocationResults;
import com.diogox.simpleweather.Api.Models.Places.AutocompleteResultItem;
import com.diogox.simpleweather.Api.Models.Places.AutocompleteResults;
import com.diogox.simpleweather.Api.Models.Places.CityDetails;
import com.diogox.simpleweather.Api.PlacesClient;
import com.diogox.simpleweather.MenuLeft.Fragments.AlertFragment;
import com.diogox.simpleweather.MenuLeft.Fragments.CityViewFragment;
import com.diogox.simpleweather.MenuLeft.Fragments.MapFragment;
import com.diogox.simpleweather.MenuLeft.Location.GPSLocation;
import com.diogox.simpleweather.MenuRight.CityViewModel;
import com.diogox.simpleweather.MenuRight.DrawerCityAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mCityName;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.star_button) LikeButton mStarBtn;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.left_drawer) NavigationView mLeftDrawer;
    @BindView(R.id.right_drawer) NavigationView mRightDrawer;
    @BindView(R.id.cities_drawer_btn) ImageButton mRightDrawerBtn;
    @BindView(R.id.searchCitiesInput) EditText mRightDrawerSearch;
    @BindView(R.id.menu_btn) ImageButton mMenuBtn;

    private CityViewModel mCityViewModel;

    private List<City> cityList = new LinkedList<>();
    private DrawerCityAdapter cityAdapter;
    private List<City> mCurrentCityList = new LinkedList<>();
    private String mCurrentCityLat;
    private String mCurrentCityLon;

    private static boolean isFirstStartup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mCityName = mToolbar.findViewById(R.id.appBarCityName);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);

        mLeftDrawer.setNavigationItemSelectedListener(this);
        mRightDrawer.setNavigationItemSelectedListener(this);

        View drawerRight = mRightDrawer.getHeaderView(0);

        cityAdapter = new DrawerCityAdapter(mRightDrawer.getContext(), cityList, city -> {

            mCurrentCityLat = city.getLat();
            mCurrentCityLon = city.getLon();

            mCurrentCityList.add(city);

            // Create Fragment with info
            Bundle bundle = new Bundle();

            mCityName.setText(city.getName());
            bundle.putString("name", city.getName());
            bundle.putString("lat", city.getLat());
            bundle.putString("lon", city.getLon());
            bundle.putString("imgUrl", city.getPhotoUrl());
            CityViewFragment cityView = new CityViewFragment();
            cityView.setArguments(bundle);

            // Start CityView fragment
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, cityView);
            transaction.commit();
            transaction.addToBackStack(null);

            City favoritedCity = mCityViewModel.findCity(city);
            if (favoritedCity != null) {
                mStarBtn.setLiked(true);
            } else {
                mStarBtn.setLiked(false);
            }

            mDrawer.closeDrawer(GravityCompat.END);
        });

        RecyclerView recyclerView = findViewById(R.id.drawerCityList);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRightDrawer.getContext()));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mRightDrawer.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        CityViewFragment homeFragment = new CityViewFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentManager.OnBackStackChangedListener onBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();
                CityViewFragment fragment = (CityViewFragment) manager.findFragmentById(R.id.fragment_container);
                fragment.onResume();
                mCityName.setText(fragment.getCityName());

                mCurrentCityLat = fragment.getCityLat();
                mCurrentCityLon = fragment.getCityLon();

                City currentCity = null;
                for (City city : mCurrentCityList) {
                    if (mCurrentCityLat.equals(city.getLat()) && mCurrentCityLon.equals(city.getLon())) {
                        currentCity = city;
                        break;
                    }
                }

                if (currentCity != null) {
                    City favoritedCity = mCityViewModel.findCity(currentCity);
                    if (favoritedCity != null) {
                        mStarBtn.setLiked(true);
                    } else {
                        mStarBtn.setLiked(false);
                    }
                } else {
                    mStarBtn.setLiked(false);
                }
            }
        };
        fragmentManager.addOnBackStackChangedListener(onBackStackChangedListener);

        if (isFirstStartup) {
            showCurrentCityForecast();
            isFirstStartup = false;
        }

        mCurrentCityLat = homeFragment.getCityLat();
        mCurrentCityLon = homeFragment.getCityLon();

        // Cities view model
        mCityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);

        // Listen for changes
        mCityViewModel.getAllCities().observe(this, cities -> cityAdapter.setData(cities));

        mRightDrawerSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                // If it's empty, return
                String searchQuery = mRightDrawerSearch.getText().toString();
                if (searchQuery.equals("")) {
                    return false;
                }

                PlacesClient.getInstance().searchCitiesByName(searchQuery).enqueue(new Callback<AutocompleteResults>() {
                    @Override
                    public void onResponse(Call<AutocompleteResults> call, Response<AutocompleteResults> response) {
                        List<AutocompleteResultItem> results = response.body().getPredictions();

                        List<City> cityResults = new ArrayList<>();
                        cityAdapter.setData(cityResults);
                        for (int i = 0; i < results.size(); i++) {
                            cityResults.add(new City("", "", "", "", "", ""));
                        }

                        for (AutocompleteResultItem item : results) {
                            // Get details
                            PlacesClient.getInstance().getCityDetails(item.getPlaceId()).enqueue(new Callback<CityDetails>() {
                                @Override
                                public void onResponse(Call<CityDetails> call, Response<CityDetails> response) {
                                    CityDetails details = response.body();

                                    City city = new City(item.getPlaceId(),
                                            item.getName(),
                                            "",
                                            details.getLat(),
                                            details.getLon(),
                                            details.getPhotoUrl());

                                    int addToIndex = results.indexOf(item);
                                    cityResults.set(addToIndex, city);
                                    cityAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<CityDetails> call, Throwable t) {
                                    Log.d("DRAWER", "FAILED TO GET DETAILS: " + t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<AutocompleteResults> call, Throwable t) {
                        Log.d("DRAWER", "FAILED TO GET INFO: " + t.getMessage());
                    }
                });
                return false;
            }
        });

        LifecycleOwner owner = this;
        // Listen for
        DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                mRightDrawerSearch.setText("");
                mCityViewModel.getAllCities().observe(owner, cities -> cityAdapter.setData(cities));
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        };
        mDrawer.addDrawerListener(drawerListener);

        mStarBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                City currentCity = null;
                for (City city : mCurrentCityList) {
                    if (mCurrentCityLat.equals(city.getLat()) && mCurrentCityLon.equals(city.getLon())) {
                        currentCity = city;
                        break;
                    }
                }

                if (currentCity == null) {
                    likeButton.setLiked(false);
                } else {
                    mCityViewModel.saveCity(currentCity);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                City currentCity = null;
                for (City city : mCurrentCityList) {
                    if (mCurrentCityLat.equals(city.getLat()) && mCurrentCityLon.equals(city.getLon())) {
                        currentCity = city;
                        break;
                    }
                }

                if (currentCity == null) {
                    throw new Error("Can't remove from favorites");
                } else {
                    mCityViewModel.deleteCity(currentCity);
                }
            }
        });

        AlertJob.scheduleJob();
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, AlertService.class);
        startService(serviceIntent);

        // For starting service without the app open
        //ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, AlertService.class);
        stopService(serviceIntent);
    }

    private void showCurrentCityForecast() {

        GPSLocation gpsLocation = new GPSLocation(getApplicationContext());
        final LatLng latLng;

        if (gpsLocation.isCanGetLocation()) {

            latLng = new LatLng(gpsLocation.getLatitude(), gpsLocation.getLongitude());

        } else {

            gpsLocation.showSettingsAlert();
            latLng = new LatLng(gpsLocation.getLatitude(), gpsLocation.getLongitude());
        }

        GeocodeClient.getInstance()
                .getCityByLocation(
                    String.valueOf(latLng.latitude),
                    String.valueOf(latLng.longitude)
                )
                .enqueue(new Callback<GeocodeLocationResults>() {
                    @Override
                    public void onResponse(Call<GeocodeLocationResults> call, Response<GeocodeLocationResults> response) {
                        GeocodeLocationResults results = response.body();
                        City currentCity = new City(results.getPlaceId(),
                                results.getName(),
                                "",
                                String.valueOf(latLng.latitude),
                                String.valueOf(latLng.longitude),
                                ""
                                );

                        PlacesClient.getInstance()
                                .getCityDetails(results.getPlaceId())
                                .enqueue(new Callback<CityDetails>() {
                                    @Override
                                    public void onResponse(Call<CityDetails> call, Response<CityDetails> response) {
                                        CityDetails cityDetails = response.body();
                                        String photoUrl = cityDetails.getPhotoUrl();
                                        currentCity.setPhotoUrl(photoUrl);

                                        showForecast(currentCity);
                                    }

                                    @Override
                                    public void onFailure(Call<CityDetails> call, Throwable t) {

                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<GeocodeLocationResults> call, Throwable t) {

                    }
                });
    }

    private void showForecast(City city) {
        CityViewFragment cityView = new CityViewFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", city.getName());
        bundle.putString("lat", city.getLat());
        bundle.putString("lon", city.getLon());
        bundle.putString("imgUrl", city.getPhotoUrl());
        cityView.setArguments(bundle);

        mCurrentCityList.add(city);

        // Start CityView fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, cityView);
        transaction.commit();
        transaction.addToBackStack(null);
    }


    @Override
    protected void onResume() {
        super.onResume();

        showCurrentCityForecast();
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

            //super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_principal) { // Home

            CityViewFragment homeFragment = new CityViewFragment();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment);
            fragmentTransaction.commit();
            mCityName.setText(homeFragment.getCityName());

            FragmentManager fm = getSupportFragmentManager();
            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }

        } else if (id == R.id.nav_map) { // Map

            Bundle bundle = new Bundle();
            bundle.putString("lat", mCurrentCityLat);
            bundle.putString("lon", mCurrentCityLon);
            bundle.putSerializable("cityList", (Serializable) mCurrentCityList);

            MapFragment mapFragment = new MapFragment();
            mapFragment.setArguments(bundle);
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
