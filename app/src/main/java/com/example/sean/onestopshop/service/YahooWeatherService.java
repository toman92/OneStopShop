package com.example.sean.onestopshop.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.sean.onestopshop.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sean on 08/11/2017.
 */

public class YahooWeatherService {

    private WeatherServiceCallBack callBack;
    private String location;
    private Exception error;

    public YahooWeatherService(WeatherServiceCallBack callBack) {
        this.callBack = callBack;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(final String location) {
        this.location = location;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"letterkenny, donegal\")", location);
                String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json",
                        Uri.encode(YQL));

                try {

                    URL url = new URL(endPoint);
                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();

                }catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s == null && error != null) {
                    callBack.serviceFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.optJSONObject("query");

                    int count = data.optInt("count");
                    if (count == 0) {
                        callBack.serviceFailure(new LocationWeatherException("No weather information found for " + location));
                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callBack.serviceSuccess(channel);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(location);
    }

    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String message) {
            super(message);
        }
    }

}
