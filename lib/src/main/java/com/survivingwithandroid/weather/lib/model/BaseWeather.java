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

    public static class WeatherUnit implements Parcelable {
        public String speedUnit;
        public String tempUnit;
        public String pressureUnit;
        public String distanceUnit;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(speedUnit);
        dest.writeString(tempUnit);
        dest.writeString(pressureUnit);
        dest.writeString(distanceUnit);
    }
  }
}
