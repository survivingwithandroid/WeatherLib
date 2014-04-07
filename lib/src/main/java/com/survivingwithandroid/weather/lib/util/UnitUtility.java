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


public class UnitUtility {
	


	public static float toCelcius(float temp) {
	   return (int) Math.round( (temp - 32) / 1.8);
	}
	
	public static int toFar(float temp) {
		return (int) ((temp - 273.15) * 1.8 + 32);
	}

	public static int toKMH(float val) {
		return (int)Math.round(val * 0.2778);
	}
	

}
