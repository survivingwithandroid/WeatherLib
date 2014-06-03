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

import java.util.ArrayList;
import java.util.List;

/**
 * Forecast weather. This class holds the list of next days forecast.
 *
 * @author Francesco Azzola
 */
public class WeatherForecast extends BaseWeather {

    private List<DayForecast> daysForecast = new ArrayList<DayForecast>();

    /*
    * Add a single day forecast
    *
    * @param forecast {@see DayForecast}
    * */
    public void addForecast(DayForecast forecast) {
        daysForecast.add(forecast);
    }

    /*
    * Retrieves the daynum day in the list. For example if i want to know the tomorrow weather i will use 0 as index.
    *
    * @param dayNum int
    * @return {@see DayForecast}
    * */
    public DayForecast getForecast(int dayNum) {
        return daysForecast.get(dayNum);
    }

    /*
    * Get the full list
    *
    * @return {@see List} of {@see DayForecast}
    * */
    public List<DayForecast> getForecast() {
        return this.daysForecast;
    }
}
