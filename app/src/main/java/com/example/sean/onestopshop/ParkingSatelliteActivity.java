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
import android.view.Menu;
import android.view.MenuItem;
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

public class ParkingSatelliteActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private MapView mapView;
    private Location mLastLocation;
    private Button btnNormal;
    private Button btnDark;
    private double currentLat;
    private double currentLong;
    private GoogleApiClient mGoogleApiClient;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_key));
        setContentView(R.layout.activity_parking_satellite);

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
                        .title(getString(R.string.dillons_carpark_title))
                        .snippet(getString(R.string.dillons_snippet)));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951399, -7.734589))
                        .title(getString(R.string.justice_carpark_title))
                        .snippet(getString(R.string.justice_snippet)));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951730, -7.734018))
                        .title(getString(R.string.courthouse_carpark_title))
                        .snippet(getString(R.string.courthouse_snippet)));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.948762, -7.735885))
                        .title(getString(R.string.courtyard_carpark_title))
                        .snippet(getString(R.string.courtyard_snippet)));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951089, -7.739774 ))
                        .title("Cathedral Car Park")
                        .snippet("FREE Parking. Max stay 3 hours"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951682, -7.741649 ))
                        .title("Mart Site Car Park")
                        .snippet("50c per hour. €1 per day"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.945938, -7.739975 ))
                        .title("Station Car Park")
                        .snippet("€1 per hour"));

            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.parking_activity_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnNormal = (Button) findViewById(R.id.btnParkingNormal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parkingNormalIntent = new Intent(ParkingSatelliteActivity.this, ParkingActivity.class);
                startActivity(parkingNormalIntent);
            }
        });

        btnDark = (Button) findViewById(R.id.btnParkingDark);
        btnDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parkingDarkIntent = new Intent(ParkingSatelliteActivity.this, ParkingDarkActivity.class);
                startActivity(parkingDarkIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_english:
                Toast.makeText(this, "English Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_irish:
                Toast.makeText(this, "Irish Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        //return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(ParkingSatelliteActivity.this, MainActivity.class);
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
        if (ContextCompat.checkSelfPermission(ParkingSatelliteActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(ParkingSatelliteActivity.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {


            } else {
                android.support.v4.app.ActivityCompat.requestPermissions(ParkingSatelliteActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {
            currentLat = mLastLocation.getLatitude();
            currentLong = mLastLocation.getLongitude();
            Toast.makeText(this, "Got Location", Toast.LENGTH_SHORT).show();
            mapView.getMapAsync(new OnMapReadyCallback() {

                @Override

                public void onMapReady(MapboxMap mapboxMap) {

                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(currentLat, currentLong))
                            .title(getString(R.string.current_location_title))
                            .snippet(getString(R.string.current_location_snippet)));
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
