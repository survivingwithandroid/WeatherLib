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
package com.survivingwithandroid.weatherapp.settings;

import java.util.ArrayList;
import java.util.List;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault;
import com.survivingwithandroid.weather.lib.exception.LocationProviderNotFoundException;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weatherapp.R;
import com.survivingwithandroid.weatherapp.adapter.CityAdapter;
import com.survivingwithandroid.weather.lib.model.City;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Francesco
 *
 */
public class SearchLocationActivity extends Activity {

	private ListView cityListView;
	private ProgressBar bar;
	private CityAdapter adp;
    private WeatherClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_location_activity);

        client = WeatherClientDefault.getInstance();
        Log.d("App", "Client ["+client+"]");

        cityListView = (ListView) findViewById(R.id.cityList);
		bar = (ProgressBar) findViewById(R.id.progressBar2);
        adp = new CityAdapter(SearchLocationActivity.this, new ArrayList<City>());
        cityListView.setAdapter(adp);

		ImageView searchView = (ImageView) findViewById(R.id.imgSearch);
		final EditText edt = (EditText) findViewById(R.id.cityEdtText);
		
		edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v.getText().toString());
					return true;
				}
				
				return false;
			}
		});
		
		searchView.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				search(edt.getEditableText().toString());
			}
		});
		
		cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SearchLocationActivity.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                City city = (City) parent.getItemAtPosition(pos);
                editor.putString("cityid", city.getId());
                editor.putString("cityName", city.getName());
                editor.putString("country", city.getCountry());
                editor.commit();
                NavUtils.navigateUpFromSameTask(SearchLocationActivity.this);
			}
		});

        ImageView locImg = (ImageView) findViewById(R.id.imgLocationSearch);
        locImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                try {
                    client.searchCityByLocation(WeatherClient.createDefaultCriteria(), new WeatherClient.CityEventListener() {

                        @Override
                        public void onCityListRetrieved(List<City> cityList) {
                            bar.setVisibility(View.GONE);
                            adp.setCityList(cityList);
                            adp.notifyDataSetChanged();
                        }

                        @Override
                        public void onWeatherError(WeatherLibException wle) {
                            bar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onConnectionError(Throwable t) {
                            bar.setVisibility(View.GONE);
                        }
                    });
                }
                catch(LocationProviderNotFoundException lpnfe) {

                }
            }

        });
    }

        private void search(String pattern) {
            bar.setVisibility(View.VISIBLE);
            client.searchCity(pattern, new WeatherClient.CityEventListener() {
                @Override
                public void onCityListRetrieved(List<City> cityList) {
                    bar.setVisibility(View.GONE);
                    adp.setCityList(cityList);
                    adp.notifyDataSetChanged();
                }

                @Override
                public void onWeatherError(WeatherLibException t) {
                    bar.setVisibility(View.GONE);
                }

                @Override
                public void onConnectionError(Throwable t) {
                    bar.setVisibility(View.GONE);
                }
            });
        }

	}
	


