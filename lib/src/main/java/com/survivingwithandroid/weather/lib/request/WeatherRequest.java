/*
 * Copyright (C) 2014 Francesco Azzola
 *  Surviving with Android (http://www.survivingwithandroid.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.survivingwithandroid.weather.lib.request;

import android.location.Location;

/**
 * This class encapsulates the request parameters that must be passed to the weather client to get the weather condition.
 * The request can be made using cityId (a unique identifier) or using latitude and longitude
 *
 * @author Francesco Azzola
 * @since 1.5.1
 */
public class WeatherRequest {
    private String cityId;
    private double lon;
    private double lat;

    public WeatherRequest(String cityId) {
        this.cityId = cityId;
    }

    public WeatherRequest(Location location) {
        this.lat = location.getLatitude();
        this.lon = location.getLongitude();
    }

    public WeatherRequest(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
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
