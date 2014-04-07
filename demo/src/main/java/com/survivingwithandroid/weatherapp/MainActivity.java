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

package com.survivingwithandroid.weatherapp;



import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.survivingwithandroid.weather.lib.WeatherClientDefault;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.provider.IWeatherProvider;
import com.survivingwithandroid.weather.lib.provider.WeatherProviderFactory;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.provider.yahooweather.YahooProviderType;
import com.survivingwithandroid.weatherapp.adapter.WeatherAdapter;

import com.survivingwithandroid.weatherapp.settings.WeatherPreferenceActivity;
import com.survivingwithandroid.weatherapp.util.LogUtils;
import com.survivingwithandroid.weatherapp.util.WeatherIconMapper;

import com.survivingwithandroid.weatherapp.util.WeatherUtil;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.util.WindDirection;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {

    private SharedPreferences prefs;

	private ViewGroup root;
	private TextView cityText;
	private TextView condDescr;
	private TextView temp;
	private TextView press;
	private TextView windSpeed;
	private TextView windDeg;
	private TextView unitTemp;
	private TextView hum;
	private ImageView imgView;
	private TextView tempMin;
	private TextView tempMax;
    private TextView sunset;
    private TextView sunrise;
	private TextView cloud;

    // Forecast
    ListView forecastList;
	// Forecast
	private ListView forecastListView;

	private boolean isFinished = false;

	private WeatherConfig config;
    private WeatherClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);
        client = WeatherClientDefault.getInstance();
        client.init(getApplicationContext());
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Log.d("App", "Client ["+client+"]");
        // Let's create the WeatherProvider
        config = new WeatherConfig();
        IWeatherProvider provider = null;
        try {
         //   provider = WeatherProviderFactory.createProvider(WeatherProviderFactory.WeatherProviderType.Openweathermap, config);

            provider = WeatherProviderFactory.createProvider(new OpenweathermapProviderType(), config);
            client.setProvider(provider);
        }
        catch (Throwable t) {
            t.printStackTrace();
            // There's a problem
        }


        cityText = (TextView) findViewById(R.id.location);
		temp = (TextView) findViewById(R.id.temp);
		condDescr = (TextView) findViewById(R.id.descrWeather);
		imgView = (ImageView) findViewById(R.id.imgWeather);
		hum = (TextView) findViewById(R.id.humidity);
		press = (TextView) findViewById(R.id.pressure);
		windSpeed = (TextView) findViewById(R.id.windSpeed);
		windDeg = (TextView) findViewById(R.id.windDeg);
		tempMin = (TextView) findViewById(R.id.tempMin);
		tempMax = (TextView) findViewById(R.id.tempMax);
        unitTemp = (TextView) findViewById(R.id.tempUnit);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);
        cloud = (TextView) findViewById(R.id.cloud);

        forecastList = (ListView) findViewById(R.id.forecastDays);
	}

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	public void refresh() {

       // SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String cityId = prefs.getString("cityid", null);
        Log.d("Swa", "City Id ["+cityId+"]");
        if (cityId == null)
            return ;

        config.lang = WeatherUtil.getLanguage(prefs.getString("swa_lang", "en"));
        config.maxResult = 5;
        config.numDays = 3;

        String unit = prefs.getString("swa_temp_unit", "c");
        if (unit.equals("c"))
            config.unitSystem = WeatherConfig.UNIT_SYSTEM.M;
        else
            config.unitSystem = WeatherConfig.UNIT_SYSTEM.I;

        client.updateWeatherConfig(config);
        displayProgress(true);
       // setProgressBarIndeterminateVisibility(true);
        client.getCurrentCondition(cityId, new WeatherClient.WeatherEventListener() {
            @Override
            public void onWeatherRetrieved(CurrentWeather weather) {
                //Log.d("SwA", "onWeather");
                //Log.d("SwA", "City ["+weather.location.getCity()+"]");
                cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
                condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
                LogUtils.LOGD("SwA", "Temp [" + temp + "]");
                LogUtils.LOGD("SwA", "Val [" + weather.temperature.getTemp() + "]");
                temp.setText("" + ((int) weather.temperature.getTemp()));
                unitTemp.setText(weather.getUnit().tempUnit);
                ((TextView) findViewById(R.id.lineTxt)).setBackgroundResource(WeatherUtil.getResource(weather.temperature.getTemp(), config));
                hum.setText(weather.currentCondition.getHumidity() + "%");
                tempMin.setText(weather.temperature.getMinTemp() + weather.getUnit().tempUnit);
                tempMax.setText(weather.temperature.getMaxTemp() + weather.getUnit().tempUnit);
                windSpeed.setText(weather.wind.getSpeed() + weather.getUnit().speedUnit);
                windDeg.setText((int) weather.wind.getDeg() + "° (" + WindDirection.getDir((int) weather.wind.getDeg()) + ")");
                press.setText(weather.currentCondition.getPressure() + weather.getUnit().pressureUnit);

                sunrise.setText(convertDate(weather.location.getSunrise()));

                sunset.setText(convertDate(weather.location.getSunset()));

                imgView.setImageResource(WeatherIconMapper.getWeatherResource(weather.currentCondition.getIcon(), weather.currentCondition.getWeatherId()));

                cloud.setText(weather.clouds.getPerc() + "%");
                LogUtils.LOGD("SwA", "UI updated");

            }

            @Override
            public void onWeatherError(WeatherLibException t) {
                //WeatherDialog.createErrorDialog("Error parsing data. Please try again", MainActivity.this);
            }

            @Override
            public void onConnectionError(Throwable t) {
                //WeatherDialog.createErrorDialog("Error parsing data. Please try again", MainActivity.this);
            }
        });

        // Upadte forecast
         client.getForecastWeather(cityId, new WeatherClient.ForecastWeatherEventListener() {
            @Override
            public void onWeatherRetrieved(WeatherForecast forecast) {
                WeatherAdapter adp = new WeatherAdapter(forecast, MainActivity.this);
                forecastList.setAdapter(adp);
                displayProgress(false);
            }

            @Override
            public void onWeatherError(WeatherLibException t) {

            }

             @Override
            public void onConnectionError(Throwable t) {
            //WeatherDialog.createErrorDialog("Error parsing data. Please try again", MainActivity.this);
            }
        });

		/*
		TextView tv = (TextView) findViewById(R.id.timeUpd);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");		
//		tv.setText(sdf.format(new Date()));
*/
	}

    private String convertDate(long unixTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(unixTime * 1000);
        sdf.setTimeZone(cal.getTimeZone());
        return sdf.format(cal.getTime());
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
            Intent i = new Intent();
            i.setClass(this, WeatherPreferenceActivity.class);
            startActivity(i);
        }
		else if (id == R.id.action_refresh)
            refresh();
		else if (id == R.id.action_share) {
			String playStoreLink = "https://play.google.com/store/apps/details?id=" +
			        getPackageName();
			
			String msg = "There's a new weather app in the play store. Look here " + playStoreLink;
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
		}
		return super.onOptionsItemSelected(item);
	}



    private void displayProgress(boolean visible) {
        setProgressBarIndeterminateVisibility(visible);
    }


}