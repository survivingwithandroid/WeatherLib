package com.survivingwithandroid.weather.lib.provider.yahooweather;

import com.survivingwithandroid.weather.lib.provider.IProviderType;

public class YahooProviderType implements IProviderType {
    @Override
    public String getProviderClass() {
        return "com.survivingwithandroid.weather.lib.provider.yahooweather.YahooWeatherProvider";
    }

    @Override
    public String getCodeProviderClass() {
        return "com.survivingwithandroid.weather.lib.provider.yahooweather.YahooWeatherCodeProvider";
    }
}
