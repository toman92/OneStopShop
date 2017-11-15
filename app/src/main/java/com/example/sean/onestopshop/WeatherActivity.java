package com.example.sean.onestopshop;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sean.onestopshop.data.Channel;
import com.example.sean.onestopshop.data.Item;
import com.example.sean.onestopshop.service.WeatherServiceCallBack;
import com.example.sean.onestopshop.service.YahooWeatherService;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallBack {

    private ImageView weatherIconView;
    private TextView temperatureView;
    private TextView conditionView;
    private TextView locationView;
    private YahooWeatherService service;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weather");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        weatherIconView = (ImageView) findViewById(R.id.weatherImg);
        temperatureView = (TextView) findViewById(R.id.temperature);
        conditionView = (TextView) findViewById(R.id.condition);
        locationView = (TextView) findViewById(R.id.location);

        service = new YahooWeatherService(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(VISIBLE);
        service.refreshWeather("Letterkenny, Donegal");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        progressBar.setVisibility(GONE);

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(resourceId, null);

        int celsius = item.getCondition().getTemp();
        celsius = ((celsius-32)*5)/9;

        weatherIconView.setImageDrawable(weatherIconDrawable);
        //temperatureView.setText(item.getCondition().getTemp() + "\u00B0" + channel.getUnits().getTemperature());
        //temperatureView.setText(celsius + "\u00B0" + channel.getUnits().getTemperature());
        temperatureView.setText(celsius + "\u00B0" + "C");
        conditionView.setText(item.getCondition().getDesc());
        locationView.setText(service.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
