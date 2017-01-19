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

import com.survivingwithandroid.weather.lib.DefaultValues;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.model.BaseWeather;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static JSONObject getLast(final JSONObject json, final String... names) throws JSONException {
        final int lastIndex = names.length - 1;
        JSONObject last = json;

        for (int i = 0; i < lastIndex; i++) {
            last = last.getJSONObject(names[i]);
        }

        return last;
    }

    public static String getString(final JSONObject json, final String... names) {
        try {
            return getLast(json, names).getString(names[names.length - 1]);
        } catch (JSONException e) {
            return DefaultValues.ERROR_STRING;
        }
    }

    public static Integer getInteger(final JSONObject json, final String... names){
        try{
            return getLast(json, names).getInt(names[names.length - 1]);
        }
        catch (JSONException e){
            return DefaultValues.ERROR_INTEGER;
        }
    }

    public static Long getLong(final JSONObject json, final String... names){
        try{
            return getLast(json, names).getLong(names[names.length - 1]);
        }
        catch (JSONException e){
            return DefaultValues.ERROR_LONG;
        }
    }

    public static Double getDouble(final JSONObject json, final String... names){
        try{
            return getLast(json, names).getDouble(names[names.length - 1]);
        }
        catch (JSONException e){
            return DefaultValues.ERROR_DOUBLE;
        }
    }
}
