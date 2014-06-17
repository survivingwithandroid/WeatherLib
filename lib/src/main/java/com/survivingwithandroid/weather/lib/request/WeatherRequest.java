package com.survivingwithandroid.weather.lib.request;

/**
 * ${copyright}.
 */

/**
 * This class encapsulates the request parameters that must be passed to the provider to get the weather condition.
 * It can hold:
 *
 * cityId the current city id
 * geographic coordinates
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
