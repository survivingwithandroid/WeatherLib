package com.survivingwithandroid.weather.lib.demo15.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.demo15.R;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
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
        config.ApiKey = getResources().getString(R.string.wunderground_key);
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
        Params params = null;
        Bundle b = getArguments();
        String imageType = b.getString("image_type");
        if (imageType.equals("radar"))
            params = new Params.ParamsBuilder()
                .setImageHeight(600)
                .setImageWidth(600)
                .setCenterLat(40.710F)
                .setCenterLon(-74F)
                .setRadius(100)
                .setNewMap(true)
                .setImageType(Params.ParamsBuilder.IMAGE_TYPE.RADAR)
                .build();
        else if (imageType.equals("satellite"))
            params = new Params.ParamsBuilder()
                    .setImageHeight(600)
                    .setImageWidth(600)
                    .setCenterLat(41.89F)
                    .setCenterLon(12.48F)
                    .setRadius(1000)
                    .setNewMap(true)
                    .setImageType(Params.ParamsBuilder.IMAGE_TYPE.SATELLITE)
                    .setSatelliteImageType(Params.ParamsBuilder.SATELLITE_IMAGE_TYPE.sat_ir4)
                    .build();


        client.getWeatherImage("", params, new WeatherClient.WeatherImageListener() {
            @Override
            public void onImageReady(Bitmap image) {
                imageView.setImageBitmap(image);
            }

            /**
             * This method is called when an error occured during the HTTP connection
             *
             * @param t {@link Throwable}
             */
            @Override
            public void onConnectionError(Throwable t) {
                // handle errors here
            }

            /**
             * This method is called when an error occured during the data parsing
             *
             * @param wle {@link com.survivingwithandroid.weather.lib.exception.WeatherLibException}
             */
            @Override
            public void onWeatherError(WeatherLibException wle) {
                // handle errors here
            }
        });
    }
}