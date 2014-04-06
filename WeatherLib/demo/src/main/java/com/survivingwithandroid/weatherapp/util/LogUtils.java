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
package com.survivingwithandroid.weatherapp.util;

import android.util.Log;

import com.survivingwithandroid.weatherapp.Config;

/**
 * @author Francesco
 *
 */
public class LogUtils {
	
	public static final String WEATHER_TAG = "WeatherApp";
	
	public static void LOGD(String TAG, String msg) {
		if (Config.DEBUG && Log.isLoggable(TAG, Log.DEBUG))
			Log.d(TAG, msg);
	}

	public static void LOGD(String msg) {
       LOGD(WEATHER_TAG, msg);
    }
	public static void LOGD(String TAG, String msg, Throwable t) {
		if (Config.DEBUG && Log.isLoggable(TAG, Log.DEBUG))
			Log.d(TAG, msg, t);
	}
	
}
