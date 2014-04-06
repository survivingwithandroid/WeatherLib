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

/**
 * @author Francesco
 *
 */
public enum WindDirection {
	
	    N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW;

	    /**
	     *  Creates the direction from the azimuth degrees.
	     */
	    public static WindDirection getDir(int deg) {
	        int degPositive = deg;
	        if (deg < 0) {
	            degPositive += (-deg / 360 + 1) * 360;
	        }
	        int degNormalized = degPositive % 360;
	        int degRotated = degNormalized + (360 / 16 / 2);
	        int sector = degRotated / (360 / 16);
	        switch (sector) {
	            case 0: return WindDirection.N;
	            case 1: return WindDirection.NNE;
	            case 2: return WindDirection.NE;
	            case 3: return WindDirection.ENE;
	            case 4: return WindDirection.E;
	            case 5: return WindDirection.ESE;
	            case 6: return WindDirection.SE;
	            case 7: return WindDirection.SSE;
	            case 8: return WindDirection.S;
	            case 9: return WindDirection.SSW;
	            case 10: return WindDirection.SW;
	            case 11: return WindDirection.WSW;
	            case 12: return WindDirection.W;
	            case 13: return WindDirection.WNW;
	            case 14: return WindDirection.NW;
	            case 15: return WindDirection.NNW;
	            case 16: return WindDirection.N;
	        }
	        return WindDirection.N;
	    }

	}	


