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
package com.survivingwithandroid.weatherapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherClientDefault;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weatherapp.R;
import com.survivingwithandroid.weatherapp.adapter.WeatherAdapter;

public class ForecastWeatherFragment extends WeatherFragment {

    private SharedPreferences prefs;
    private ListView forecastList;
    private WeatherAdapter weatherAdapter;

    public static ForecastWeatherFragment newInstance() {
        ForecastWeatherFragment fragment = new ForecastWeatherFragment();
        return fragment;
    }

    public ForecastWeatherFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.forecast_fragment, container, false);
       forecastList = (ListView) v.findViewById(R.id.forecastDays);
        return v;

    }

    public void refreshData() {
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    private void refresh() {
        // Update forecast
        WeatherClient client = WeatherClientDefault.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String cityId = prefs.getString("cityid", null);

        client.getForecastWeather(cityId, new WeatherClient.ForecastWeatherEventListener() {
            @Override
            public void onWeatherRetrieved(WeatherForecast forecast) {
                WeatherAdapter adp = new WeatherAdapter(forecast, getActivity());
                forecastList.setAdapter(adp);
            }

            @Override
            public void onWeatherError(WeatherLibException t) {

            }

            @Override
            public void onConnectionError(Throwable t) {
                //WeatherDialog.createErrorDialog("Error parsing data. Please try again", MainActivity.this);
            }
        });
    }
}