package com.survivingwithandroid.weather.lib.provider.wunderground.extrequest;

import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.request.WeatherProviderFeature;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

/**
 * Created by Francesco on 08/07/2014.
 */
public abstract class WeatherUndergroundProviderFeature extends WeatherProviderFeature {

    protected  static final String BASE_URL = "http://api.wunderground.com/api";

    public WeatherUndergroundProviderFeature(WeatherRequest request, WeatherConfig config) {
        super(request, config);

    }


    protected String addLanguage(String url) {
        if (config.lang == null)
            return url;

        String nUrl = url + "/lang:" + config.lang.toUpperCase() + "/";
        return nUrl;
    }

}
