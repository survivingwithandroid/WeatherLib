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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Single day weather forecast. This class holds the information about next days weather conditions.
 *
 * @author Francesco Azzola
 */
public class DayForecast extends WeatherForecastData implements Parcelable {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public ForecastTemp forecastTemp = new ForecastTemp();

    /*
    * Forecast temperature
    * */
    public static class ForecastTemp implements  Parcelable{
        public float day;
        public float min;
        public float max;
        public float night;
        public float eve;
        public float morning;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(day);
            dest.writeFloat(min);
            dest.writeFloat(max);
            dest.writeFloat(night);
            dest.writeFloat(eve);
            dest.writeFloat(morning);
        }

        public static final Parcelable.Creator<ForecastTemp> CREATOR
                = new Parcelable.Creator<ForecastTemp>() {
            public ForecastTemp createFromParcel(Parcel in) {
                return new ForecastTemp(in);
            }

            public ForecastTemp[] newArray(int size) {
                return new ForecastTemp[size];
            }
        };

        private ForecastTemp(Parcel in) {
            day = in.readFloat();
            min = in.readFloat();
            max = in.readFloat();
            night = in.readFloat();
            eve = in.readFloat();
            morning = in.readFloat();
        }

        public ForecastTemp() {}
    }

    public String getStringDate() {
        return sdf.format(new Date(timestamp));
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(forecastTemp, flags);
    }

    public static final Parcelable.Creator<DayForecast> CREATOR
            = new Parcelable.Creator<DayForecast>() {
        public DayForecast createFromParcel(Parcel in) {
            return new DayForecast(in);
        }

        public DayForecast[] newArray(int size) {
            return new DayForecast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    private DayForecast(Parcel in) {
        forecastTemp = in.readParcelable(ForecastTemp.class.getClassLoader());
    }

    public DayForecast() {}

}
