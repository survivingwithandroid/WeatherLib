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
package com.survivingwithandroid.weather.lib.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Single day weather forecast. This class holds the information about next days weather conditions.
 *
 */
public class DayForecast {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    /*
    * Current weather information for the day.
    *
    * {@see CurrentWeather}
    * */
	public CurrentWeather weather = new CurrentWeather();
	public ForecastTemp forecastTemp = new ForecastTemp();
	public long timestamp;

    /*
    * Forecast temperature
    * */
	public class ForecastTemp {
		public float day;
		public float min;
		public float max;
		public float night;
		public float eve;
		public float morning;
	}
	
	public String getStringDate() {
		return sdf.format(new Date(timestamp));
	}
}
