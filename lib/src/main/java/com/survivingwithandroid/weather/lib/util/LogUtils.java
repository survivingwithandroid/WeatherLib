/**
 * This is a tutorial source code 
 * provided "as is" and without warranties.
 *
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 *
 * or write an email to
 * survivingwithandroid@gmail.com
 *
 */
package com.survivingwithandroid.weather.lib.util;

import android.util.Log;

/**
 * @author Francesco
 *
 */
public class LogUtils {

    public static boolean DEBUG = true;

	public static final String WEATHER_TAG = "WeatherLib";
	
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
