package com.survivingwithandroid.weather.lib.demo15.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.demo15.R;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.provider.wunderground.extrequest.WebcamFeatureRequest;
import com.survivingwithandroid.weather.lib.provider.wunderground.extresponse.WebcamFeatureResponse;
import com.survivingwithandroid.weather.lib.provider.wunderground.extresponse.WebcamResponseParser;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.List;

/**
 * Created by Francesco on 09/07/2014.
 */
public class WebcamFragment extends Fragment {

    private WeatherClient client;
    private ImageView webcamView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webcam, container, false);
        webcamView = (ImageView) v.findViewById(R.id.webcam);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        WeatherRequest wRequest = new WeatherRequest("/q/zmw:00000.1.16181");
        WeatherConfig config = new WeatherConfig();
        config.ApiKey = getResources().getString(R.string.wunderground_key);

        WebcamFeatureRequest req = new WebcamFeatureRequest(wRequest, config);
        WebcamResponseParser parser = new WebcamResponseParser();

       client.getProviderWeatherFeature(wRequest, req, parser, new WeatherClient.GenericRequestWeatherEventListener<List<WebcamFeatureResponse>>() {
           @Override
           public void onResponseRetrieved(List<WebcamFeatureResponse> data) {
               Log.d("Webcam", "Data ["+data+"]");
               for (WebcamFeatureResponse resp : data) {
                   Log.d("Wecbam", "Name ["+resp.city+"] - URL ["+resp.currentImageUrl+"]");

               }
               if (data != null && data.size() > 0) {
                   // We take the first element
                   WebcamFeatureResponse resp = data.get(0);
                   client.getImage(resp.currentImageUrl, new WeatherClient.WeatherImageListener() {
                       @Override
                       public void onImageReady(Bitmap image) {
                           webcamView.setImageBitmap(image);
                       }

                       @Override
                       public void onWeatherError(WeatherLibException wle) {
                            // Never used in this case
                       }

                       @Override
                       public void onConnectionError(Throwable t) {
                           Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                       }
                   });
               }
           }

           @Override
           public void onWeatherError(WeatherLibException wle) {
               Toast.makeText(getActivity(), "Error parsing the response", Toast.LENGTH_SHORT).show();
               wle.printStackTrace();
           }

           @Override
           public void onConnectionError(Throwable t) {

           }
       });
    }
}
