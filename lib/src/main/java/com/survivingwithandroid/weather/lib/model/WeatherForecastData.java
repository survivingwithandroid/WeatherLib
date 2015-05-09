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

/**
* This is the basic class for weather forecast data. It holds some basic information
*
* @author Francesco Azzola
* */
public class WeatherForecastData implements Parcelable {


    public Weather weather = new Weather();

    /*
    * Forecast timestamp. Using this parameter you know at what day/hour the forecast information is refering to.
    * */
    public long timestamp;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(weather, flags);
        dest.writeLong(timestamp);
    }

    public static final Parcelable.Creator<WeatherForecastData> CREATOR
            = new Parcelable.Creator<WeatherForecastData>() {
        public WeatherForecastData createFromParcel(Parcel in) {
            return new WeatherForecastData(in);
        }

        public WeatherForecastData[] newArray(int size) {
            return new WeatherForecastData[size];
        }
    };

    private WeatherForecastData(Parcel in) {
        weather = in.readParcelable(Weather.class.getClassLoader());
        timestamp = in.readLong();
    }

    public WeatherForecastData() {}
}
