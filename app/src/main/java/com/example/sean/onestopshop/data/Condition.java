package com.example.sean.onestopshop.data;

import org.json.JSONObject;

/**
 * Created by Sean on 08/11/2017.
 */

public class Condition implements JSONPopulator{

    private int code;
    private int temp;
    private String desc;

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temp = data.optInt("temp");
        desc = data.optString("text");
    }

    public int getCode() {
        return code;
    }

    public int getTemp() {
        return temp;
    }

    public String getDesc() {
        return desc;
    }
}
