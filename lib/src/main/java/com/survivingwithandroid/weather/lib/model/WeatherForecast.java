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

import java.util.ArrayList;
import java.util.List;

/**
 * Forecast weather. This class holds the list of next day forecast
 *
 */
public class WeatherForecast extends Weather {

	private List<DayForecast> daysForecast = new ArrayList<DayForecast>();

    /*
    * Add a single day forecast
    *
    * @param forecast {@see DayForecast}
    * */
	public void addForecast(DayForecast forecast) {
		daysForecast.add(forecast);
	}

    /*
    * Retrive the daynum day in the list. For example if i want to know the tomorrow weather i will use 0 as index.
    *
    * @param dayNum int
    * @return {@see DayForecast}
    * */
	public DayForecast getForecast(int dayNum) {
		return daysForecast.get(dayNum);
	}

    /*
    * Get the full list
    *
    * @return {@see List} of {@see DayForecast}
    * */
	public List<DayForecast> getForecast() {
		return this.daysForecast;
	}
}
