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

package com.survivingwithandroid.weather.lib.provider.wunderground;

import com.survivingwithandroid.weather.lib.WeatherCode;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;

/**
 * This is a concrete implementaton of IWeatherCodeProvider interface for Weather undergound provider
 *
 * @author Francesco Azzola
 * */

public class WeatherUndergroundCodeProvider implements IWeatherCodeProvider {
    @Override
    public WeatherCode getWeatherCode(String weatherCode) {
        if (weatherCode == null)
            return WeatherCode.NOT_AVAILABLE;

        if (weatherCode.equalsIgnoreCase("chanceflurries"))
            return WeatherCode.SNOW_FLURRIES;
        else if (weatherCode.equalsIgnoreCase("chancerain"))
            return WeatherCode.SHOWERS;
        else if (weatherCode.equalsIgnoreCase("chancesleet"))
            return WeatherCode.SLEET;
        else if (weatherCode.equalsIgnoreCase("chancesnow"))
            return WeatherCode.SNOW;
        else if (weatherCode.equalsIgnoreCase("chancetstorms"))
            return WeatherCode.THUNDERSTORMS;
        else if (weatherCode.equalsIgnoreCase("clear"))
            return WeatherCode.FAIR_DAY;
        else if (weatherCode.equalsIgnoreCase("cloudy"))
            return WeatherCode.CLOUDY;
        else if (weatherCode.equalsIgnoreCase("flurries"))
            return WeatherCode.SNOW_FLURRIES;
        else if (weatherCode.equalsIgnoreCase("fog"))
            return WeatherCode.FOGGY;
        else if (weatherCode.equalsIgnoreCase("hazy"))
            return WeatherCode.HAZE;
        else if (weatherCode.equalsIgnoreCase("mostlycloudy"))
            return WeatherCode.MOSTLY_CLOUDY_DAY;
        else if (weatherCode.equalsIgnoreCase("mostlysunny"))
            return WeatherCode.FAIR_DAY;
        else if (weatherCode.equalsIgnoreCase("partlycloudy"))
            return WeatherCode.PARTLY_CLOUD;
        else if (weatherCode.equalsIgnoreCase("partlysunny"))
            return WeatherCode.PARTLY_CLOUDY_DAY;
        else if (weatherCode.equals("sleet"))
            return WeatherCode.SLEET;
        else if (weatherCode.equalsIgnoreCase("rain"))
            return WeatherCode.SHOWERS;
        else if (weatherCode.equalsIgnoreCase("snow"))
            return WeatherCode.SNOW;
        else if (weatherCode.equalsIgnoreCase("sunny"))
            return WeatherCode.SUNNY;
        else if (weatherCode.equalsIgnoreCase("tstorms"))
            return WeatherCode.THUNDERSTORMS;
        else if (weatherCode.equalsIgnoreCase("cloudy"))
            return WeatherCode.CLOUDY;
        else
            return WeatherCode.NOT_AVAILABLE;
    }
}

 
