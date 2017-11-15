package com.example.sean.onestopshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("One Stop Shop");

        Button weatherBtn = (Button) findViewById(R.id.btn_weather);
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(weatherIntent);
            }
        });

        Button parkingBtn = (Button) findViewById(R.id.btn_parking);
        parkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parkingIntent = new Intent(MainActivity.this, ParkingActivity.class);
                startActivity(parkingIntent);
            }
        });

        Button foodBtn = (Button) findViewById(R.id.btn_food);
        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Food clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button entertainBtn = (Button) findViewById(R.id.btn_entertain);
        entertainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Entertainment Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
