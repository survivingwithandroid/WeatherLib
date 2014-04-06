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
package com.survivingwithandroid.weatherapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Francesco
 *
 */
public class WeatherService extends Service {
	
	
	public static String ACTION_FORCE_UPDATE = "com.survivingwithandroid.weatherapp.FORCE_UPDATE";

	public static String WIDGET_ID = "com.survivingwithandroid.weatherapp.WIDGET_ID";
	
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {		
		return null;
	}

}
