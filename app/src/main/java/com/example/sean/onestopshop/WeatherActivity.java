package com.example.sean.onestopshop;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sean.onestopshop.Helper.LocaleHelper;
import com.example.sean.onestopshop.data.Channel;
import com.example.sean.onestopshop.data.Item;
import com.example.sean.onestopshop.service.WeatherServiceCallBack;
import com.example.sean.onestopshop.service.YahooWeatherService;

import io.paperdb.Paper;

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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.weather_activity_title);
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

        Paper.init(this);

        //default language english
        String language = Paper.book().read("language");
        if(language == null) {
            Paper.book().write("language","en");
        }

        updateView((String)Paper.book().read("language"));
    }

    private void updateView(String language) {
        Context context = LocaleHelper.setLocale(this, language);
        Resources resources = context.getResources();

        locationView.setText(resources.getString(R.string.weather_location));
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
                Paper.book().write("language", "en");
                updateView((String)Paper.book().read("language"));
                break;

            case R.id.action_irish:
                Paper.book().write("language", "ga");
                updateView((String)Paper.book().read("language"));
                break;
        }

        //return true;
        return super.onOptionsItemSelected(item);
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
        //locationView.setText(service.getLocation());
        locationView.setText(R.string.weather_location);
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
