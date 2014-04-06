package com.survivingwithandroid.weatherapp.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.survivingwithandroid.weatherapp.R;

/*
 * Copyright (C) 2014 Francesco Azzola - Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class WeatherPreferenceActivity extends PreferenceActivity  {


    @Override
    public void onCreate(Bundle Bundle) {
        super.onCreate(Bundle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String action = getIntent().getAction();

        addPreferencesFromResource(R.xml.weather_prefs);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // We set the current values in the description
        Preference prefLocation = getPreferenceScreen().findPreference("swa_loc");
        final Preference prefTemp = getPreferenceScreen().findPreference("swa_temp_unit");
        final Preference prefLang = getPreferenceScreen().findPreference("swa_lang");


        prefLocation.setSummary(getResources().getText(R.string.summary_loc) + " " + prefs.getString("cityName", "") + "," + prefs.getString("country", ""));
        prefLang.setSummary(getResources().getText(R.string.summary_lang) + " " + prefs.getString("swa_lang", "en"));

        String unit =  prefs.getString("swa_temp_unit", null) != null ? "°" + prefs.getString("swa_temp_unit", null).toUpperCase() : "";
        prefTemp.setSummary(getResources().getText(R.string.summary_temp) + " " + unit);

        prefTemp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                prefTemp.setSummary(getResources().getText(R.string.summary_temp) + " °" +  ((String) newValue).toUpperCase());
                return true;
            }
        });

        prefLang.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                prefLang.setSummary(getResources().getText(R.string.summary_lang) + " " +  ((String) newValue).toUpperCase());
                return true;
            }
        });

    }






}
