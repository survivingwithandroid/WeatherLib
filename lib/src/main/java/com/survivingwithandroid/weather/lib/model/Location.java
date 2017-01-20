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

import com.survivingwithandroid.weather.lib.DefaultValues;

import java.io.Serializable;

/**
* This class holds information about location as returned from the weather provider
*
* @author Francesco Azzola
* */
public class Location implements Serializable {


    private Double longitude = DefaultValues.DEFAULT_DOUBLE;
    private Double latitude = DefaultValues.DEFAULT_DOUBLE;
    private Long sunset = DefaultValues.DEFAULT_LONG;
    private Long sunrise = DefaultValues.DEFAULT_LONG;
    private String country = DefaultValues.DEFAULT_STRING;
    private String city = DefaultValues.DEFAULT_STRING;
    private String region = DefaultValues.DEFAULT_STRING;
    private Astronomy astronomy = new Astronomy();
    private Long population = DefaultValues.DEFAULT_LONG;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
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

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public class Astronomy {
        public String moonAge;
        public String percIllum;
        public String moonPhaseDescr;
        public String hemisphere;
    }
}
