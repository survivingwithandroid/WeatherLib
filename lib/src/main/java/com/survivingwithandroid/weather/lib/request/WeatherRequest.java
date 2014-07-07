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

/**
 * This class encapsulates the request parameters that must be passed to the weather client to get the weather condition.
 * The request can be made using cityId (a unique identifier) or using latitude and longitude
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
