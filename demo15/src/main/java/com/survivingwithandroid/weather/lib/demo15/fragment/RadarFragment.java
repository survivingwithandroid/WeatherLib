package com.survivingwithandroid.weather.lib.demo15.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.demo15.R;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.provider.forecastio.ForecastIOProviderType;
import com.survivingwithandroid.weather.lib.provider.wunderground.WeatherUndergroundProviderType;
import com.survivingwithandroid.weather.lib.request.Params;

/**
 * ${copyright}.
 */
public class RadarFragment extends Fragment {

    private ImageView imageView;
    private WeatherClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_radar, container, false);
        imageView = (ImageView) v.findViewById(R.id.wImage);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WeatherClient.ClientBuilder builder = new WeatherClient.ClientBuilder();
        WeatherConfig config = new WeatherConfig();
        config.ApiKey = "86c6f2cc0d64bc7b";
        try {
            client = builder.attach(getActivity())
                    .provider(new WeatherUndergroundProviderType())
                    .httpClient(com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault.class)
                    .config(config)
                    .build();
        } catch (WeatherProviderInstantiationException wpie) {
            wpie.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Params params = new Params.ParamsBuilder()
                .setImageHeight(400)
                .setImageWidth(400)
                .setNewMap(true)
                .build();

        client.getWeatherImage("/q/zmw:00000.1.16181", params, new WeatherClient.WeatherImageListener() {
            @Override
            public void onImageReady(Bitmap image) {
                imageView.setImageBitmap(image);
            }
        });
    }
}