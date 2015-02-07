package com.survivingwithandroid.weather.lib.provider.forecastio;

import android.location.Location;
import android.util.Log;

import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.ApiKeyRequiredException;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.BaseWeather;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.HistoricalWeather;
import com.survivingwithandroid.weather.lib.model.HourForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.model.WeatherHourForecast;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;
import com.survivingwithandroid.weather.lib.provider.IWeatherProvider;
import com.survivingwithandroid.weather.lib.request.Params;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.survivingwithandroid.weather.lib.util.WeatherUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * ${copyright}.
 */

/**
 * This class implements ForecastIO API retrieving weather information from https://developer.forecast.io/docs/v2.
 * One important this to notice is in cityId must be passed lat#lon.
 * This provider doesn't implement a way to look for a city but it requires you use the latitude and logitude,
 * so you have to pass it directly into the cityId.
 * You can use {@link android.location.Geocoder} to get the geographic coordinates from the city name.
 *
 * @author Francesco Azzola
 * */
public class ForecastIOWeatherProvider implements IWeatherProvider {

    private static final String URL = "https://api.forecast.io/forecast/";
    private static final long EXPIRE_TIME = 5 * 60 * 1000; // 5 min
    private WeatherConfig config;
    private CurrentWeather cWeather;
    private WeatherHourForecast whf;
    private WeatherForecast forecast;
    private long lastUpdate;


    private BaseWeather.WeatherUnit units = new BaseWeather.WeatherUnit();

    @Override
    public CurrentWeather getCurrentCondition(String data) throws WeatherLibException {
        if (cWeather != null && !isExpired())
            return cWeather;
        else {
            parseData(data);
            return cWeather;
        }
    }

    @Override
    public WeatherForecast getForecastWeather(String data) throws WeatherLibException {
        if (forecast != null && !isExpired())
            return forecast;
        else {
            parseData(data);
            return forecast;
        }

    }

    @Override
    public List<City> getCityResultList(String data) throws WeatherLibException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WeatherHourForecast getHourForecastWeather(String data) throws WeatherLibException {
        if (whf != null && !isExpired())
            return whf;
        else {
            parseData(data);
            return  whf;
        }
    }


    @Override
    public String getQueryCityURL(String cityNamePattern) throws ApiKeyRequiredException {
        return null;
    }
    /*
        @Override
        public String getQueryCurrentWeatherURL(String cityId) throws ApiKeyRequiredException {
            return createURL(cityId);
        }

        @Override
        public String getQueryForecastWeatherURL(String cityId) throws ApiKeyRequiredException {
            return createURL(cityId);
        }

        @Override
        public String getQueryHourForecastWeatherURL(String cityId) throws ApiKeyRequiredException {
          return createURL(cityId);
        }
*/
        @Override
        public HistoricalWeather getHistoricalWeather(String data) throws WeatherLibException {
            return null;
        }


    @Override
    public String getQueryCityURLByLocation(Location location) throws ApiKeyRequiredException {
        return null;
    }

    @Override
    public String getQueryCityURLByCoord(double lon, double lat) throws ApiKeyRequiredException {
        return null;
    }

    @Override
    public void setConfig(WeatherConfig config) {
        this.config = config;
        units = WeatherUtility.createWeatherUnit(config.unitSystem);
    }

    @Override
    public void setWeatherCodeProvider(IWeatherCodeProvider codeProvider) {

    }

    @Override
    public String getQueryImageURL(String weatherId) throws ApiKeyRequiredException {
        return null;
    }


    @Override
    public String getQueryHistoricalWeatherURL(WeatherRequest request, Date startDate, Date endDate) throws ApiKeyRequiredException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getQueryLayerURL(String cityId, Params params) throws ApiKeyRequiredException {
        return null;
    }

    private String createURL(WeatherRequest request)throws ApiKeyRequiredException {
        if (config.ApiKey == null || config.ApiKey.equals(""))
            throw new ApiKeyRequiredException();

        //StringTokenizer st = new StringTokenizer(cityId, "#");

        return URL + config.ApiKey + "/" + request.getLat() + "," + request.getLon() + "?units=" + (WeatherUtility.isMetric(config.unitSystem) ? "ca" : "us") ;
    }


    private void parseData(String data)  throws WeatherLibException {
        lastUpdate = System.currentTimeMillis();

        cWeather = new CurrentWeather();
        Weather weather = new Weather();
        try {
            // We create out JSONObject from the data
            JSONObject rootObj = new JSONObject(data);

            // Parse city
            com.survivingwithandroid.weather.lib.model.Location loc = new com.survivingwithandroid.weather.lib.model.Location();
            loc.setLatitude((float)  rootObj.getDouble("latitude"));
            loc.setLongitude((float) rootObj.getDouble("longitude"));

            weather.location = loc;

            // Parse current weather
            JSONObject currently = rootObj.getJSONObject("currently");
            Log.d("FIOW", currently.toString() + "");

            loc.setSunrise(currently.optLong("sunriseTime"));
            loc.setSunset(currently.optLong("sunsetTime"));
            weather = parseWeather(currently);
            cWeather.weather = weather;
            cWeather.setUnit(units);

            // Hourly Weather
            JSONObject hourly = rootObj.getJSONObject("hourly");

            whf = new WeatherHourForecast();
            JSONArray jsonData = hourly.getJSONArray("data");
            for (int i=0; i < jsonData.length(); i++) {
                JSONObject jsonHour = jsonData.getJSONObject(i);
                Weather hWeather = parseWeather(jsonHour);
                HourForecast hourForecast = new HourForecast();
                hourForecast.timestamp = jsonHour.optLong("time");
                hourForecast.weather = hWeather;

                whf.addForecast(hourForecast);
            }

            whf.setUnit(units);

            // Day forecast
            JSONObject daily = rootObj.getJSONObject("daily");

            forecast = new WeatherForecast();

            JSONArray jsonDailyData = daily.getJSONArray("data");

            for (int i=0; i < jsonDailyData.length(); i++) {
                JSONObject jsonDay = jsonDailyData.getJSONObject(i);
                Weather hWeather = parseWeather(jsonDay);
                DayForecast dayForecast = new DayForecast();
                dayForecast.timestamp = jsonDay.optLong("time");
                dayForecast.weather = hWeather;

                forecast.addForecast(dayForecast);
            }

            forecast.setUnit(units);
        }
        catch (JSONException json) {
            //json.printStackTrace();
            throw new WeatherLibException(json);
        }


        //cWeather.setUnit(units);
        cWeather.weather = weather;
    }

    private Weather parseWeather(JSONObject jsonWeather) throws JSONException {
        Weather weather = new Weather();

        weather.currentCondition.setDescr(jsonWeather.optString("summary"));
        weather.currentCondition.setIcon(jsonWeather.optString("icon"));



        weather.rain[0].setAmmount((float) jsonWeather.optDouble("precipIntensity"));

        weather.rain[0].setChance((float) jsonWeather.optDouble("precipProbability") * 100);

        weather.temperature.setTemp((float) jsonWeather.optDouble("temperature"));
        weather.temperature.setMinTemp((float) jsonWeather.optDouble("temperatureMin"));
        weather.temperature.setMaxTemp((float) jsonWeather.optDouble("temperatureMax"));
        weather.currentCondition.setDewPoint((float) jsonWeather.optDouble("dewPoint"));

        weather.wind.setSpeed((float) jsonWeather.optDouble("windSpeed"));
        weather.wind.setDeg((float) jsonWeather.optDouble("windBearing"));

        weather.clouds.setPerc((int) jsonWeather.optDouble("cloudCover") * 100); // We transform it in percentage
        weather.currentCondition.setHumidity((float) jsonWeather.optDouble("humidity") * 100);
        weather.currentCondition.setVisibility((float) jsonWeather.optDouble("visibility"));
        weather.currentCondition.setPressure((float) jsonWeather.optDouble("pressure"));

        return weather;
    }

    private boolean isExpired() {
        if (lastUpdate == 0)
            return true; // First time;

        if (lastUpdate - System.currentTimeMillis() > EXPIRE_TIME)
            return true;

        return false;
    }

    // New methods

    @Override
    public String getQueryCurrentWeatherURL(WeatherRequest request) throws ApiKeyRequiredException {
        return createURL(request);
    }

    @Override
    public String getQueryForecastWeatherURL(WeatherRequest request) throws ApiKeyRequiredException {
        return createURL(request);
    }

    @Override
    public String getQueryHourForecastWeatherURL(WeatherRequest request) throws ApiKeyRequiredException {
        return null;
    }


}
