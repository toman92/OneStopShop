package com.example.sean.onestopshop;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class ParkingDarkActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    private MapView mapView;
    private Location mLastLocation;
    private Button btnNormal;
    private Button btnSatellite;
    private double currentLat;
    private double currentLong;
    private GoogleApiClient mGoogleApiClient;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_key));
        setContentView(R.layout.activity_parking_dark);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override

            public void onMapReady(MapboxMap mapboxMap) {

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.950672, -7.735915 ))
                        .title("Dillons Car Park")
                        .snippet("1 hour free parking. €2 per hour after"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951399, -7.734589))
                        .title("Justice Walsh Car Park")
                        .snippet("1 hour free parking. €2 per hour after"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951730, -7.734018))
                        .title("Courthouse Car Park")
                        .snippet("1 hour free Parking. €2 per hour after"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.948762, -7.735885))
                        .title("Courtyard Car Park")
                        .snippet("1 hour free parking. €2 per hour after"));

            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Parking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnNormal = (Button) findViewById(R.id.btnParkingNormal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parkingNormalIntent = new Intent(ParkingDarkActivity.this, ParkingActivity.class);
                startActivity(parkingNormalIntent);
            }
        });

        btnSatellite = (Button) findViewById(R.id.btnParkingSatellite);
        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parkingSatelliteIntent = new Intent(ParkingDarkActivity.this, ParkingSatelliteActivity.class);
                startActivity(parkingSatelliteIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(ParkingDarkActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(ParkingDarkActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(ParkingDarkActivity.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {


            } else {
                android.support.v4.app.ActivityCompat.requestPermissions(ParkingDarkActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {
            currentLat = mLastLocation.getLatitude();
            currentLong = mLastLocation.getLongitude();
            Toast.makeText(this, "Got Last Location", Toast.LENGTH_SHORT).show();
            mapView.getMapAsync(new OnMapReadyCallback() {

                @Override

                public void onMapReady(MapboxMap mapboxMap) {

                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(currentLat, currentLong))
                            .title("Home")
                            .snippet("Current Location"));
                }

            });
        } else {
            Toast.makeText(this, "Failed to get last Location", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }
}
