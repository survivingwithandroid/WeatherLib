package com.survivingwithandroid.weather.lib.demo15.fragment;


import android.app.Fragment;
import android.os.Bundle;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.demo15.R;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.model.HistoricalHourWeather;
import com.survivingwithandroid.weather.lib.model.HistoricalWeather;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HistoricalWeatherFragment extends Fragment {

    private RecyclerView rv;
    private WeatherClient client;
    private RecyclerWeatherAdapter rwa;

    public HistoricalWeatherFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeatherClient.ClientBuilder builder = new WeatherClient.ClientBuilder();
        WeatherConfig config = new WeatherConfig();

        try {
            client = builder.attach(getActivity())
                    .provider(new OpenweathermapProviderType())
                    .httpClient(com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault.class)
                    .config(config)
                    .build();
        } catch (WeatherProviderInstantiationException wpie) {
            wpie.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_historical_weather, container, false);
        rv = (RecyclerView) v.findViewById(R.id.weather_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rwa = new RecyclerWeatherAdapter(null);
        rv.setAdapter(rwa);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        Calendar c1 = Calendar.getInstance();
        c1.clear();
        //c1.set(2014,Calendar.JANUARY,1,0,0,0);
        c1.set(2014, Calendar.JULY, 6,0,0,0);

        Date d1 = c1.getTime();

        Calendar c2 = Calendar.getInstance();
        c2.clear();
       // c2.set(2014,Calendar.JANUARY, 2,0,0,0);
        c2.set(2014, Calendar.JULY, 7,0,0,0);
        Date d2 = c2.getTime();

        //client.getHistoricalWeather(new WeatherRequest("2885679"), d1, d2, new WeatherClient.HistoricalWeatherEventListener() {
        client.getHistoricalWeather(new WeatherRequest(56.0499F, 12.7067F), d1, d2, new WeatherClient.HistoricalWeatherEventListener() {
            @Override
            public void onWeatherRetrieved(HistoricalWeather histWeather) {

                List<HistoricalHourWeather> histData = histWeather.getHoistoricalData();
                Log.d("Hist", "Data ["+histData+"] - Size ["+histData.size()+"]");
                rwa.setData(histData);
                rwa.notifyDataSetChanged();

                rv.setAdapter(rwa);
            }

            @Override
            public void onWeatherError(WeatherLibException wle) {
                Toast.makeText(getActivity(), "Error parsing the response", Toast.LENGTH_SHORT).show();
                wle.printStackTrace();
            }

            @Override
            public void onConnectionError(Throwable t) {
                Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static class RecyclerWeatherAdapter extends RecyclerView.Adapter<RecyclerWeatherAdapter.WeatherViewHolder> {

        private List<HistoricalHourWeather> histData;
        private SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy  HH:mm");

        public RecyclerWeatherAdapter(List<HistoricalHourWeather> histData) {
            this.histData = histData;
        }

        @Override
        public void onBindViewHolder(WeatherViewHolder weatherViewHolder, int i) {
            HistoricalHourWeather hhw = histData.get(i);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(hhw.timestamp);
            String d = sdf.format(c.getTime());
            weatherViewHolder.tvDate.setText(d);
            weatherViewHolder.tvMinTemp.setText(String.valueOf(hhw.weather.temperature.getMinTemp()));
            weatherViewHolder.tvMaxTemp.setText(String.valueOf(hhw.weather.temperature.getMaxTemp()));
        }

        @Override
        public WeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hist_row, null);
            WeatherViewHolder wvh = new WeatherViewHolder(v);
            return wvh;
        }

        public static class WeatherViewHolder extends RecyclerView.ViewHolder {
            protected TextView tvDate;
            protected TextView tvMinTemp;
            protected TextView tvMaxTemp;

            public WeatherViewHolder(View v) {
                super(v);
                this.tvDate = (TextView) v.findViewById(R.id.hist_date);
                this.tvMinTemp = (TextView) v.findViewById(R.id.hist_min_temp);
                this.tvMaxTemp = (TextView) v.findViewById(R.id.hist_max_temp);
            }
         }


        @Override
        public int getItemCount() {
            return histData == null ? 0 : histData.size();
        }

        @Override
        public long getItemId(int position) {
            return histData == null ? 0 : histData.get(position).hashCode();
        }

        public void setData(List<HistoricalHourWeather> histData) {
            this.histData = histData;
        }
    }
}