package com.survivingwithandroid.weather.lib.provider.wunderground.extrequest;

import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

/**
 * Created by Francesco on 08/07/2014.
 */
public class WebcamFeatureRequest extends WeatherUndergroundProviderFeature {

    public WebcamFeatureRequest(WeatherRequest request, WeatherConfig config) {
        super(request, config);
    }

    @Override
    public String getURL() throws ApiKeyRequiredException {
        if (config.ApiKey == null || config.ApiKey.equals(""))
            throw new ApiKeyRequiredException();

        String url = "http://api.wunderground.com/api" + "/" + config.ApiKey + "/webcams/";
        url = addLanguage(url);

        if (request.getCityId() != null)
            url = url + request.getCityId() + ".json";
        else
            url = url + request.getLat() + "," + request.getLon() + ".json";

        return url;
    }
}
