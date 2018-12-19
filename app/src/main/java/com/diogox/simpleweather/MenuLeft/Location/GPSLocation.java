package com.diogox.simpleweather.MenuLeft.Location;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class GPSLocation extends Service implements LocationListener {

    /**
     * Distância mínima para obter atualizações
     */
    private static final long MIN_DISTANCE_FOR_UPDATES = 10;
    /**
     * Tempo mínimo para o intervalo das atualizações
     */
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1;

    private LocationManager mLocationManager;

    private boolean isGpsEnabled;
    private boolean isNetworkEnabled;
    private boolean canGetLocation;

    private Location mLocation;
    private double latitude;
    private double longitude;

    private Context mContext;


    public GPSLocation(Context context) {
        this.mContext         = context;
        this.isGpsEnabled     = false;
        this.isNetworkEnabled = false;
        this.canGetLocation   = false;

        getLocation();
    }


    public Location getLocation() {

        try {

            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // GPS status
            isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Network status
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGpsEnabled && !isNetworkEnabled) {

            } else {

                this.canGetLocation = true;

                // Get the first location
                if (isNetworkEnabled) {

                    if (ActivityCompat.checkSelfPermission(mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(mContext,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        ) {

                        requestPermissions();

                    }

                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BETWEEN_UPDATES,
                            MIN_DISTANCE_FOR_UPDATES,
                            this
                    );

                    Log.d("NetWork", "NetWork");
                    if (mLocationManager != null) {

                        mLocation = mLocationManager.
                                getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (mLocation != null) {
                            this.latitude  = mLocation.getLatitude();
                            this.longitude = mLocation.getLongitude();
                        }

                    }

                }

                // Get location with GPS services
                if (isGpsEnabled) {

                    if (mLocation == null) {

                        if (ActivityCompat.checkSelfPermission(mContext,
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(mContext,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            ) {

                            requestPermissions();

                        }

                        mLocationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BETWEEN_UPDATES,
                                MIN_DISTANCE_FOR_UPDATES,
                                this
                        );

                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {

                            mLocation = mLocationManager.
                                    getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (mLocation != null) {
                                this.latitude  = mLocation.getLatitude();
                                this.longitude = mLocation.getLongitude();
                            }

                        }

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mLocation;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                (Activity)  mContext,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 101
        );
    }

    /**
     * Get latitude
     *
     * @return latitude
     */
    public double getLatitude() {

        if (mLocation != null)
            latitude = mLocation.getLatitude();

        return latitude;
    }

    /**
     * Get longitude
     *
     * @return longitude
     */
    public double getLongitude() {

        if (mLocation != null)
            longitude = mLocation.getLongitude();

        return longitude;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    /**
     * Caso não tenha os serviços de Gps ativos
     */
    public void showSettingsAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Permissão da utilização de GPS");
        builder.setMessage("GPS indisponível. Pretende ativar os serviços de GPS?");
        builder.setPositiveButton("Sim, pretendo ativar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
