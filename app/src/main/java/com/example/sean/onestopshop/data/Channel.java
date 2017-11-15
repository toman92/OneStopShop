package com.example.sean.onestopshop.data;

import org.json.JSONObject;

/**
 * Created by Sean on 08/11/2017.
 */

public class Channel implements JSONPopulator {

    private Item item;
    private Units units;

    @Override
    public void populate(JSONObject data) {
        item = new Item();
        item.populate(data.optJSONObject("item"));

        units = new Units();
        units.populate(data.optJSONObject("units"));
    }

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }
}
