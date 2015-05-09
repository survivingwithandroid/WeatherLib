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

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the hourly weather forecast.
 *
 * @author Francesco Azzola
 */
public class WeatherHourForecast extends BaseWeather implements Parcelable {

    /**
     * Hour forecast information as retrieved by the weather provider. Traversing this list
     * you can have all the information about hour forecast
     */
    public List<HourForecast> hoursForecast = new ArrayList<HourForecast>();

    /*
 * Add a single hour forecast
 *
 * @param forecast {@see HourForecast}
 * */
    public void addForecast(HourForecast forecast) {
        hoursForecast.add(forecast);
    }

    /*
    * Retrieve the hour forecast in the list. For example if i want to know the next hour weather forecast you have to use 1 as index.
    *
    * @param hourNum int
    * @return {@see HourForecast}
    * */
    public HourForecast getHourForecast(int hourNum) {
        return hoursForecast.get(hourNum);
    }

    /*
    * Get the full list
    *
    * @return {@see List} of {@see HourForecast}
    * */
    public List<HourForecast> getHourForecast() {
        return this.hoursForecast;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(hoursForecast);

    }

    public static final Parcelable.Creator<WeatherHourForecast> CREATOR
            = new Parcelable.Creator<WeatherHourForecast>() {
        public WeatherHourForecast createFromParcel(Parcel in) {
            return new WeatherHourForecast(in);
        }

        public WeatherHourForecast[] newArray(int size) {
            return new WeatherHourForecast[size];
        }
    };

    private WeatherHourForecast(Parcel in) {
        in.readList(hoursForecast, hoursForecast.getClass().getClassLoader());
    }

    public WeatherHourForecast() {}
}
