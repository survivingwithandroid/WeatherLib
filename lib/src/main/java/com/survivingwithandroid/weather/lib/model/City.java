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

/**
 * This class represents the a City as returned during the search process. The search can be done using name pattern
 * or using geographic location. The weather provider used will create the instance of city
 *
 * @author Francesco Azzola
 */
public class City {

    /*
    * Unique city identfier
    * */
    private String id;

    /**
     * City name
     */
    private String name;

    /*
    * Country name
    * */
    private String country;

    /*
    * region
    * */
    private String region;

    /**
     * longitude
     */
    private double lon;

    /**
     * latitude
    * */
    private double lat;
/*
    public City() {
    }

    public City(String id, String name, String region, String country) {
        super();
        this.id = id;
        this.name = name;
        this.region = region;
        this.country = country;
    }
*/

    public City(CityBuilder builder) {
        this.id = builder.id;
        this.region = builder.region;
        this.country = builder.country;
        this.name = builder.name;
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String toString(){
        return this.name + ", " + this.country;
    }

    public static class CityBuilder {
        /*
         * Unique city identfier
        * */
        private String id;

        /**
         * City name
         */
        private String name;

        /*
        * Country name
        * */
        private String country;

        /*
        * region
        * */
        private String region;

        /**
         * longitude
         */
        private double lon;

        /**
         * latitude
         * */
        private double lat;

        public CityBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CityBuilder country(String country) {
            this.country = country;
            return this;
        }

        public CityBuilder region(String region) {
            this.region = region;
            return this;
        }

        public CityBuilder geoCoord(double lon, double lat) {
            this.lat = lat;
            this.lon = lon;
            return this;
        }

        public City build() {
            return new City(this);
        }

    }
}
