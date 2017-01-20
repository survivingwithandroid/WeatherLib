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

package com.survivingwithandroid.weather.lib.provider.wunderground;

import com.survivingwithandroid.weather.lib.WeatherCode;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.HistoricalHourWeather;
import com.survivingwithandroid.weather.lib.model.HistoricalWeather;
import com.survivingwithandroid.weather.lib.model.HourForecast;
import com.survivingwithandroid.weather.lib.model.Location;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.model.WeatherHourForecast;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;
import com.survivingwithandroid.weather.lib.provider.IWeatherProvider;
import com.survivingwithandroid.weather.lib.request.Params;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.survivingwithandroid.weather.lib.util.WeatherUtility;
import com.survivingwithandroid.weather.lib.model.BaseWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


/**
 * This is a concrete implementaton of IWeatherProvider interface for Weather undergound provider
 *
 * @author Francesco Azzola
 * */

public class WeatherUndergroundProvider implements IWeatherProvider {

    private static String BASE_URL_ID = "http://api.wunderground.com/api";
    private static String IMG_URL = "http://icons.wxug.com/i/c/k/";
    private static String SEARCH_URL = "http://autocomplete.wunderground.com/aq?query=";

    private WeatherConfig config;
    private BaseWeather.WeatherUnit units;
    private Units unitStrings;
    private IWeatherCodeProvider codeProvider;

    public CurrentWeather getCurrentCondition(String data) throws WeatherLibException {
        try {
            final CurrentWeather currentWeather = new CurrentWeather();
            currentWeather.setUnit(units);

            final Weather weather = new Weather();
            currentWeather.weather = weather;

            final JSONObject rootObj = new JSONObject(data);
            final JSONObject jObj = rootObj.getJSONObject("current_observation");
            final JSONObject dObj = jObj.getJSONObject("display_location");

            // Location
            final Location loc = new Location();
            loc.setLatitude(WeatherUtility.getDouble(dObj, "latitude"));
            loc.setLongitude(WeatherUtility.getDouble(dObj, "longitude"));
            loc.setCountry(WeatherUtility.getString(dObj, "state_name"));
            loc.setCity(WeatherUtility.getString(dObj, "city"));
            weather.location = loc;

            weather.currentCondition.setDescr(WeatherUtility.getString(jObj, "weather"));
            weather.currentCondition.setIcon(WeatherUtility.getString(jObj, "icon"));

            // WeatherCode: Convert to internal code
            if (codeProvider != null) {
                try {
                    weather.currentCondition.setWeatherCode(codeProvider.getWeatherCode(weather.currentCondition.getIcon()));

                } catch (Throwable t) {
                    weather.currentCondition.setWeatherCode(WeatherCode.NOT_AVAILABLE);
                }
            }

            // Humidity
            final String relUm = WeatherUtility.getString(jObj, "relative_humidity");
            if(relUm != null) {
                try {
                    weather.currentCondition.setHumidity(Double.parseDouble(relUm.substring(0, relUm.length() - 1)));
                } catch (NumberFormatException e) {
                    //
                }
            }

            // At this point the pressure trend was set, but it was 0 all the time
            // This is because wunderground can return 3 values: + - and 0, so mapping it to int makes no sense

            weather.currentCondition.setUV(WeatherUtility.getDouble(jObj, "UV"));
            weather.currentCondition.setSolarRadiation(WeatherUtility.getString(jObj, "solarradiation"));
            weather.currentCondition.setPressure(WeatherUtility.getDouble(jObj, "pressure_" + unitStrings.pressure));
            weather.temperature.setTemp(WeatherUtility.getDouble(jObj, "temp_" + unitStrings.temperatureShort));
            weather.wind.setGust(WeatherUtility.getDouble(jObj, "wind_gust_" + unitStrings.speed));
            weather.wind.setSpeed(WeatherUtility.getDouble(jObj, "wind_" + unitStrings.speed));
            weather.wind.setDeg(WeatherUtility.getDouble(jObj, "wind_degrees"));
            weather.currentCondition.setVisibility(WeatherUtility.getDouble(jObj, "visibility_" + unitStrings.distance));
            weather.currentCondition.setFeelsLike(WeatherUtility.getDouble(jObj, "feelslike_" + unitStrings.temperatureShort));
            weather.currentCondition.setDewPoint(WeatherUtility.getDouble(jObj, "dewpoint_" + unitStrings.temperatureShort));
            weather.currentCondition.setHeatIndex(WeatherUtility.getString(jObj, "heat_index_" + unitStrings.temperatureShort));


            // Forecast is parsed for today's min/max temp
            parseForecast(rootObj, weather);

            // Astronomy
            if(rootObj.has("moon_phase")) {
                final JSONObject moonObj = rootObj.getJSONObject("moon_phase");
                weather.location.getAstronomy().percIllum = WeatherUtility.getString(moonObj, "percentIlluminated");
                weather.location.getAstronomy().moonAge = WeatherUtility.getString(moonObj, "ageOfMoon");
                weather.location.getAstronomy().moonPhaseDescr = WeatherUtility.getString(moonObj, "phaseofMoon");
                weather.location.getAstronomy().hemisphere = WeatherUtility.getString(moonObj, "hemisphere");

                final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

                if(moonObj.has("sunrise")) {
                    final JSONObject riseObj = moonObj.getJSONObject("sunrise");
                    final String d1 = WeatherUtility.getString(riseObj, "hour") + ":" + WeatherUtility.getString(riseObj, "minute");
                    try {
                        weather.location.setSunrise(sdf.parse(d1).getTime());
                    } catch (ParseException e) {
                        //
                    }
                }

                if(moonObj.has("sunset")) {
                    final JSONObject setObj = moonObj.getJSONObject("sunset");
                    final String d2 = WeatherUtility.getString(setObj, "hour") + ":" + WeatherUtility.getString(setObj, "minute");
                    try {
                        weather.location.setSunset(sdf.parse(d2).getTime());
                    } catch (ParseException e) {
                        //
                    }
                }
            }

            return currentWeather;
        } catch (JSONException json) {
            throw new WeatherLibException(json);
        }
    }

    public WeatherForecast getForecastWeather(String data) throws WeatherLibException {
        try {
            final JSONObject rootObj = new JSONObject(data);

            return parseForecast(rootObj, null);
        } catch (JSONException json) {
            throw new WeatherLibException(json);
        }
    }

    private WeatherForecast parseForecast(JSONObject root, Weather weather) throws JSONException {
        final JSONObject jsonForecast = root.getJSONObject("forecast");
        final JSONObject simpleForecast = jsonForecast.getJSONObject("simpleforecast");
        final JSONArray jArr = simpleForecast.getJSONArray("forecastday");

        final WeatherForecast forecast = new WeatherForecast();
        forecast.setUnit(units);

        for (int i = 0; i < jArr.length(); i++) {
            final DayForecast df = new DayForecast();

            final JSONObject dayForecast = jArr.getJSONObject(i);

            if(dayForecast.has("date")) {
                final JSONObject jsonDate = dayForecast.getJSONObject("date");
                df.timestamp = WeatherUtility.getLong(jsonDate, "epoch");
            }

            df.weather.currentCondition.setDescr(WeatherUtility.getString(dayForecast, "conditions"));
            df.weather.currentCondition.setIcon(WeatherUtility.getString(dayForecast, "icon"));
            df.weather.currentCondition.setHumidity(WeatherUtility.getDouble(dayForecast, "avehumidity"));

            if (codeProvider != null) {
                try {
                    df.weather.currentCondition.setWeatherCode(codeProvider.getWeatherCode(df.weather.currentCondition.getIcon()));
                } catch (Throwable t) {
                    df.weather.currentCondition.setWeatherCode(WeatherCode.NOT_AVAILABLE);
                }
            }

            df.forecastTemp.max = WeatherUtility.getDouble(dayForecast, "high", unitStrings.temperature);
            df.forecastTemp.min = WeatherUtility.getDouble(dayForecast, "low", unitStrings.temperature);
            df.weather.wind.setSpeed(WeatherUtility.getDouble(dayForecast, "avewind", unitStrings.speed));
            df.weather.wind.setDeg(WeatherUtility.getDouble(dayForecast, "avewind", "degrees"));
            df.weather.snow.setTime("Day");
            df.weather.snow.setAmmount(WeatherUtility.getDouble(dayForecast, "snow_allday", unitStrings.snowHeight));
            df.weather.rain[0].setTime("Day");
            df.weather.rain[0].setAmmount(WeatherUtility.getDouble(dayForecast, "qpf_allday", unitStrings.waterHeight));

            if (i == 0 && weather != null) {
                weather.temperature.setMinTemp(df.forecastTemp.min);
                weather.temperature.setMaxTemp(df.forecastTemp.max);
            }

            forecast.addForecast(df);
        }

        return forecast;
    }

    public List<City> getCityResultList(String data) throws WeatherLibException {
        try {
            final List<City> cityList = new ArrayList<City>();

            final JSONObject jObj = new JSONObject(data);
            final JSONArray jArr = jObj.getJSONArray("RESULTS");

            for (int i = 0; i < jArr.length(); i++) {
                final JSONObject obj = jArr.getJSONObject(i);

                final City c = new City.CityBuilder()
                        .name(obj.getString("name"))
                        .id(obj.getString("l"))
                        .country(obj.getString("c"))
                        .geoCoord(obj.getDouble("lat"), obj.getDouble("lon"))
                        .build();

                cityList.add(c);
            }

            return cityList;
        } catch (JSONException json) {
            throw new WeatherLibException(json);
        }
    }

    @Override
    public WeatherHourForecast getHourForecastWeather(String data) throws WeatherLibException {
        try {
            final WeatherHourForecast forecast = new WeatherHourForecast();
            forecast.setUnit(units);

            final JSONObject jObj = new JSONObject(data);
            final JSONArray jHoursArray = jObj.getJSONArray("hourly_forecast");

            for (int i = 0; i < jHoursArray.length(); i++) {
                final HourForecast hourForecast = new HourForecast();

                final JSONObject jHour = jHoursArray.getJSONObject(i);
                final String unitTag = WeatherUtility.isMetric(config.unitSystem) ? "metric" : "english";

                hourForecast.timestamp = jHour.getJSONObject("FCTTIME").getLong("epoch");
                hourForecast.weather.currentCondition.setDescr(WeatherUtility.getString(jHour, "conditions"));
                hourForecast.weather.currentCondition.setIcon(WeatherUtility.getString(jHour, "icon"));
                hourForecast.weather.currentCondition.setHumidity(WeatherUtility.getDouble(jHour, "humidity"));
                hourForecast.weather.currentCondition.setUV(WeatherUtility.getDouble(jHour, "uvi"));
                hourForecast.weather.wind.setDeg(WeatherUtility.getDouble(jHour, "wdir", "degrees"));
                hourForecast.weather.temperature.setTemp(WeatherUtility.getDouble(jHour, "temp", unitTag));
                hourForecast.weather.currentCondition.setDewPoint(WeatherUtility.getDouble(jHour, "dewpoint", unitTag));
                hourForecast.weather.wind.setSpeed(WeatherUtility.getDouble(jHour, "wspd", unitTag));
                hourForecast.weather.currentCondition.setFeelsLike(WeatherUtility.getDouble(jHour, "feelslike", unitTag));
                hourForecast.weather.currentCondition.setHeatIndex(WeatherUtility.getString(jHour, "heatindex", unitTag));
                hourForecast.weather.rain[0].setAmmount(WeatherUtility.getDouble(jHour, "qpf", unitTag));
                hourForecast.weather.snow.setAmmount(WeatherUtility.getDouble(jHour, "snow", unitTag));

                forecast.addForecast(hourForecast);
            }

            return forecast;
        } catch (JSONException json) {
            throw new WeatherLibException(json);
        }
    }

    @Override
    public HistoricalWeather getHistoricalWeather(String data) throws WeatherLibException {
        try {
            final HistoricalWeather histWeather = new HistoricalWeather();
            histWeather.setUnit(units);

            final JSONObject jObj = new JSONObject(data);
            final JSONObject histObj = jObj.getJSONObject("history");
            final JSONArray wList = histObj.getJSONArray("observations");

            for (int i = 0; i < wList.length(); i++) {
                final HistoricalHourWeather hhWeather = new HistoricalHourWeather();
                final JSONObject jHour = wList.getJSONObject(i);

                try {
                    final JSONObject utcObj = jHour.getJSONObject("utcdate");
                    final int y = utcObj.getInt("year");
                    final int m = utcObj.getInt("mon");
                    final int mday = utcObj.getInt("mday");
                    final int h = utcObj.getInt("hour");
                    final int min = utcObj.getInt("min");

                    final Calendar cal = GregorianCalendar.getInstance();
                    cal.set(y, Calendar.JANUARY, mday, h, min);
                    cal.add(Calendar.MONTH, m - 1);

                    hhWeather.timestamp = cal.getTimeInMillis();
                } catch (JSONException e) {
                    //
                }

                final String tag = WeatherUtility.isMetric(config.unitSystem) ? "m" : "i";

                hhWeather.weather.temperature.setTemp(WeatherUtility.getDouble(jHour, "temp" + tag));
                hhWeather.weather.currentCondition.setDewPoint(WeatherUtility.getDouble(jHour, "dewpt" + tag));
                hhWeather.weather.currentCondition.setHumidity(WeatherUtility.getDouble(jHour, "hum"));
                hhWeather.weather.wind.setSpeed(WeatherUtility.getDouble(jHour, "wspd" + tag));
                hhWeather.weather.wind.setGust(WeatherUtility.getDouble(jHour, "wgust" + tag));
                hhWeather.weather.wind.setDeg(WeatherUtility.getDouble(jHour, "wdird"));
                hhWeather.weather.wind.setChill(WeatherUtility.getDouble(jHour, "windchill" + tag));
                hhWeather.weather.currentCondition.setVisibility(WeatherUtility.getDouble(jHour, "vis" + tag));
                hhWeather.weather.currentCondition.setPressure(WeatherUtility.getDouble(jHour, "pressure" + tag));
                hhWeather.weather.currentCondition.setHeatIndex(WeatherUtility.getString(jHour, "heatindex" + tag));
                hhWeather.weather.rain[0].setAmmount(WeatherUtility.getDouble(jHour, "precip" + tag));
                hhWeather.weather.currentCondition.setDescr(WeatherUtility.getString(jHour, "conds"));
                hhWeather.weather.currentCondition.setIcon(WeatherUtility.getString(jHour, "icon"));

                if (codeProvider != null) {
                    try {
                        hhWeather.weather.currentCondition.setWeatherCode(codeProvider.getWeatherCode(hhWeather.weather.currentCondition.getIcon()));
                    } catch (Throwable t) {
                        hhWeather.weather.currentCondition.setWeatherCode(WeatherCode.NOT_AVAILABLE);
                    }
                }
                // fog, hail, tornado and so on still not supported

                histWeather.addHistoricalHourWeather(hhWeather);
            }

            return histWeather;
        } catch (JSONException json) {
            throw new WeatherLibException(json);
        }
    }

    @Override
    public void setConfig(WeatherConfig config) {
        this.config = config;
        units = WeatherUtility.createWeatherUnit(config.unitSystem);

        unitStrings = WeatherUtility.isMetric(config.unitSystem) ? new MetricUnits() : new ImperialUnits();
    }

    @Override
    public String getQueryCityURL(String cityNamePattern) {

        if (config.ApiKey == null)
            throw new ApiKeyRequiredException();

        cityNamePattern = cityNamePattern.replaceAll(" ", "%20");
        return SEARCH_URL + cityNamePattern; // + "&cnt=" + config.maxResult;
    }

    @Override
    public String getQueryImageURL(String icon) throws ApiKeyRequiredException {
        return IMG_URL + icon + ".gif";
    }

    @Override
    public void setWeatherCodeProvider(IWeatherCodeProvider codeProvider) {
        this.codeProvider = codeProvider;
    }

    @Override
    public String getQueryCityURLByLocation(android.location.Location location) throws ApiKeyRequiredException {
        return getQueryCityURLByCoord(location.getLatitude(), location.getLongitude());
    }

    @Override
    public String getQueryCityURLByCoord(double lat, double lon) throws ApiKeyRequiredException {
        if (config.ApiKey == null)
            throw new ApiKeyRequiredException();

        return BASE_URL_ID + "/" + config.ApiKey + "/geolookup/q/" + lat + "," + lon + ".json";
    }

    @Override
    public String getQueryLayerURL(String cityId, Params params) throws ApiKeyRequiredException {
        if (config.ApiKey == null)
            throw new ApiKeyRequiredException();

        String url = BASE_URL_ID + "/" + config.ApiKey + "/" + params.getImageType() + "/" +
                ( (cityId == null  || cityId.equals("")) ? "image.png" : cityId + ".png")  +
                "?" + params.string();

       return url;
    }

    private String buildURL(final WeatherRequest request, final String type) throws ApiKeyRequiredException{
        if (config.ApiKey == null) {
            throw new ApiKeyRequiredException();
        }

        String location = request.getCityId();
        if (location == null) {
            location = request.getLat() + "," + request.getLon();
        }

        String language = "EN";
        if (config.lang != null) {
            language = config.lang.toUpperCase();
        }

        final String url = String.format("%s/%s/%s/lang:%s/q/%s.json", BASE_URL_ID, config.ApiKey, type, language, location);

        return url;
    }

    // New methods

    @Override
    public String getQueryCurrentWeatherURL(WeatherRequest request) throws ApiKeyRequiredException {
        return buildURL(request, "conditions/forecast/astronomy");
    }

    @Override
    public String getQueryForecastWeatherURL(WeatherRequest request) throws ApiKeyRequiredException {
        return buildURL(request, "forecast");
    }

    @Override
    public String getQueryHourForecastWeatherURL(WeatherRequest request) throws ApiKeyRequiredException {
        return buildURL(request, "hourly");
    }

    @Override
    public String getQueryHistoricalWeatherURL(WeatherRequest request, Date startDate, Date endDate) throws ApiKeyRequiredException {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

        return buildURL(request, "history_" + sdf.format(startDate));
    }

    private class MetricUnits extends Units{
        public MetricUnits(){
            super("celsius", "c", "km", "kph", "cm", "mm", "mb");
        }
    }

    private class ImperialUnits extends Units{
        public ImperialUnits(){
            super("fahrenheit", "f", "mi", "mph", "in", "in", "in");
        }
    }

    private class Units{
        public final String temperature;
        public final String temperatureShort;
        public final String distance;
        public final String speed;
        public final String snowHeight;
        public final String waterHeight;
        public final String pressure;

        public Units(String temperature, String temperatureShort, String distance, String speed, String snowHeight, String waterHeight, String pressure){
            this.temperature = temperature;
            this.temperatureShort = temperatureShort;
            this.distance = distance;
            this.speed = speed;
            this.snowHeight = snowHeight;
            this.waterHeight = waterHeight;
            this.pressure = pressure;
        }
    }
}
