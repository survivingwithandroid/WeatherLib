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
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.HistoricalWeather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.model.WeatherHourForecast;
import com.survivingwithandroid.weather.lib.provider.IProviderType;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;
import com.survivingwithandroid.weather.lib.provider.IWeatherProvider;
import com.survivingwithandroid.weather.lib.provider.WeatherProviderFactory;
import com.survivingwithandroid.weather.lib.request.Params;
import com.survivingwithandroid.weather.lib.request.WeatherProviderFeature;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.survivingwithandroid.weather.lib.response.GenericResponseParser;
import com.survivingwithandroid.weather.lib.util.LogUtils;

import java.util.Date;
import java.util.List;

/**
 * This class represents the entry point to get the Weather information.
 * This class uses a IWeatherProvider to parse the response. Internally this class is based on Android Volley lib
 * so that you don't have to worry about Thread hanlding because the class makes the remote HTTP request
 * on a separate thread.
 * <p>
 * Usually you need only one instance of this class in your app so that the class implements the Singleton pattern
 * To get the reference to this class you have to use getInstance method:
 * </p>
 * {@code WeatherClient client = WeatherClient.getInstance();}
 * <p>
 * soon after you the the reference and before you make any requests you have to pass the {@link android.content.Context}
 * to this class.
 * </p>
 *
 * @author Francesco Azzola
 */

public abstract class WeatherClient {


    private static WeatherClient me;
    protected IWeatherProvider provider;
    protected Context ctx;
    protected CityEventListener cityListener;

    /*
    * This parameter represents the amount of time before considering the location out of date
    * It must be expressed in seconds
    *
    * */
    public static int LOCATION_TIMEOUT = 5;


    /**
     * This method has to be called before any HTTP request
     *
     * @param ctx Reference to the {@link android.content.Context}
     */
    public void init(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * This method update the current provider configuration
     *
     * @param config WeatherConfig info
     * @see com.survivingwithandroid.weather.lib.WeatherConfig
     */
    public void updateWeatherConfig(WeatherConfig config) {
        provider.setConfig(config);
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
     * @deprecated 1.5.0 Use instead {@link com.survivingwithandroid.weather.lib.WeatherClient#getCurrentCondition(com.survivingwithandroid.weather.lib.request.WeatherRequest, com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener)}
     *
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    public abstract void getCurrentCondition(String location, final WeatherEventListener listener) throws ApiKeyRequiredException;

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
    public abstract void searchCity(String pattern, final CityEventListener listener) throws ApiKeyRequiredException;

    /**
     * Search the city using latitude and longitude. It returns a class structure that is indipendent from the
     * provider used that holds the city list matching the pattern.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onCityListRetrieved passing a {@link java.util.List} of cities.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param lat  a double representing the latitude
     * @param lon a double representing the longitude
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     *
     * @since 1.5.3
     */
    public abstract void searchCity(double lat, double lon, final CityEventListener listener) throws ApiKeyRequiredException;

    /**
     * Get the forecast weather condition. It returns a class structure that is independent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.WeatherForecast} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @deprecated 1.5.0 Use instead {@link com.survivingwithandroid.weather.lib.WeatherClient#getForecastWeather(com.survivingwithandroid.weather.lib.request.WeatherRequest, com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener)}
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */

    public abstract void getForecastWeather(String location, final ForecastWeatherEventListener listener) throws ApiKeyRequiredException;


    /**
     * Get the forecast weather condition. It returns a class structure that is independent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.WeatherHourForecast} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @deprecated 1.5.0 use instead {@link com.survivingwithandroid.weather.lib.WeatherClient#getHourForecastWeather(com.survivingwithandroid.weather.lib.request.WeatherRequest, com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener)}
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */

    public abstract void getHourForecastWeather(String location, final HourForecastWeatherEventListener listener) throws ApiKeyRequiredException;

    /**
     * Get the historical weather condition. It returns a class structure that is independent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HistoricalWeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.HistoricalWeather} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @deprecated 1.5.0 {@link com.survivingwithandroid.weather.lib.WeatherClient#getHistoricalWeather(com.survivingwithandroid.weather.lib.request.WeatherRequest, java.util.Date, java.util.Date, com.survivingwithandroid.weather.lib.WeatherClient.HistoricalWeatherEventListener)}
     * @param location a String representing the location id
     * @param d1 is the starting date
     * @param d2 is the end date
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HistoricalWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    public abstract void getHistoricalWeather(String location , Date d1, Date d2, final HistoricalWeatherEventListener listener);


    /**
    * This is the default image Provider that can be used to get the image provided by the Weather provider
    * @param icon String    The icon containing the weather code to retrieve the image
    * @param listener       {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherImageListener}
    * */
    public abstract void getDefaultProviderImage(String icon, final WeatherImageListener listener);



    /**
     * Search the city using geographic coordinates. It returns a class structure that is independent from the
     * provider used that holds the city list matching the pattern.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onCityListRetrieved passing a {@link java.util.List} of cities.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param criteria {@link android.location.Criteria}
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    public abstract void searchCityByLocation(Criteria criteria, final CityEventListener listener) throws LocationProviderNotFoundException;


    /**
     * This method retrieves the image using the url generated by the weahter provider {@link com.survivingwithandroid.weather.lib.provider.IWeatherProvider}
     *
     * @param cityId String representing the city id
     * @param params {@link Params}: list of parameters used to create the image
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherImageListener} listener that gets notified when the image is ready to use
     */
    public abstract void getWeatherImage(String cityId, Params params, final WeatherImageListener listener);

    /**
     * This method is used to set the Weather provider. The Weather provider must implement ({@link com.survivingwithandroid.weather.lib.provider.IWeatherProvider}
     * interface. This method has to be called before making any HTTP request otherwise the client doesn't know how
     * to parse the request
     *
     * @param provider {@link com.survivingwithandroid.weather.lib.provider.IWeatherProvider}
     * @see {@link com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProvider}
     * @see {@link com.survivingwithandroid.weather.lib.provider.yahooweather.YahooWeatherProvider}
     */
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
     */

    public static interface WeatherClientListener {
        /**
         * This method is called when an error occured during the data parsing
         *
         * @param wle {@link com.survivingwithandroid.weather.lib.exception.WeatherLibException}
         */
        public void onWeatherError(WeatherLibException wle);

        /**
         * This method is called when an error occured during the HTTP connection
         *
         * @param t {@link Throwable}
         */
        public void onConnectionError(Throwable t);
    }


    /**
     * This interface must be implemented by the client that wants to get informed when
     * the city list is ready.
     */
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
     */
    public static interface WeatherEventListener extends WeatherClientListener {

        /**
         * This method is called to notify to the listener that the Weather information is ready
         *
         * @param weather {@link com.survivingwithandroid.weather.lib.model.CurrentWeather}
         */
        public void onWeatherRetrieved(CurrentWeather weather);
    }

    /**
     * This interface must be implemented by the client that wants to get informed when
     * the forecast weather data is ready.
     */
    public static interface ForecastWeatherEventListener extends WeatherClientListener {

        /**
         * This method is called to notify to the listener that the Weather information is ready
         *
         * @param forecast {@link com.survivingwithandroid.weather.lib.model.WeatherForecast}
         */

        public void onWeatherRetrieved(WeatherForecast forecast);
    }

    /**
     * This interface must be implemented by the client that wants to get informed when
     * the forecast weather image is ready.
     */

    public static interface WeatherImageListener extends WeatherClientListener {
        /**
         * This method is called to notify to the listener that the Weather information is ready
         *
         * @param image {@link android.graphics.Bitmap}
         */

        public void onImageReady(Bitmap image);
    }

    /**
     * This interface must be implemented by the client that wants to get informed when
     * the  hour forecast weather data is ready.
     */
    public static interface HourForecastWeatherEventListener extends WeatherClientListener {

        /**
         * This method is called to notify to the listener that the Weather information is ready
         *
         * @param forecast {@link com.survivingwithandroid.weather.lib.model.WeatherHourForecast}
         */

        public void onWeatherRetrieved(WeatherHourForecast forecast);
    }

    /**
     * This interface must be implemented by the client that wants to get informed when
     * the  historical weather data is ready.
     */
    public static interface HistoricalWeatherEventListener extends WeatherClientListener {

        /**
         * This method is called to notify to the listener that the historical Weather information is ready
         *
         * @param histWeather {@link com.survivingwithandroid.weather.lib.model.HistoricalWeather}
         */

        public void onWeatherRetrieved(HistoricalWeather histWeather);
    }


    /**
     * This interface must be implemented by the client that wants to get informed when
     * the  generic request is available
     *
     * @since 1.5.3
     */
    public static interface GenericRequestWeatherEventListener<T> extends WeatherClientListener {

        public void onResponseRetrieved(T data);
    }


    /**
    * This method creates the Weather provider. It is the same:
    *
    * <code>
    *    IWeatherProvider provider = null;
    *    try {
    *         provider = WeatherProviderFactory.createProvider(new OpenweathermapProviderType(), config);
    *         client.setProvider(provider);
    *    }
    *    catch (Throwable t) {
    *        t.printStackTrace();
    *        // There's a problem
    *    }
    *  </code>
    *
    *  @deprecated Version 1.4 use instead {@link com.survivingwithandroid.weather.lib.WeatherClient.ClientBuilder}
    **/
    public void createProvider(IProviderType providerType, WeatherConfig config) throws WeatherProviderInstantiationException {
        provider = WeatherProviderFactory.createProvider(providerType, config);
    }


    /**
     * This class must be used to obtain a valid instance of WeatherClient. It accepts several config params
     * and at the end you should call build() to create the client.
     *
     * Ex:
     *      client = builder.attach(this)
     *               .provider(new OpenweathermapProviderType())
     *               .httpClient(com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault.class)
     *               .config(new WeatherConfig())
     *               .build();
     */
    public static final class ClientBuilder {
        private Context ctx;
        private IProviderType providerType;
        private WeatherConfig config;
        private Class httpWeatherClient;

        /**
         * Attach a {@link android.content.Context} to the WeatherClient
         * */
        public ClientBuilder attach(Context ctx) {
            this.ctx = ctx;
            return this;
        }

        /**
         * Set the provider ({@link com.survivingwithandroid.weather.lib.provider.IProviderType})
         * */
        public ClientBuilder provider(IProviderType providerType) {
            this.providerType = providerType;
            return this;
        }

        /**
        * Set the configuration ({@link com.survivingwithandroid.weather.lib.WeatherConfig})
        * */
        public ClientBuilder config(WeatherConfig config) {
            this.config = config;
            return this;
        }

        /**
         * Attach the http Client that has to be used to make requests.
         *
         * @see com.survivingwithandroid.weather.lib.StandardHttpClient
         **/
        public ClientBuilder httpClient(Class httpClient) {
            this.httpWeatherClient = httpClient;
            return this;
        }

        /**
         * Build the weather client setting the right parameters
         * */
        public WeatherClient build() throws WeatherProviderInstantiationException {
            // Create provider
            IWeatherProvider provider = _createProvider(providerType, config);
            WeatherClient client = null;
            try {
                client = (WeatherClient) httpWeatherClient.newInstance();
                client.init(ctx);
                Log.d("SwA", "Client ["+client+"]");
                client.setProvider(provider);
                client.updateWeatherConfig(config);
                return client;
            }
            catch (InstantiationException e) {
                e.printStackTrace();
                throw new WeatherProviderInstantiationException();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new WeatherProviderInstantiationException();
            }
        }

        private static IWeatherProvider _createProvider(IProviderType providerType, WeatherConfig config) throws WeatherProviderInstantiationException {
            try {
                Class<?> clazz = Class.forName(providerType.getProviderClass());
                IWeatherProvider provider = (IWeatherProvider) clazz.newInstance();

                if (config != null)
                    provider.setConfig(config);

                if (providerType.getCodeProviderClass() != null) {
                    Class<?> clazzCodeProvider = Class.forName(providerType.getCodeProviderClass());
                    IWeatherCodeProvider codeProvider = (IWeatherCodeProvider) clazzCodeProvider.newInstance();
                    provider.setWeatherCodeProvider(codeProvider);
                }

                return provider;
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
                throw new WeatherProviderInstantiationException();
            }
            catch (InstantiationException e) {
                e.printStackTrace();
                throw new WeatherProviderInstantiationException();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new WeatherProviderInstantiationException();
            }
        }
    }


    protected void handleLocation(Criteria criteria, final CityEventListener listener) throws LocationProviderNotFoundException {
        this.cityListener = listener;
        LocationManager locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = locManager.getBestProvider(criteria, true);

        LogUtils.LOGD("Provider [" + locationProvider + "]");

        if (locationProvider == null || "".equals(locationProvider))
            throw new LocationProviderNotFoundException();

        Location loc = locManager.getLastKnownLocation(locationProvider);
        if (loc == null ||
                (SystemClock.elapsedRealtime() - loc.getTime()) > LOCATION_TIMEOUT * 1000) {

            locManager.requestSingleUpdate(locationProvider, locListener, null);
        } else
            searchCityByLocation(loc, listener);
    }

    protected  LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            searchCityByLocation(location, cityListener);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    protected abstract void searchCityByLocation(Location location, final CityEventListener listener) throws ApiKeyRequiredException;

    // New abstract methods

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
     * @param request {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    public abstract void getCurrentCondition(WeatherRequest request, final WeatherEventListener listener) throws ApiKeyRequiredException;


    /**
     * Get the forecast weather condition. It returns a class structure that is independent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.WeatherForecast} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param request {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */

    public abstract void getForecastWeather(WeatherRequest request, final ForecastWeatherEventListener listener) throws ApiKeyRequiredException;

    /**
     * Get the forecast weather condition. It returns a class structure that is independent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.WeatherHourForecast} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param request {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */

    public abstract void getHourForecastWeather(WeatherRequest request, final HourForecastWeatherEventListener listener) throws ApiKeyRequiredException;


    /**
     * Get the historical weather condition. It returns a class structure that is independent from the
     * provider used to ge the weather data.
     * This method is an async method, in other word you have to implement your listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HistoricalWeatherEventListener} to
     * get notified when the weather data is ready.
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.HistoricalWeather} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param request {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param d1 is the starting date
     * @param d2 is the end date
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HistoricalWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    public abstract void getHistoricalWeather(WeatherRequest request, Date d1, Date d2, final HistoricalWeatherEventListener listener);


    /**
     * Get a specific weather provider feature not implemented in all weather provider
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.HistoricalWeather} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param request {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param extRequest is the extended request as required by the weather provider
     * @param parser  is the parser used to parsed the response {@link com.survivingwithandroid.weather.lib.response.GenericResponseParser}
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.GenericRequestWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */

    public abstract <T extends WeatherProviderFeature, S extends Object>  void getProviderWeatherFeature(WeatherRequest request, T extRequest, GenericResponseParser<S> parser, GenericRequestWeatherEventListener<S> listener);


    /**
     * Get an image at the specified URL and inform the listener when the image is ready
     *
     * @param url String representing the url
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherImageListener}
     * @since 1.5.3
     * */
    public abstract void getImage(String url, WeatherImageListener listener);
}
