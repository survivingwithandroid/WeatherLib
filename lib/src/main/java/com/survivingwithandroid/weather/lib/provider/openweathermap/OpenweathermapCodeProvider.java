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

package com.survivingwithandroid.weather.lib.provider.openweathermap;

import com.survivingwithandroid.weather.lib.WeatherCode;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;
import com.survivingwithandroid.weather.lib.util.LogUtils;

/**
* Openweathermap code provider. It converts the owm weather code to the unified code system provided by this library
*
* @author Francesco Azzola
* */
public class OpenweathermapCodeProvider implements IWeatherCodeProvider {
    @Override
    public WeatherCode getWeatherCode(String weatherCode) {

        int code = Integer.parseInt(weatherCode);
        switch (code) {
            case 900:
                return WeatherCode.TORNADO;
            case 901:
                return WeatherCode.TROPICAL_STORM;
            case 902:
                return WeatherCode.HURRICANE;
            case 212:
                return WeatherCode.SEVERE_THUNDERSTORMS;
            case 200:
            case 210:
            case 211:
            case 232:
                return WeatherCode.THUNDERSTORMS;
            case 615:
            case 616:
                return WeatherCode.MIXED_RAIN_SNOW;
            case 314:
                return WeatherCode.FREEZING_DRIZZLE;
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 315:
                return WeatherCode.DRIZZLE;
            case 511:
                return WeatherCode.FREEZING_RAIN;
            case 500:
            case 501:
            case 520:
                return WeatherCode.SHOWERS;
            case 502:
            case 503:
            case 504:
            case 521:
            case 522:
                return WeatherCode.HEAVY_SHOWERS;
            case 600:
            case 601:
            case 620:
                return WeatherCode.SNOW;
            case 906:
                return WeatherCode.HAIL;
            case 611:
                return WeatherCode.SLEET;
            case 761:
                return WeatherCode.DUST;
            case 701:
            case 741:
                return WeatherCode.FOGGY;
            case 711:
                return WeatherCode.HAZE;
            case 721:
                return WeatherCode.SMOKY;
            case 905:
                return WeatherCode.WINDY;
            case 903:
                return WeatherCode.COLD;
            case 802:
            case 803:
            case 804:
                return WeatherCode.CLOUDY;
            case 801:
                return WeatherCode.MOSTLY_CLOUDY_DAY;
            case 800:
                return WeatherCode.SUNNY;
            case 221:
                return WeatherCode.SCATTERED_THUNDERSTORMS;
            case 531:
                return WeatherCode.SCATTERED_SHOWERS;
            case 602:
            case 622:
                return WeatherCode.HEAVY_SNOW;
            case 201:
            case 202:
            case 230:
            case 231:
                return WeatherCode.THUNDERSTORMS;
            case 621:
                return WeatherCode.SNOW_SHOWERS;
            default:
                throw new UnsupportedOperationException();
        }
    }


}
