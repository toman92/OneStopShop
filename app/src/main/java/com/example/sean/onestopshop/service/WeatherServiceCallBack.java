package com.example.sean.onestopshop.service;

import com.example.sean.onestopshop.data.Channel;

/**
 * Created by Sean on 08/11/2017.
 */

public interface WeatherServiceCallBack {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
