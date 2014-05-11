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
package com.survivingwithandroid.weatherapp.widget;

import com.survivingwithandroid.weatherapp.service.WeatherService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * @author Francesco
 *
 */
public class WeatherWidget extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {		
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {		
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (final int appWidgetId : appWidgetIds) {
        	Intent widdgetIntent = new Intent(context, WeatherService.class);
        	widdgetIntent.setAction(WeatherService.ACTION_FORCE_UPDATE);
        	widdgetIntent.putExtra(WeatherService.WIDGET_ID, appWidgetId);
        	context.startService(widdgetIntent);        	
        }
	}
	
	

}
