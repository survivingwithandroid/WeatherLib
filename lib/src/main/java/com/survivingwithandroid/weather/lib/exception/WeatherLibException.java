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

package com.survivingwithandroid.weather.lib.exception;

/**
* This excpetion is raised when an error occurs during the parsing process.
*
* @author Francesco Azzola
* */
public class WeatherLibException extends Exception {

    public WeatherLibException() {
    }

    public WeatherLibException(String detailMessage) {
        super(detailMessage);
    }

    public WeatherLibException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public WeatherLibException(Throwable throwable) {
        super(throwable);
    }
}
