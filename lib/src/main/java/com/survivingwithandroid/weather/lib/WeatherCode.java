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

import android.content.Context;


/**
* Unified weather codes. These codes are independent from the weather provider used so that
* a client that wants to use different weather providers at the same time doesn't have
* to worry about different weather code to implement different custom icons
*
* @see com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider
* @see com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapCodeProvider
* @see com.survivingwithandroid.weather.lib.provider.yahooweather.YahooWeatherCodeProvider
* @see com.survivingwithandroid.weather.lib.provider.wunderground.WeatherUndergroundCodeProvider
*
* @author Francesco Azzola
* */
public enum WeatherCode {


    TORNADO(0, R.string.weather_text_000),
    TROPICAL_STORM(1, R.string.weather_text_001),
    HURRICANE(2, R.string.weather_text_002),
    SEVERE_THUNDERSTORMS(3, R.string.weather_text_003),
    THUNDERSTORMS(4, R.string.weather_text_004),
    MIXED_RAIN_SNOW(5, R.string.weather_text_005),
    MIXED_RAIN_SLEET(6, R.string.weather_text_006),
    MIXED_SNOW_SLEET(7, R.string.weather_text_007),
    FREEZING_DRIZZLE(8, R.string.weather_text_008),
    DRIZZLE(9, R.string.weather_text_009),
    FREEZING_RAIN(10, R.string.weather_text_010),
    SHOWERS(11, R.string.weather_text_011),
    HEAVY_SHOWERS(12, R.string.weather_text_012),
    SNOW_FLURRIES(13, R.string.weather_text_013),
    LIGHT_SNOW_SHOWERS(14, R.string.weather_text_014),
    BLOWING_SNOW(15, R.string.weather_text_015),
    SNOW(16, R.string.weather_text_016),
    HAIL(17, R.string.weather_text_017),
    SLEET(18, R.string.weather_text_018),
    DUST(19, R.string.weather_text_019),
    FOGGY(20, R.string.weather_text_020),
    HAZE(21, R.string.weather_text_021),
    SMOKY(22, R.string.weather_text_022),
    BLUSTERY(23, R.string.weather_text_023),
    WINDY(24, R.string.weather_text_024),
    COLD(25, R.string.weather_text_025),
    CLOUDY(26, R.string.weather_text_026),
    MOSTLY_CLOUDY_NIGHT(27, R.string.weather_text_027),
    PARTLY_CLOUDY_NIGHT(29, R.string.weather_text_029),
    PARTLY_CLOUDY_DAY(30, R.string.weather_text_030),
    CLEAR_NIGHT(31, R.string.weather_text_031),
    SUNNY(32, R.string.weather_text_032),
    FAIR_NIGHT(33, R.string.weather_text_033),
    FAIR_DAY(34, R.string.weather_text_034),
    MIXED_RAIN_AND_HAIL(35, R.string.weather_text_035),
    //HOT(36, R.string.weather_text_036),
    ISOLATED_THUNDERSTORMS(37, R.string.weather_text_037),
    SCATTERED_THUNDERSTORMS(38, R.string.weather_text_038),
    SCATTERED_THUNDERSTORMS_1(39, R.string.weather_text_039),
    SCATTERED_SHOWERS(40, R.string.weather_text_040),
    HEAVY_SNOW(41, R.string.weather_text_041),
    SCATTERED_SNOW_SHOWERS(42, R.string.weather_text_042),
    PARTLY_CLOUD(44, R.string.weather_text_044),
    THUNDERSHOWERS(45, R.string.weather_text_045),
    SNOW_SHOWERS(46, R.string.weather_text_046),
    ISOLATED_THUDERSHOWERS(47, R.string.weather_text_047),
    NOT_AVAILABLE(1000, R.string.weather_text_1000),
    MOSTLY_CLOUDY_DAY(28, R.string.weather_text_028);

    private int code;
    private int resId;

    WeatherCode(int code, int resId) {
        this.code = code;
        this.resId = resId;
    }

    public int getCode() {
        return this.code;
    }

    public String getLabel(Context context) {
        String msg = context.getResources().getString(resId);
        return msg;
    }

}
