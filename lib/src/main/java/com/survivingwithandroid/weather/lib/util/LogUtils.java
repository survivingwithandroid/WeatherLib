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

import android.util.Log;

/**
 * @author Francesco
 */
public class LogUtils {

    public static boolean DEBUG = true;

    public static final String WEATHER_TAG = "SwA";

    public static void LOGD(String TAG, String msg) {
        if (DEBUG && Log.isLoggable(TAG, Log.DEBUG))
            Log.d(TAG, msg);
    }

    public static void LOGD(String msg) {
        LOGD(WEATHER_TAG, msg);
    }

    public static void LOGD(String TAG, String msg, Throwable t) {
        if (DEBUG && Log.isLoggable(TAG, Log.DEBUG))
            Log.d(TAG, msg, t);
    }

}
