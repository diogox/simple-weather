package com.diogox.simpleweather.MenuLeft.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diogox.simpleweather.Api.GeocodeClient;
import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.Api.Models.Geocode.GeocodeLocationResults;
import com.diogox.simpleweather.Api.Models.Places.CityDetails;
import com.diogox.simpleweather.Api.Models.Weather.Area.AreaWeather;
import com.diogox.simpleweather.Api.PlacesClient;
import com.diogox.simpleweather.Api.Services.WeatherService;
import com.diogox.simpleweather.Api.WeatherClient;
import com.diogox.simpleweather.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment {

    private Context context;
    private MapView mMapView;
    private GoogleMap mGoogleMap;

    private String mLatitude;
    private String mLongitude;

    private List<City> mCurrentCityList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mContentView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = mContentView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            Bundle args = getArguments();
            mLatitude = args.getString("lat");
            mLongitude = args.getString("lon");
            mCurrentCityList = (List<City>) args.getSerializable("cityList");
        } catch (NullPointerException npe) {}

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mGoogleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng city = new LatLng(Float.parseFloat(mLatitude), Float.parseFloat(mLongitude));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(city).zoom(12).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                        GeocodeClient.getInstance()
                                .getCityByLocation(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude))
                                .enqueue(new Callback<GeocodeLocationResults>() {
                                    @Override
                                    public void onResponse(Call<GeocodeLocationResults> call, Response<GeocodeLocationResults> response) {
                                        String cityName = response.body().getName();
                                        String cityId= response.body().getPlaceId();
                                        PlacesClient.getInstance().getCityDetails(response.body().getPlaceId()).enqueue(new Callback<CityDetails>() {
                                            @Override
                                            public void onResponse(Call<CityDetails> call, Response<CityDetails> response) {
                                                CityDetails locationCity = response.body();

                                                // Create Fragment with info
                                                Bundle bundle = new Bundle();

                                                bundle.putString("name", cityName);
                                                bundle.putString("lat", locationCity.getLat());
                                                bundle.putString("lon", locationCity.getLon());
                                                bundle.putString("imgUrl", locationCity.getPhotoUrl());
                                                CityViewFragment cityView = new CityViewFragment();
                                                cityView.setArguments(bundle);

                                                City city = new City(cityId,
                                                        cityName,
                                                        "",
                                                        locationCity.getLat(),
                                                        locationCity.getLon(),
                                                        locationCity.getPhotoUrl());
                                                mCurrentCityList.add(city);

                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.fragment_container, cityView);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
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
                });

                mGoogleMap.setOnInfoWindowClickListener(Marker::showInfoWindow);
            }
        });

        return mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            Bundle args = getArguments();
            mLatitude = args.getString("lat");
            mLongitude = args.getString("lon");
        } catch (NullPointerException npe) {}
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
