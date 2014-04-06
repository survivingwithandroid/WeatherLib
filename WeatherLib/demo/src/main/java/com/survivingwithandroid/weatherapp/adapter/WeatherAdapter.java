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

package com.survivingwithandroid.weatherapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weatherapp.R;
import com.survivingwithandroid.weatherapp.util.WeatherIconMapper;
import com.survivingwithandroid.weather.lib.model.DayForecast;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherAdapter extends ArrayAdapter<DayForecast>{

	private List<DayForecast> dayForecastList;
	private Context ctx;
	private final static SimpleDateFormat sdf = new SimpleDateFormat("E");
	private Weather.WeatherUnit units;

	public WeatherAdapter(WeatherForecast forecast, Context ctx) {
		super(ctx, R.layout.row_forecast_layout);
		this.dayForecastList = forecast.getForecast();
        units = forecast.getUnit();
		this.ctx = ctx;
	}
	
	

	@Override
	public int getCount() {
		return dayForecastList.size();
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_forecast_layout, parent, false);
		}
		
		TextView dayText = (TextView) convertView.findViewById(R.id.dayName);
		ImageView icon = (ImageView) convertView.findViewById(R.id.dayIcon);
		TextView tempText = (TextView) convertView.findViewById(R.id.dayTemp);
		
		DayForecast forecast = dayForecastList.get(position);
		Date d = new Date();
		Calendar gc =  new GregorianCalendar();
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_MONTH, position + 1);
		dayText.setText(sdf.format(gc.getTime()));

		icon.setImageResource(WeatherIconMapper.getWeatherResource(forecast.weather.currentCondition.getIcon(), forecast.weather.currentCondition.getWeatherId()));
		
		tempText.setText( Math.round(forecast.forecastTemp.min) + units.tempUnit + "/" + Math.round(forecast.forecastTemp.max) + units.tempUnit);
		
		return convertView;
	}
	
	

}
