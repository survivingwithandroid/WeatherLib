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

package com.survivingwithandroid.weather.lib.client.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.os.Handler;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException;
import com.survivingwithandroid.weather.lib.exception.LocationProviderNotFoundException;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.HistoricalWeather;
import com.survivingwithandroid.weather.lib.model.HourForecast;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.model.WeatherHourForecast;
import com.survivingwithandroid.weather.lib.request.Params;
import com.survivingwithandroid.weather.lib.request.WeatherProviderFeature;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.survivingwithandroid.weather.lib.response.GenericResponseParser;
import com.survivingwithandroid.weather.lib.util.LogUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;


public class WeatherDefaultClient extends WeatherClient {
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
     * @deprecated use instead {@link com.survivingwithandroid.weather.lib.WeatherClient#getCurrentCondition(com.survivingwithandroid.weather.lib.request.WeatherRequest, com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener)}
     */
    @Override
    public void getCurrentCondition(String location, final WeatherEventListener listener) throws ApiKeyRequiredException {
        getCurrentCondition(new WeatherRequest(location), listener);
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
    public void searchCity(String pattern, final CityEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryCityURL(pattern);
        _doSearchCity(url, listener);
        /*
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                notifyConnectionError(e, listener);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final List<City> result = provider.getCityResultList(response.body().string());
                    Handler handler = new Handler(ctx.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onCityListRetrieved(result);
                        }
                    });

                } catch (WeatherLibException e) {
                    //listener.onWeatherError(e);
                    notifyWeatherError(e, listener);
                }
            }
        });
        */
    }

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
     * @param lat      a double representing the latitude
     * @param lon      a double representing the longitude
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.CityEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     * @since 1.5.3
     */
    @Override
    public void searchCity(double lat, double lon, CityEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryCityURLByCoord(lon, lat);
        _doSearchCity(url, listener);
    }

    private void _doSearchCity(String url, final CityEventListener listener) throws ApiKeyRequiredException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                notifyConnectionError(e, listener);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final List<City> result = provider.getCityResultList(response.body().string());
                    Handler handler = new Handler(ctx.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onCityListRetrieved(result);
                        }
                    });

                } catch (WeatherLibException e) {
                    //listener.onWeatherError(e);
                    notifyWeatherError(e, listener);
                }
            }
        });
    }

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
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     * @deprecated use instead {@link com.survivingwithandroid.weather.lib.WeatherClient#getForecastWeather(com.survivingwithandroid.weather.lib.request.WeatherRequest, com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener)}
     */
    @Override
    public void getForecastWeather(String location, final ForecastWeatherEventListener listener) throws ApiKeyRequiredException {
        getForecastWeather(new WeatherRequest(location), listener);
    }

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
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     * @deprecated use instead {@link com.survivingwithandroid.weather.lib.WeatherClient#getHourForecastWeather(com.survivingwithandroid.weather.lib.request.WeatherRequest, com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener)}
     */
    @Override
    public void getHourForecastWeather(String location, final HourForecastWeatherEventListener listener) throws ApiKeyRequiredException {
        getHourForecastWeather(new WeatherRequest(location), listener);
    }

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
     * @param location a String representing the location id
     * @param d1       is the starting date
     * @param d2
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HistoricalWeatherEventListener}  @param2 d2 is the end date
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     * @deprecated use instead {@link com.survivingwithandroid.weather.lib.WeatherClient#getHistoricalWeather(com.survivingwithandroid.weather.lib.request.WeatherRequest, java.util.Date, java.util.Date, com.survivingwithandroid.weather.lib.WeatherClient.HistoricalWeatherEventListener)}
     */
    @Override
    public void getHistoricalWeather(String location, Date d1, Date d2, final HistoricalWeatherEventListener listener) {
        getHistoricalWeather(new WeatherRequest(location), d1, d2, listener);
    }

    /**
     * This is the default image Provider that can be used to get the image provided by the Weather provider
     *
     * @param icon     String    The icon containing the weather code to retrieve the image
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherImageListener}
     */
    @Override
    public void getDefaultProviderImage(String icon, WeatherImageListener listener) {
        String imageURL = provider.getQueryImageURL(icon);
        downloadImage(imageURL, listener);
    }

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
    @Override
    public void searchCityByLocation(Criteria criteria, CityEventListener listener) throws LocationProviderNotFoundException {
        super.handleLocation(criteria, listener);

    }

    /**
     * This method retrieves the image using the url generated by the weahter provider {@link com.survivingwithandroid.weather.lib.provider.IWeatherProvider}
     *
     * @param cityId  String representing the city id
     * @param params   {@link com.survivingwithandroid.weather.lib.request.Params}: list of parameters used to create the image
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherImageListener} listener that gets notified when the image is ready to use
     */
    @Override
    public void getWeatherImage(String cityId, Params params, final WeatherImageListener listener) {
        String imageURL = provider.getQueryLayerURL(cityId, params);
        downloadImage(imageURL, listener);
    }

    private void downloadImage(String urlImage, final WeatherImageListener listener)  {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlImage).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                notifyConnectionError(e, listener);
            }


            @Override
            public void onResponse(Response response) throws IOException {
                final Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                Handler handler = new Handler(ctx.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onImageReady(bmp);
                    }
                });
            }
        });
    }


    @Override
    protected void searchCityByLocation(Location location, final CityEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryCityURLByLocation(location);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                notifyConnectionError(e, listener);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final List<City> cityList = provider.getCityResultList(response.body().string());
                    Handler handler = new Handler(ctx.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onCityListRetrieved(cityList);
                        }
                    });

                } catch (WeatherLibException e) {
                    //listener.onWeatherError(e);
                    notifyWeatherError(e, listener);
                }
            }
        });
    }


    // New methods


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
     * @param wRequest  {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public void getCurrentCondition(WeatherRequest wRequest, final WeatherEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryCurrentWeatherURL(wRequest);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                notifyConnectionError(e, listener);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final CurrentWeather currentWeather = provider.getCurrentCondition(response.body().string());
                    Handler handler = new Handler(ctx.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onWeatherRetrieved(currentWeather);
                        }
                    });
                } catch (WeatherLibException e) {
                    //listener.onWeatherError(e);
                    notifyWeatherError(e, listener);
                }
            }
        });
    }

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
     * @param wRequest  {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.ForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public void getForecastWeather(WeatherRequest wRequest, final ForecastWeatherEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryForecastWeatherURL(wRequest);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                notifyConnectionError(e, listener);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final WeatherForecast forecast = provider.getForecastWeather(response.body().string());
                    Handler handler = new Handler(ctx.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onWeatherRetrieved(forecast);
                        }
                    });
                } catch (WeatherLibException e) {
                    //listener.onWeatherError(e);
                    notifyWeatherError(e, listener);
                }
            }
        });
    }

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
     * @param wRequest  {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public void getHourForecastWeather(WeatherRequest wRequest, final HourForecastWeatherEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryHourForecastWeatherURL(wRequest);
        OkHttpClient client = new OkHttpClient();
        if (url == null) {
            url = provider.getQueryForecastWeatherURL(wRequest);
        }
        if (url != null) {
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Request request, IOException e) {
                    notifyConnectionError(e, listener);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        final WeatherHourForecast forecast = provider.getHourForecastWeather(response.body().string());
                        Handler handler = new Handler(ctx.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onWeatherRetrieved(forecast);
                            }
                        });
                        //listener.onWeatherRetrieved(forecast); // Issue OkHttpClient #7
                    } catch (WeatherLibException e) {
                        // listener.onWeatherError(e);
                        notifyWeatherError(e, listener);
                    }
                }
            });
        }
    }

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
     * @param wRequest  {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param d1       is the starting date
     * @param d2
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HistoricalWeatherEventListener}  @param2 d2 is the end date
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public void getHistoricalWeather(WeatherRequest wRequest, Date d1, Date d2, final HistoricalWeatherEventListener listener) {
        String url = provider.getQueryHistoricalWeatherURL(wRequest, d1, d2);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                notifyConnectionError(e, listener);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final HistoricalWeather histWeather = provider.getHistoricalWeather(response.body().string());
                    Handler handler = new Handler(ctx.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onWeatherRetrieved(histWeather);
                        }
                    });

                } catch (WeatherLibException e) {
                    //listener.onWeatherError(e);
                    notifyWeatherError(e, listener);
                }
            }
        });
    }


    /**
     * Get a specific weather provider feature not implemented in all weather provider
     * <p>
     * When the data is ready this method calls the onWeatherRetrieved passing the {@link com.survivingwithandroid.weather.lib.model.HistoricalWeather} weather information.
     * If there are some errors during the request parsing, it calls onWeatherError passing the exception or
     * onConnectionError if the errors happened during the HTTP connection
     * </p>
     *
     * @param wRequest    {@link com.survivingwithandroid.weather.lib.request.WeatherRequest}
     * @param extRequest is the extended request as required by the weather provider
     * @param parser     is the parser used to parsed the response {@link com.survivingwithandroid.weather.lib.response.GenericResponseParser}
     * @param listener   {@link com.survivingwithandroid.weather.lib.WeatherClient.GenericRequestWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public <T extends WeatherProviderFeature, S extends Object> void getProviderWeatherFeature(WeatherRequest wRequest, T extRequest, final GenericResponseParser<S> parser, final GenericRequestWeatherEventListener<S> listener) {
        String url = extRequest.getURL();
        LogUtils.LOGD("Generic Weather feature URL [" + url + "]");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                notifyConnectionError(e, listener);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final S result = parser.parseData(response.body().string());
                    Handler handler = new Handler(ctx.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onResponseRetrieved(result);
                        }
                    });

                } catch (WeatherLibException e) {
                    //listener.onWeatherError(e);
                    notifyWeatherError(e, listener);
                }
            }
        });
    }


    /**
     * Get an image at the specified URL and inform the listener when the image is ready
     *
     * @param url      String representing the url
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherImageListener}
     * @since 1.5.3
     */
    @Override
    public void getImage(String url, WeatherImageListener listener) {
        downloadImage(url, listener);
    }

    // Notify connection error on main thread so that the client can show dialogs,toast etc. to notify the error to the final user
    private void notifyConnectionError(final Throwable t, final WeatherClientListener listener) {
        Handler handler = new Handler(ctx.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onConnectionError(t);
            }
        });
    }

    private void notifyWeatherError(final WeatherLibException wle, final WeatherClientListener listener) {
        Handler handler = new Handler(ctx.getMainLooper()); // Hanlder
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onWeatherError(wle);
            }
        });
    }
}
