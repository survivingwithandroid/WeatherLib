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
package com.survivingwithandroid.weather.lib.util;


public class UnitUtility {


    public static float toCelcius(float temp) {
        return (int) Math.round((temp - 32) / 1.8);
    }

    public static int toFar(float temp) {
        return (int) ((temp - 273.15) * 1.8 + 32);
    }

    public static int toKMH(float val) {
        return (int) Math.round(val * 0.2778);
    }


}
