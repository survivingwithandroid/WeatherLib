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

package com.survivingwithandroid.weather.lib.model;

/**
* Base weather class that holds the unit system
*
* @author: Francesco Azzola
* */
public abstract class BaseWeather {

    private WeatherUnit unit;

    public WeatherUnit getUnit() {
        return unit;
    }

    public void setUnit(WeatherUnit unit) {
        this.unit = unit;
    }

    public static class WeatherUnit {
        public String speedUnit;
        public String tempUnit;
        public String pressureUnit;
        public String distanceUnit;
    }
}
