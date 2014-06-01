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
 * This class represents the current weather condition as retrieved by the provider.
 * An instance of this class is returned by the provider using {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherClientListener}
 *
 * @author Francesco Azzola
 */
public class CurrentWeather extends BaseWeather {

    public Weather weather = new Weather();

}
