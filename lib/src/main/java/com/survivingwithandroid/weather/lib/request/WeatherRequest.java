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
    private double lon;
    private double lat;

    public WeatherRequest(String cityId) {
        this.cityId = cityId;
    }

    public WeatherRequest(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getCityId() {
        return cityId;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
