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

package com.survivingwithandroid.weather.lib.util;

import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.model.BaseWeather;

public class WeatherUtility {

    public static BaseWeather.WeatherUnit createWeatherUnit(WeatherConfig.UNIT_SYSTEM unit) {
        BaseWeather.WeatherUnit weatherUnit = new BaseWeather.WeatherUnit();

        if (unit == null)
            return weatherUnit;

        if (isMetric(unit)) {
            weatherUnit.speedUnit = "m/s";
            weatherUnit.tempUnit = "°C";
        } else {
            weatherUnit.speedUnit = "mph";
            weatherUnit.tempUnit = "°F";
        }

        weatherUnit.pressureUnit = "hPa";

        return weatherUnit;
    }

    public static boolean isMetric(WeatherConfig.UNIT_SYSTEM currentUnit) {
        return currentUnit.equals(WeatherConfig.UNIT_SYSTEM.M);
    }


    public static float string2Float(String value) {
        if (value == null || "".equals(value))
            return -1;

        return Float.parseFloat(value);
    }


}
