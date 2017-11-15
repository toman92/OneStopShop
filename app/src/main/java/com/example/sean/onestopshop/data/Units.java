package com.example.sean.onestopshop.data;

import org.json.JSONObject;

/**
 * Created by Sean on 08/11/2017.
 */

public class Units implements JSONPopulator{


    private String temperature;

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }

    public String getTemperature() {
        return temperature;
    }
}
