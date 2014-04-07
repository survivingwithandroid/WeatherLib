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

package com.survivingwithandroid.weather.lib;

/*
* Unified weather codes. These codes are indipendent from the weather provider used so that
* a client that wants to use different weather providers at the same time doesn't have
* to worry about different weather code to implement different custom icons
*
* @see com.survivingwithandroid.weather.lib.IWeatherCodeProvider
* @see com.survivingwithandroid.weather.lib..openweathermap.OpenweathermapCodeProvider
* @see com.survivingwithandroid.weather.lib.yahooweather.YahooWeatherCodeProvider
* */
public enum WeatherCode {

    TORNADO(0),
    TROPICAL_STORM(1),
    HURRICANE(2),
    SEVERE_THUNDERSTORMS(3),
    THUNDERSTORMS(4),
    MIXED_RAIN_SNOW(5),
    MIXED_RAIN_SLEET(6),
    MIXED_SNOW_SLEET(7),
    FREEZING_DRIZZLE(8),
    DRIZZLE(9),
    FREEZING_RAIN(10),
    SHOWERS(11),
    HEAVY_SHOWERS(12),
    SNOW_FLURRIES(13),
    LIGHT_SNOW_SHOWERS(14),
    BLOWING_SNOW(15),
    SNOW(16),
    HAIL(17),
    SLEET(18),
    DUST(19),
    FOGGY(20),
    HAZE(21),
    SMOKY(22),
    BLUSTERY(23),
    WINDY(24),
    COLD(25),
    CLOUDY(26),
    MOSTLY_CLOUDY_NIGHT(27),
    MOSTLY_CLOUDY_DAY(28),
    PARTLY_CLOUDY_NIGHT(29),
    PARTLY_CLOUDY_DAY(30),
    CLEAR_NIGHT(31),
    SUNNY(32),
    FAIR_NIGHT(33),
    FAIR_DAY(34),
    MIXED_RAIN_AND_HAIL(35),
    HOT(36),
    ISOLATED_THUNDERSTORMS(37),
    SCATTERED_THUNDERSTORMS(38),
    SCATTERED_SHOWERS(40),
    HEAVY_SNOW(41),
    SCATTERED_SNOW_SHOWERS(42),
    PARTLY_CLOUD(44),
    THUNDERSHOWERS(45),
    SNOW_SHOWERS(46),
    ISOLATED_THUDERSHOWERS(47),
    NOT_AVAILABLE(1000);

    private int code;

    WeatherCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
