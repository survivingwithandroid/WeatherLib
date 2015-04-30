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

import java.io.Serializable;

/**
* This class holds information about location as returned from the weather provider
*
* @author Francesco Azzola
* */
public class Location implements Serializable, Parcelable {


    private float longitude;
    private float latitude;
    private long sunset;
    private long sunrise;
    private String country;
    private String city;
    private String region;
    private Astronomy astronomy = new Astronomy();
    private long population;


    public Location() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(longitude);
        dest.writeFloat(latitude);
        dest.writeLong(sunset);
        dest.writeFloat(sunrise);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(region);
        dest.writeParcelable(astronomy, flags);
        dest.writeLong(population);
    }

    public static final Parcelable.Creator<Location> CREATOR
            = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    private Location(Parcel in) {
        longitude = in.readFloat();
        latitude = in.readFloat();
        sunset = in.readLong();
        sunrise = in.readLong();
        country = in.readString();
        city = in.readString();
        region = in.readString();
        astronomy = (Astronomy) in.readParcelable(Astronomy.class.getClassLoader());
        population = in.readLong();
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Astronomy getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(Astronomy astronomy) {
        this.astronomy = astronomy;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public static class Astronomy implements Parcelable {
        public String moonAge;
        public String percIllum;
        public String moonPhaseDescr;
        public String hemisphere;

        public Astronomy() {}

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
           dest.writeString(moonAge);
           dest.writeString(percIllum);
           dest.writeString(moonPhaseDescr);
           dest.writeString(hemisphere);
        }

        public static final Parcelable.Creator<Astronomy> CREATOR
                = new Parcelable.Creator<Astronomy>() {
            public Astronomy createFromParcel(Parcel in) {
                return new Astronomy(in);
            }

            public Astronomy[] newArray(int size) {
                return new Astronomy[size];
            }
        };

        private Astronomy(Parcel in) {
            moonAge = in.readString();
            percIllum = in.readString();
            moonPhaseDescr = in.readString();
            hemisphere = in.readString();
        }
    }
}
