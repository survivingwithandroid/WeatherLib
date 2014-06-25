package com.survivingwithandroid.weather.lib.request;

/**
 * ${copyright}.
 */

/**
 * This class encapsulates the request parameters that must be passed to the weather client to get the weather condition.
 * The request can be made using cityId (a unique indentifier) or using latitude and longitude
 *
 * @author Francesco Azzola
 * @since 1.5.1
 * */
public class WeatherRequest {
    private String cityId;
    private float lon;
    private float lat;

    public WeatherRequest(String cityId) {
        this.cityId = cityId;
    }

    public WeatherRequest(float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getCityId() {
        return cityId;
    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }
}
