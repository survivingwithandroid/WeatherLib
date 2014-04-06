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
package com.survivingwithandroid.weather.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException;
import com.survivingwithandroid.weather.lib.exception.LocationProviderNotFoundException;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.provider.IWeatherProvider;
import com.survivingwithandroid.weather.lib.util.LogUtils;

import java.util.List;
import com.android.volley.*;
import com.android.volley.toolbox.*;

/**
 * This class represents the entry point to get the Weather information.
 * This class uses a IWeatherProvider to parse the response. Internally this class is based on Android Volley lib
 * so that you don't have to worry about Thread hanlding because the class makes the remote HTTP request
 * on a separate thread.
 * <p>
 * Usually you need only one instance of this class in your app so that the class implements the Singleton pattern
 * To get the reference to this class you have to use getInstance method:
 *</p>
 * {@code  WeatherClient client = WeatherClient.getInstance();}
 *<p>
 * soon after you the the reference and before you make any requests you have to pass the {@link android.content.Context}
 * to this class.
 *</p>
 * */

public abstract class WeatherClient  {


    private static WeatherClient me;
    private IWeatherProvider provider;
    protected Context ctx;
    private CityEventListener cityListener;

    /**
     * This method has to be called before any HTTP request
     * @param ctx Reference to the {@link android.content.Context}
     * */
    public void init(Context ctx) {
        this.ctx = ctx;
    }

    /***
     * This method update the current provider configuration
     * @param config WeatherConfig info
     * @see com.survivingwithandroid.weather.lib.WeatherConfig
     * */

    public void updateWeatherConfig(WeatherConfig config) {
        provider.setConfig(config);
    }

    /**
     * Get the current weather condition. It returns a class structure that is indipendent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     *     When the data is ready this method calls the onWeatherRetrieved passing the current weather information.
     *     If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     *     onConnectionError if the errors happened during the HTTP connection
     * </p>
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     * */
    public abstract void getCurrentCondition(String location, final WeatherEventListener listener) throws ApiKeyRequiredException ;

    /**
     * Search the city using a name pattern. It returns a class structure that is indipendent from the
     * provider used that holds the city list matching the pattern.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener} to
     * get notified when the weather data is ready.
     * <p>
     *     When the data is ready this method calls the onCityListRetrieved passing a {@link java.util.List} of cities.
     *     If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     *     onConnectionError if the errors happened during the HTTP connection
     * </p>
     * @param pattern a String representing the pattern
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     * */
    public abstract void searchCity(String pattern, final CityEventListener listener)  throws ApiKeyRequiredException;

    /**
     * Get the forecast weather condition. It returns a class structure that is indipendent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     *     When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.WeatherForecast} weather information.
     *     If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     *     onConnectionError if the errors happened during the HTTP connection
     * </p>
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     * */

    public abstract void getForecastWeather(String location, final ForecastWeatherEventListener listener)  throws ApiKeyRequiredException;

    /*
    * This is the default imee Provider that can be used to get the image provided by the Weather provider
    * @param icon String    The icon containing the weather code to retrieve the image
    * @param listener       {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherImageListener}
    * */
    public abstract void getDefaultProviderImage(String icon, final WeatherImageListener listener);



    public abstract void searchCityByLocation(Criteria criteria, final CityEventListener listener) throws LocationProviderNotFoundException;

    /**
     *
     * This method is used to set the Weather provider. The Weather provider must implement ({@link com.survivingwithandroid.weather.lib.provider.IWeatherProvider}
     * interface. This method has to be called before making any HTTP request otherwise the client doesn't know how
     * to parse the request
     *
     * @param provider {@link com.survivingwithandroid.weather.lib.provider.IWeatherProvider}
     *
     * @see {@link com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProvider}
     * @see {@link com.survivingwithandroid.weather.lib.provider.yahooweather.YahooWeatherProvider}
     *
     * */
    public void setProvider(IWeatherProvider provider) {
        this.provider = provider;
    }


    public static Criteria createDefaultCriteria() {
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);

        return criteria;
    }

    /**
     * This is the base interface.
     *
     * @see com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener
     * @see com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener
     * @see com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener
     *
     * */

    private static interface WeatherClientListener {
        /**
         * This method is called when an error occured during the data parsing
         *
         * @param wle {@link com.survivingwithandroid.weather.lib.exception.WeatherLibException}
         * */
        public void onWeatherError(WeatherLibException wle);

        /**
         * This method is called when an error occured during the HTTP connection
         *
         * @param t {@link Throwable}
         * */
        public void onConnectionError(Throwable t);
    }


    /**
     * This interface must be implemented by the client that wants to get informed when
     * the city list is ready.
     * */
    public static interface CityEventListener extends WeatherClientListener {
        /**
         * This method is called to notify to the listener that the {@link com.survivingwithandroid.weather.lib.model.City} list is ready
         *
         * @param cityList {@link java.util.List}
         */

        public void onCityListRetrieved(List<City> cityList);
    }

    /**
     * This interface must be implemented by the client that wants to get informed when
     * the weather data is ready.
     *      */
    public static interface WeatherEventListener extends WeatherClientListener {

        /**
         * This method is called to notify to the listener that the Weather information is ready
         *
         * @param weather  {@link com.survivingwithandroid.weather.lib.model.CurrentWeather}
         */
        public void onWeatherRetrieved(CurrentWeather weather);
    }

    /**
     * This interface must be implemented by the client that wants to get informed when
     * the forecast weather data is ready.
     *      */
    public static interface ForecastWeatherEventListener extends WeatherClientListener {
        /**
         * This method is called to notify to the listener that the Weather information is ready
         *
         * @param forecast  {@link com.survivingwithandroid.weather.lib.model.WeatherForecast}
         */

        public void onWeatherRetrieved(WeatherForecast forecast);
    }

    /**
     * This interface must be implemented by the client that wants to get informed when
     * the forecast weather image is ready.
     *
     **/

    public static interface WeatherImageListener {
        /**
         * This method is called to notify to the listener that the Weather information is ready
         *
         * @param image  {@link android.graphics.Bitmap}
         */

        public void onImageReady(Bitmap image);
    }

}
