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

package com.survivingwithandroid.weather.lib.provider.openweathermap;

import android.util.Log;

import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.HourForecast;
import com.survivingwithandroid.weather.lib.model.Location;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.model.WeatherHourForecast;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;
import com.survivingwithandroid.weather.lib.provider.IWeatherProvider;
import com.survivingwithandroid.weather.lib.util.WeatherUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OpenweathermapProvider implements IWeatherProvider {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?mode=json&q=";
    private static String BASE_URL_ID = "http://api.openweathermap.org/data/2.5/weather?mode=json&id=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String SEARCH_URL = "http://api.openweathermap.org/data/2.5/find?mode=json&type=like&q=";
    private static String SEARCH_URL_GEO = "http://api.openweathermap.org/data/2.5/find?mode=json&type=accurate";
    private static String BASE_FORECAST_URL_ID = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&id=";
    private static String BASE_HOUR_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?mode=json&id=";

    private WeatherConfig config;
    private Weather.WeatherUnit units = new Weather.WeatherUnit();
    private IWeatherCodeProvider codeProvider;

    public CurrentWeather getCurrentCondition(String data) throws WeatherLibException {
        //LogUtils.LOGD("JSON CurrentWeather ["+data+"]");
        CurrentWeather weather = new CurrentWeather();
        try {
            // We create out JSONObject from the data
            JSONObject jObj = new JSONObject(data);

            // We start extracting the info
            Location loc = new Location();

            JSONObject coordObj = getObject("coord", jObj);
            loc.setLatitude(getFloat("lat", coordObj));
            loc.setLongitude(getFloat("lon", coordObj));

            JSONObject sysObj = getObject("sys", jObj);
            loc.setCountry(getString("country", sysObj));
            loc.setSunrise(getInt("sunrise", sysObj));
            loc.setSunset(getInt("sunset", sysObj));
            loc.setCity(getString("name", jObj));
            weather.location = loc;

            // We get weather info (This is an array)
            JSONArray jArr = jObj.getJSONArray("weather");

            // We use only the first value
            JSONObject JSONWeather = jArr.getJSONObject(0);
            weather.currentCondition.setWeatherId(getInt("id", JSONWeather));

            // Convert internal code
            if (codeProvider != null)
                weather.currentCondition.setWeatherCode(codeProvider.getWeatherCode(weather.currentCondition.getWeatherId()));

            weather.currentCondition.setDescr(getString("description", JSONWeather));
            weather.currentCondition.setCondition(getString("main", JSONWeather));
            weather.currentCondition.setIcon(getString("icon", JSONWeather));

            JSONObject mainObj = getObject("main", jObj);
            weather.currentCondition.setHumidity(getInt("humidity", mainObj));
            weather.currentCondition.setPressure(getInt("pressure", mainObj));
            weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
            weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
            weather.temperature.setTemp(getFloat("temp", mainObj));

            // Wind
            JSONObject wObj = getObject("wind", jObj);
            weather.wind.setSpeed(getFloat("speed", wObj));
            weather.wind.setDeg(getFloat("deg", wObj));
            try {
                weather.wind.setGust(getFloat("gust", wObj));
            } catch (Throwable t) {
            }

            // Clouds
            JSONObject cObj = getObject("clouds", jObj);
            weather.clouds.setPerc(getInt("all", cObj));

            // Rain
            try {
                JSONObject rObj = getObject("rain", jObj);
                weather.rain.setAmmount(getFloat("1h", rObj));
                weather.rain.setTime("1h");
            } catch (Throwable t) {
            }

            // Snow
            try {
                JSONObject sObj = getObject("snow", jObj);
                weather.rain.setAmmount(getFloat("3h", sObj));
            } catch (Throwable t) {
            }

        } catch (JSONException json) {
            json.printStackTrace();
            throw new WeatherLibException(json);
        }
        weather.setUnit(units);

        return weather;
    }

    public WeatherForecast getForecastWeather(String data) throws WeatherLibException {

        WeatherForecast forecast = new WeatherForecast();

        try {

            // We create out JSONObject from the data
            JSONObject jObj = new JSONObject(data);

            JSONArray jArr = jObj.getJSONArray("list"); // Here we have the forecast for every day

            // We traverse all the array and parse the data
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject jDayForecast = jArr.getJSONObject(i);

                // Now we have the json object so we can extract the data
                DayForecast df = new DayForecast();

                // We retrieve the timestamp (dt)
                df.timestamp = jDayForecast.getLong("dt");

                // Clouds
                df.weather.clouds.setPerc(jDayForecast.getInt("clouds"));


                // Rain
                try {
                 df.weather.rain.setAmmount((float) jDayForecast.getDouble("rain"));
                }
                catch(Throwable t) { // ignore
                }

                // Temp is an object
                JSONObject jTempObj = jDayForecast.getJSONObject("temp");

                df.forecastTemp.day = (float) jTempObj.getDouble("day");
                df.forecastTemp.min = (float) jTempObj.getDouble("min");
                df.forecastTemp.max = (float) jTempObj.getDouble("max");
                df.forecastTemp.night = (float) jTempObj.getDouble("night");
                df.forecastTemp.eve = (float) jTempObj.getDouble("eve");
                df.forecastTemp.morning = (float) jTempObj.getDouble("morn");

                // Pressure & Humidity
                df.weather.currentCondition.setPressure((float) jDayForecast.getDouble("pressure"));
                df.weather.currentCondition.setHumidity((float) jDayForecast.getDouble("humidity"));

                // ...and now the weather
                JSONArray jWeatherArr = jDayForecast.getJSONArray("weather");
                JSONObject jWeatherObj = jWeatherArr.getJSONObject(0);
                df.weather.currentCondition.setWeatherId(getInt("id", jWeatherObj));

                // Convert internal code
                if (codeProvider != null)
                    df.weather.currentCondition.setWeatherCode(codeProvider.getWeatherCode(df.weather.currentCondition.getWeatherId()));

                df.weather.currentCondition.setDescr(getString("description", jWeatherObj));
                df.weather.currentCondition.setCondition(getString("main", jWeatherObj));
                df.weather.currentCondition.setIcon(getString("icon", jWeatherObj));

                forecast.addForecast(df);
            }
        } catch (JSONException json) {
            throw new WeatherLibException(json);
        }

        forecast.setUnit(units);
        return forecast;
    }


    public List<City> getCityResultList(String data) throws WeatherLibException {
        List<City> cityList = new ArrayList<City>();
        // Log.d("SwA", "Data ["+data+"]");
        try {

            JSONObject jObj = new JSONObject(data);
            JSONArray jArr = jObj.getJSONArray("list");

            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj = jArr.getJSONObject(i);

                String name = obj.getString("name");
                String id = obj.getString("id");

                JSONObject sys = obj.getJSONObject("sys");
                String country = sys.getString("country");
                //Log.d("SwA", "ID [" + id + "]");
                City c = new City(id, name, null, country);

                cityList.add(c);
            }
        } catch (JSONException json) {
            throw new WeatherLibException(json);
        }

        return cityList;
    }

    @Override
    public WeatherHourForecast getHourForecastWeather(String data) throws WeatherLibException {
        WeatherHourForecast forecast = new WeatherHourForecast();

        try {
            JSONObject jObj = new JSONObject(data);
            // We move to the list tag
            JSONArray wList = jObj.getJSONArray("list");
            for (int i = 0; i < wList.length(); i++) {
                JSONObject jHour = wList.getJSONObject(i);

                HourForecast hourForecast = new HourForecast();
                hourForecast.timestamp = getLong("dt", jHour);
                // Weather info
                JSONObject jMain = jHour.getJSONObject("main");
                hourForecast.weather.temperature.setTemp(getFloat("temp", jMain));
                hourForecast.weather.temperature.setMinTemp(getFloat("temp_min", jMain));
                hourForecast.weather.temperature.setMaxTemp(getFloat("temp_max", jMain));
                hourForecast.weather.currentCondition.setPressure(getFloat("pressure", jMain));
                hourForecast.weather.currentCondition.setPressureSeaLevel(getFloat("sea_level", jMain));
                hourForecast.weather.currentCondition.setPressureGroundLevel(getFloat("grnd_level", jMain));
                hourForecast.weather.currentCondition.setHumidity(getFloat("humidity", jMain));

                // Now the weather codes
                // We get weather info (This is an array)
                JSONArray jArr1 = jHour.getJSONArray("weather");
                // We use only the first value
                JSONObject JSONWeather = jArr1.getJSONObject(0);
                hourForecast.weather.currentCondition.setWeatherId(getInt("id", JSONWeather));

                // Convert internal code
                if (codeProvider != null)
                    hourForecast.weather.currentCondition.setWeatherCode(codeProvider.getWeatherCode(hourForecast.weather.currentCondition.getWeatherId()));

                hourForecast.weather.currentCondition.setDescr(getString("description", JSONWeather));
                hourForecast.weather.currentCondition.setCondition(getString("main", JSONWeather));
                hourForecast.weather.currentCondition.setIcon(getString("icon", JSONWeather));

                // Clouds
                JSONObject cObj = getObject("clouds", jHour);
                hourForecast.weather.clouds.setPerc(getInt("all", cObj));

                // Wind
                JSONObject wObj = getObject("wind", jHour);
                hourForecast.weather.wind.setSpeed(getFloat("speed", wObj));
                hourForecast.weather.wind.setDeg(getFloat("deg", wObj));
                try {
                    hourForecast.weather.wind.setGust(getFloat("gust", wObj));
                } catch (Throwable t) {
                }

                //Log.d("SwA", "Add weather forecast");
                forecast.addForecast(hourForecast);
            }
        } catch (JSONException json) {
            throw new WeatherLibException(json);
        }

        forecast.setUnit(units);
        return forecast;
    }

    @Override
    public void setConfig(WeatherConfig config) {
        this.config = config;
        units = WeatherUtility.createWeatherUnit(config.unitSystem);
    }

    @Override
    public String getQueryCityURL(String cityNamePattern) {
        return SEARCH_URL + cityNamePattern; // + "&cnt=" + config.maxResult;
    }

    @Override
    public String getQueryCurrentWeatherURL(String cityId) {
        return BASE_URL_ID + cityId + "&units=" + (WeatherUtility.isMetric(config.unitSystem) ? "metric" : "imperial") + "&lang=" + config.lang + createAPPID();
    }

    @Override
    public String getQueryForecastWeatherURL(String cityId) {
        return BASE_FORECAST_URL_ID + cityId + "&lang=" + config.lang + "&cnt=" + config.numDays + "&units=" + (WeatherUtility.isMetric(config.unitSystem) ? "metric" : "imperial") + createAPPID();
    }

    @Override
    public String getQueryImageURL(String icon) throws ApiKeyRequiredException {
        return IMG_URL + icon + ".png";
    }

    @Override
    public void setWeatherCodeProvider(IWeatherCodeProvider codeProvider) {
        this.codeProvider = codeProvider;
    }

    @Override
    public String getQueryCityURLByLocation(android.location.Location location) throws ApiKeyRequiredException {
        return SEARCH_URL_GEO + "&lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&cnt=3";
    }

    @Override
    public String getQueryHourForecastWeatherURL(String cityId) throws ApiKeyRequiredException {
        return BASE_HOUR_FORECAST_URL + cityId + "&lang=" + config.lang + "&units=" + (WeatherUtility.isMetric(config.unitSystem) ? "metric" : "imperial") + createAPPID();
    }

    private String createAPPID() {
        if (config.ApiKey == null || config.ApiKey.equals(""))
            return "";

        return "&APPID=" + config.ApiKey;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    private static long getLong(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getLong(tagName);
    }

}
