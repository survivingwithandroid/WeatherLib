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
package com.survivingwithandroid.weather.lib.provider.yahooweather;

import com.survivingwithandroid.weather.lib.WeatherCode;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;

import java.util.IllegalFormatCodePointException;

public class YahooWeatherCodeProvider implements IWeatherCodeProvider {
    @Override
    public WeatherCode getWeatherCode(String weatherCode) {
        int code = Integer.parseInt(weatherCode);

        for (WeatherCode c : WeatherCode.values()) {
            if (c.getCode() == code)
                return c;
        }
        throw new IllegalFormatCodePointException(-1);
    }
}
