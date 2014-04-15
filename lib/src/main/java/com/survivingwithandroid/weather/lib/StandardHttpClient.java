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
package com.survivingwithandroid.weather.lib;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * This class represents the entry point to get the Weather information. This is another implementation of {@link WeatherClient},
 * this is a synchronous client and you should consider it when using this class in your main thread.
 * This class uses a IWeatherProvider to parse the response. Internally this class is based on standard {@link java.net.HttpURLConnection}
 *
 * <p>
 * Usually you need only one instance of this class in your app so that the class implements the Singleton pattern
 * To get the reference to this class you have to use getInstance method:
 *</p>
 * {@code  WeatherClient client = StandardHttpClient.getInstance();}
 *<p>
 * soon after you the the reference and before you make any requests you have to pass the {@link android.content.Context}
 * to this class.
 *</p>
 * */
public class StandardHttpClient extends WeatherClient {

    public static int LOCATION_TIMEOUT = 5 ;

    private IWeatherProvider provider;
    private CityEventListener cityListener;
    private static StandardHttpClient me;

    public static StandardHttpClient getInstance() {
        if (me == null)
            me = new StandardHttpClient();

        return me;
    }

    /**
     * This method has to be called before any HTTP request
     *
     * @param ctx Reference to the {@link android.content.Context}
     */
    @Override
    public void init(Context ctx) {
        super.init(ctx);
    }

    /**
     * This method update the current provider configuration
     *
     * @param config WeatherConfig info
     * @see WeatherConfig
     */
    @Override
    public void updateWeatherConfig(WeatherConfig config) {
        super.updateWeatherConfig(config);
    }

    /**
     * Get the current weather condition. It returns a class structure that is indipendent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the current weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public void getCurrentCondition(String location, WeatherEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryCurrentWeatherURL(location);
        String data = null;
        try {
            data = connectAndRead(url);
        }
        catch (Throwable t) {
            listener.onConnectionError(t);
            return ;
        }

        try {
            Log.d("SwA", "Data [" + data + "]");
            CurrentWeather weather = provider.getCurrentCondition(data);
            listener.onWeatherRetrieved(weather);
        }
        catch (WeatherLibException t) {
            listener.onWeatherError(t);
        }
    }

    /**
     * Search the city using a name pattern. It returns a class structure that is indipendent from the
     * provider used that holds the city list matching the pattern.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onCityListRetrieved passing a {@link java.util.List} of cities.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param pattern  a String representing the pattern
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public void searchCity(String pattern, CityEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryCityURL(pattern);
        String data = null;
        try {
            data = connectAndRead(url);
        }
        catch (Throwable t) {
            listener.onConnectionError(t);
            return ;
        }

        try {
            Log.d("SwA", "Data [" + data + "]");
            List<City> cityResult = provider.getCityResultList(data);
            listener.onCityListRetrieved(cityResult);
        }
        catch (WeatherLibException t) {
            listener.onWeatherError(t);
        }
    }

    /**
     * Get the forecast weather condition. It returns a class structure that is indipendent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.WeatherForecast} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public void getForecastWeather(String location, ForecastWeatherEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryForecastWeatherURL(location);
        String data = null;
        try {
            data = connectAndRead(url);
        }
        catch (Throwable t) {
            listener.onConnectionError(t);
            return ;
        }

        try {
            Log.d("SwA", "Data [" + data + "]");
            WeatherForecast forecast = provider.getForecastWeather(data);
            listener.onWeatherRetrieved(forecast);
        }
        catch (WeatherLibException t) {
            listener.onWeatherError(t);
        }
    }

    @Override
    public void getDefaultProviderImage(String icon, WeatherImageListener listener) {

    }

    @Override
    public void searchCityByLocation(Criteria criteria, CityEventListener listener) throws LocationProviderNotFoundException {
        LocationManager locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = locManager.getBestProvider(criteria, true);

        if (locationProvider == null || "".equals(locationProvider))
            throw new LocationProviderNotFoundException();

        Location loc = locManager.getLastKnownLocation(locationProvider);
        if (loc == null ||
                (SystemClock.elapsedRealtime() - loc.getTime()) > LOCATION_TIMEOUT * 1000) {

            locManager.requestSingleUpdate(locationProvider, locListener, null);
        }
        else
            searchCityByLocation(loc, listener);
    }

    /**
     * This method is used to set the Weather provider. The Weather provider must implement ({@link com.survivingwithandroid.weather.lib.provider.IWeatherProvider}
     * interface. This method has to be called before making any HTTP request otherwise the client doesn't know how
     * to parse the request
     *
     * @param provider {@link com.survivingwithandroid.weather.lib.provider.IWeatherProvider}
     * @see {@link com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProvider}
     * @see {@link com.survivingwithandroid.weather.lib.provider.yahooweather.YahooWeatherProvider}
     */
    @Override
    public void setProvider(IWeatherProvider provider) {
        this.provider = provider;
    }

    private String connectAndRead(String url) throws Throwable {
        HttpURLConnection connection = null;
        StringBuffer buffer = new StringBuffer();
        try {

            connection = (HttpURLConnection) (new URL(url)).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            while ( (line = br.readLine() ) != null ) {
                buffer.append(line + "\r\n");
            }
        }
        catch(Throwable t) {
            throw t;
        }
        finally {
            try {  connection.disconnect(); }
            catch(Throwable t) { }
        }

        return buffer.toString();
    }

    private LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            searchCityByLocation(location, cityListener);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };

    private void searchCityByLocation(Location location, final CityEventListener listener)  throws ApiKeyRequiredException {
        String url = provider.getQueryCityURLByLocation(location);
        String data = null;
        try {
            data = connectAndRead(url);
        }
        catch (Throwable t) {
            listener.onConnectionError(t);
            return ;
        }

        try {
            Log.d("SwA", "Data [" + data + "]");
            List<City> cityResult = provider.getCityResultList(data);
            listener.onCityListRetrieved(cityResult);
        }
        catch (WeatherLibException t) {
            listener.onWeatherError(t);
        }
    }
}
