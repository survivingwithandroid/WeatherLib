package com.survivingwithandroid.weather.lib.client.okhttp;

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

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * ${copyright}.
 */
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
     */
    @Override
    public void getCurrentCondition(String location, final WeatherEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryCurrentWeatherURL(location);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, Throwable throwable) {
                listener.onConnectionError(throwable);
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
                    listener.onWeatherError(e);
                }
            }
        });
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
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, Throwable throwable) {
                listener.onConnectionError(throwable);
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
                    listener.onWeatherError(e);
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
     */
    @Override
    public void getForecastWeather(String location, final ForecastWeatherEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryForecastWeatherURL(location);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, Throwable throwable) {
                listener.onConnectionError(throwable);
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
                    listener.onWeatherError(e);
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
     * @param location a String representing the location id
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.HourForecastWeatherEventListener}
     * @throws com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException
     */
    @Override
    public void getHourForecastWeather(String location, final HourForecastWeatherEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryHourForecastWeatherURL(location);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, Throwable throwable) {
                listener.onConnectionError(throwable);
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
                    listener.onWeatherRetrieved(forecast);
                } catch (WeatherLibException e) {
                    listener.onWeatherError(e);
                }
            }
        });
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
     */
    @Override
    public void getHistoricalWeather(String location, Date d1, Date d2, final HistoricalWeatherEventListener listener) {
        String url = provider.getQueryHistoricalWeatherURL(location, d1, d2);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, Throwable throwable) {
                listener.onConnectionError(throwable);
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
                    listener.onWeatherError(e);
                }
            }
        });
    }

    /**
     * This is the default image Provider that can be used to get the image provided by the Weather provider
     *
     * @param icon     String    The icon containing the weather code to retrieve the image
     * @param listener {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherImageListener}
     */
    @Override
    public void getDefaultProviderImage(String icon, WeatherImageListener listener) {

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

    @Override
    protected void searchCityByLocation(Location location, final CityEventListener listener) throws ApiKeyRequiredException {
        String url = provider.getQueryCityURLByLocation(location);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, Throwable throwable) {
                listener.onConnectionError(throwable);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    List<City> cityList = provider.getCityResultList(response.body().string());
                    listener.onCityListRetrieved(cityList);
                } catch (WeatherLibException e) {
                    listener.onWeatherError(e);
                }
            }
        });
    }
}
