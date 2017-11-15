package com.example.sean.onestopshop.data;

import org.json.JSONObject;

/**
 * Created by Sean on 08/11/2017.
 */

public class Item implements JSONPopulator {

    private Condition condition;

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }

    public Condition getCondition() {
        return condition;
    }
}
