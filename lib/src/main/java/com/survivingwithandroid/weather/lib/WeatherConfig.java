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


/**
 * Weather configuration class. It is used to pass some configuration parameters to the weather provider.
 * Some attributes of this class can be ignored for some weather provider implementation.
 *
 * @author Francesco Azzola
 */
public class WeatherConfig {

    /*
    * Enumeration that contains the unit system used to retrieve the weather data
    * M = Metric system
    * I = Imperial System
    * */
    public enum UNIT_SYSTEM {
        M, I
    }

    ;

    /**
     * This parameter represents the number of results that the city search should return
     */
    public int maxResult = 10;
    public int numDays = 3;

    /*
    * This parameter is the language used to retrieve weather information, if the provider supports multilanguage messages
    * */
    public String lang = "en";

    /*
    * The API key that must me sent to the weather provider. Not all the providers require this key. It may be null
    * */
    public String ApiKey = "";
    public UNIT_SYSTEM unitSystem = UNIT_SYSTEM.M;


}
