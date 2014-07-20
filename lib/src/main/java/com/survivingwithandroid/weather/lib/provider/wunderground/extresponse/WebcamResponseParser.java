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
package com.survivingwithandroid.weather.lib.provider.wunderground.extresponse;

import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.response.GenericResponseParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class parses the response from Weatherundergound provider and extract the information related to the webcams
 *
 * @since 1.5.3
 * */
public class WebcamResponseParser extends GenericResponseParser<List<WebcamFeatureResponse>> {
    @Override
    public List<WebcamFeatureResponse> parseData(String data) throws WeatherLibException {
        List<WebcamFeatureResponse> result = new ArrayList<WebcamFeatureResponse>();
        try {
            JSONObject jObj = new JSONObject(data);
            JSONArray webcams = jObj.getJSONArray("webcams");
            for (int i=0; i < webcams.length(); i++) {
                JSONObject webcam = webcams.getJSONObject(i);

                WebcamFeatureResponse resp = new WebcamFeatureResponse();
                resp.city = webcam.getString("city");
                resp.state = webcam.getString("state");
                resp.country = webcam.getString("country");
                resp.camId = webcam.getString("camid");
                resp.handle = webcam.getString("handle");
                resp.link = webcam.getString("link");
                resp.linkText = webcam.getString("linktext");
                resp.currentImageUrl = webcam.getString("CURRENTIMAGEURL");
                resp.widgetCurrentImageUrl = webcam.getString("WIDGETCURRENTIMAGEURL");

                result.add(resp);
            }
        }
        catch(Throwable t) {
            throw new WeatherLibException(t);
        }

        return result;
    }
}
