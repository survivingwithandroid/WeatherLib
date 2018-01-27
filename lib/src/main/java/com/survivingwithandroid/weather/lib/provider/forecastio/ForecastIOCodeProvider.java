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

package com.survivingwithandroid.weather.lib.provider.forecastio;

import com.survivingwithandroid.weather.lib.WeatherCode;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;

/**
* ForecastIO code provider. It converts the owm weather code to the unified code system provided by this library
*
* @author Francesco Azzola
* @author Chris Watts
* */
public class ForecastIOCodeProvider implements IWeatherCodeProvider {
    @Override
    public WeatherCode getWeatherCode(String weatherCode) {
        if (weatherCode == null)
            return WeatherCode.NOT_AVAILABLE;

        if (weatherCode.equalsIgnoreCase("clear-day"))
            return WeatherCode.SUNNY;
        else if (weatherCode.equalsIgnoreCase("clear-night"))
            return WeatherCode.CLEAR_NIGHT;
        else if (weatherCode.equalsIgnoreCase("rain"))
            return WeatherCode.SHOWERS;
        else if (weatherCode.equalsIgnoreCase("snow"))
            return WeatherCode.SNOW;
        else if (weatherCode.equalsIgnoreCase("sleet"))
            return WeatherCode.SLEET;
        else if (weatherCode.equalsIgnoreCase("wind"))
            return WeatherCode.WINDY;
        else if (weatherCode.equalsIgnoreCase("fog"))
            return WeatherCode.FOGGY;
        else if (weatherCode.equalsIgnoreCase("cloudy"))
            return WeatherCode.CLOUDY;
        else if (weatherCode.equalsIgnoreCase("partly-cloudy-day"))
            return WeatherCode.PARTLY_CLOUDY_DAY;
        else if (weatherCode.equalsIgnoreCase("partly-cloudy-night"))
            return WeatherCode.PARTLY_CLOUDY_NIGHT;
        else if (weatherCode.equalsIgnoreCase("hail"))
            return WeatherCode.HAIL;
        else if (weatherCode.equalsIgnoreCase("thunderstorm"))
            return WeatherCode.THUNDERSTORMS;
        else if (weatherCode.equalsIgnoreCase("tornado"))
            return WeatherCode.TORNADO;
        else
            return WeatherCode.NOT_AVAILABLE;
    }


}
