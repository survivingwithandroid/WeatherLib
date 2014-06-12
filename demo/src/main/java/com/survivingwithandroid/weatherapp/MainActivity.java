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



import java.util.ArrayList;
import java.util.List;

import com.survivingwithandroid.weather.lib.WeatherConfig;

import com.survivingwithandroid.weatherapp.fragment.CurrentWeatherFragment;
import com.survivingwithandroid.weatherapp.fragment.ForecastWeatherFragment;
import com.survivingwithandroid.weatherapp.fragment.WeatherFragment;
import com.survivingwithandroid.weatherapp.settings.WeatherPreferenceActivity;

import com.survivingwithandroid.weather.lib.WeatherClient;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


public class MainActivity extends Activity implements ActionBar.TabListener, WeatherFragment.WeatherEventListener {

	private boolean isThereForecast = false;

	private WeatherConfig config;
    private WeatherClient client;
    private List<Fragment> activeFragment = new ArrayList<Fragment>();
    private int currentPos;

    private MenuItem refreshItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);

        setContentView(R.layout.main_activity);

        // Let's verify if we are using a smartphone or a tablet
        View v = findViewById(R.id.currentWeatherFrag);

        if (v != null)
            isThereForecast = true;


        /*
        // Prior to 1.5.0
        client = WeatherClientDefault.getInstance();
        client.init(getApplicationContext());
        Log.d("App", "Client ["+client+"]");
        // Let's create the WeatherProvider
        config = new WeatherConfig();
        config.unitSystem = WeatherConfig.UNIT_SYSTEM.M;


        IWeatherProvider provider = null;
        try {
            //provider = WeatherProviderFactory.createProvider(new YahooProviderType(), config);
             provider = WeatherProviderFactory.createProvider(new OpenweathermapProviderType(), config);
            // provider = WeatherProviderFactory.createProvider(new WeatherUndergroundProviderType(), config);
             client.setProvider(provider);
        }
        catch (Throwable t) {
            t.printStackTrace();
            // There's a problem
        }
        */


        setProgressBarIndeterminate(true);
        setProgressBarVisibility(true);
        if (!isThereForecast) {
            ActionBar bar = getActionBar();
            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            //ActionBar.Tab tab = bar.newTab();
            createTab(R.string.tab_current, bar);
            createTab(R.string.tab_forecast, bar);
        }
        else {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setTransitionStyle(R.style.fragmentAnim);
            CurrentWeatherFragment cf = CurrentWeatherFragment.newInstance();
            ft.add(R.id.currentWeatherFrag, cf, "currentWeather") ;
            ft.commit();
            FragmentTransaction ft1 = getFragmentManager().beginTransaction();
            ft1.setTransitionStyle(R.style.fragmentAnim);
            ForecastWeatherFragment ff = ForecastWeatherFragment.newInstance();
            ft1.add(R.id.forecastWeatherFrag, ff, "forecastWeather");
            ft1.commit();
        }


	}

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
            Intent i = new Intent();
            i.setClass(this, WeatherPreferenceActivity.class);
            startActivity(i);
        }
		else if (id == R.id.action_refresh) {
            refreshItem = item;
            FragmentManager fm = getFragmentManager();

            if (isThereForecast) {

                CurrentWeatherFragment frag  = (CurrentWeatherFragment) fm.findFragmentById(R.id.currentWeatherFrag);
                if (frag instanceof CurrentWeatherFragment) {
                    showProgressBar(true);
                    ((CurrentWeatherFragment) frag).refreshData();
                } else {
                    ForecastWeatherFragment frag1 = (ForecastWeatherFragment) fm.findFragmentById(R.id.forecastWeatherFrag);
                    if (frag1 instanceof ForecastWeatherFragment)
                        ((ForecastWeatherFragment) frag1).refreshData();
                }
            } else {
                WeatherFragment f = (WeatherFragment) activeFragment.get(currentPos);
                if (f != null)
                    f.refreshData();
            }
        }
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

    private void createTab(int labelId, ActionBar bar) {
        ActionBar.Tab tab = bar.newTab();
        tab.setText(getResources().getString(labelId));
        tab.setTabListener(this);
        bar.addTab(tab);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        int pos = tab.getPosition();
        currentPos = pos;

        Fragment f = null;
        fragmentTransaction.setTransitionStyle(R.style.fragmentAnim);

        if (activeFragment.size() > pos)
          f = activeFragment.get(pos);

        if (f == null) {
            if (pos == 0) {
                // Current weather
                CurrentWeatherFragment cwf = CurrentWeatherFragment.newInstance();
                fragmentTransaction.add(android.R.id.content, cwf);
                activeFragment.add(cwf);
            }
            else if (pos == 1) {
                ForecastWeatherFragment fwf = ForecastWeatherFragment.newInstance();
                fragmentTransaction.add(android.R.id.content, fwf);
                activeFragment.add(fwf);
            }
        }
        else {
            fragmentTransaction.add(android.R.id.content, f);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        Log.d("SwaA", "Tab unselected");
        int pos = tab.getPosition();
        Fragment f = activeFragment.get(pos);
        fragmentTransaction.remove(f);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        Log.d("Swa", "Tab reselected");
    }

    @Override
    public void requestCompleted() {
        showProgressBar(false);
    }

    private void showProgressBar(boolean showIt) {
      Log.d("SwA", "ShowProgress ["+showIt+"]");
        setProgressBarVisibility(showIt);

      if (refreshItem == null)
          return ;

        if (showIt)
            refreshItem.setActionView(R.layout.progress_bar);
        else
            refreshItem.setActionView(null);
    }
}
