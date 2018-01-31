package com.example.sean.onestopshop;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.sean.onestopshop.Helper.LocaleHelper;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private Button weatherBtn;
    private Button parkingBtn;
    private Button entertainBtn;
    private Button foodBtn;
    private Button lkWebBtn;
    private Button wildWayBtn;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("One Stop Shop");

        weatherBtn = (Button) findViewById(R.id.btn_weather);
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(weatherIntent);
            }
        });

        parkingBtn = (Button) findViewById(R.id.btn_parking);
        parkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parkingIntent = new Intent(MainActivity.this, ParkingActivity.class);
                startActivity(parkingIntent);
            }
        });

        foodBtn = (Button) findViewById(R.id.btn_food);
        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diningActivity = new Intent(MainActivity.this, DiningActivity.class);
                startActivity(diningActivity);
            }
        });

        entertainBtn = (Button) findViewById(R.id.btn_entertain);
        entertainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activitiesIntent = new Intent(MainActivity.this, ActivitiesActivity.class);
                startActivity(activitiesIntent);
            }
        });

        lkWebBtn = (Button) findViewById(R.id.btn_website_lk);
        lkWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Create new intent for webview
                Intent webIntent = new Intent(MainActivity.this, LkGuideWebActivity.class);
                startActivity(webIntent);
            }
        });

        wildWayBtn = (Button) findViewById(R.id.btn_six);
        wildWayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atlanticIntent = new Intent(MainActivity.this, WildAtlanticWayActivity.class);
                startActivity(atlanticIntent);
            }
        });

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

        wildWayBtn.setText(resources.getString(R.string.wild_atlantic_btn));
        weatherBtn.setText(resources.getString(R.string.weather_btn));
        lkWebBtn.setText(resources.getString(R.string.go_lk_site_btn));
        entertainBtn.setText(resources.getString(R.string.activities_btn));
        foodBtn.setText(resources.getString(R.string.dining_btn));
        parkingBtn.setText(resources.getString(R.string.parking_btn));
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
}
