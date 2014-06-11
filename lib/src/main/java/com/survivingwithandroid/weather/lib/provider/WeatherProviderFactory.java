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

package com.survivingwithandroid.weather.lib.provider;

import android.util.Log;

import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;

/**
* This class implements the weather provider class factory. It helps you to create
* the weather provider providing the configuration parameters
*
* @deprecated You shouldn't use this class anymore to instantiate a weather provider from 1.4. Use instead {@link com.survivingwithandroid.weather.lib.WeatherClient.ClientBuilder}
* @author Francesco Azzola
* */
public class WeatherProviderFactory {

    public static IWeatherProvider createProvider(IProviderType provType, WeatherConfig config) throws WeatherProviderInstantiationException {
        return _createProvider(provType, config);
    }

    private static IWeatherProvider _createProvider(IProviderType provType, WeatherConfig config) throws WeatherProviderInstantiationException {
        try {
            Class<?> clazz = Class.forName(provType.getProviderClass());
            IWeatherProvider provider = (IWeatherProvider) clazz.newInstance();
            Log.d("App", "Provider [" + provider + "]");

            if (config != null)
                provider.setConfig(config);

            if (provType.getCodeProviderClass() != null) {
                Class<?> clazzCodeProvider = Class.forName(provType.getCodeProviderClass());
                IWeatherCodeProvider codeProvider = (IWeatherCodeProvider) clazzCodeProvider.newInstance();
                provider.setWeatherCodeProvider(codeProvider);
            }

            return provider;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            throw new WeatherProviderInstantiationException();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new WeatherProviderInstantiationException();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new WeatherProviderInstantiationException();
        }
    }

    public static IWeatherProvider createProvider(IProviderType provType) throws WeatherProviderInstantiationException {
        return _createProvider(provType, null);
    }


}
