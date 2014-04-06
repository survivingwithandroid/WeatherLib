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
package com.survivingwithandroid.weatherapp.adapter;

import java.util.List;

import com.survivingwithandroid.weatherapp.R;
import com.survivingwithandroid.weather.lib.model.City;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Francesco
 *
 */
public class CityAdapter extends ArrayAdapter<City>{
	
	private Context ctx;
	private List<City> cityList;
	
	public CityAdapter(Context ctx, List<City> cityList) {
		super(ctx, R.layout.row_city_layout, cityList);
		this.cityList = cityList;
		this.ctx = ctx;
	}
	
	
	@Override
	public int getCount() {
		return cityList.size();
	}


	@Override
	public City getItem(int position) {
		
		return cityList.get(position);
	}


	@Override
	public long getItemId(int position) {
		City city = cityList.get(position);
		return Long.parseLong(city.getId());
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_city_layout, parent, false);
		}
		
		City city = cityList.get(position);
		TextView cityText = (TextView) convertView.findViewById(R.id.cityName);
		cityText.setText(city.getName() + "," + city.getCountry());
		
		return convertView;
	}

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

}
