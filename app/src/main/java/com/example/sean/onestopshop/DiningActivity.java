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
import android.util.Log;
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

public class DiningActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    private MapView mapView;
    private Location mLastLocation;
    private Button btnDark;
    private Button btnSatellite;
    private double currentLat;
    private double currentLong;
    private GoogleApiClient mGoogleApiClient;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final String TAG = ParkingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_key));
        setContentView(R.layout.activity_dining);

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
                        .position(new LatLng(54.949735, -7.737819 ))
                        .title("Brewery Bar and Restaurant")
                        .snippet("4.4 / 5 Star Public Rating"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951515, -7.736124))
                        .title("Charcoal Grill")
                        .snippet("4.9 / 5 Star Public Rating"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951629, -7.735931))
                        .title("Pats Pizza")
                        .snippet("4.6 / 5 Star Public Rating"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.951463, -7.733511))
                        .title("Uptown Cafe")
                        .snippet("4.6 / 5 Star Public Rating"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.952664, -7.705991))
                        .title("China Town Restaurant")
                        .snippet("4.3 / 5 Star Public Rating"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.948525, -7.737051))
                        .title("Lemon Tree Restaurant")
                        .snippet("4.5 / 5 Star Public Rating"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.946727, -7.741230))
                        .title("Tin Tai Chinese Restaurant")
                        .snippet("4.5 / 5 Star Public Rating"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(54.946416, -7.737599))
                        .title("Johnson's Fish & Chips")
                        .snippet("4.2 / 5 Star Public Rating"));

            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dining");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnDark = (Button) findViewById(R.id.btnParkingDark);
        btnDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiningActivity.this, "Button dark clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnSatellite = (Button) findViewById(R.id.btnParkingSatellite);
        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiningActivity.this, "Button satellite clicked", Toast.LENGTH_SHORT).show();
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
        if (ContextCompat.checkSelfPermission(DiningActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(DiningActivity.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {


            } else {
                android.support.v4.app.ActivityCompat.requestPermissions(DiningActivity.this,
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
