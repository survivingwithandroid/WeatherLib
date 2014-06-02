package com.survivingwithandroid.weather.lib.model;

/**
 * ${copyright}.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Ths class holds the historical weather information retrieved from the weather provider
 *
 * @author Francesco Azzola
 */
public class HistoricalWeather extends BaseWeather {

    /**
     * Historical weather information as retrieved by the weather provider. Traversing this list
     * you can have all the historical information
     */
    public List<HistoricalHourWeather> historicalData = new ArrayList<HistoricalHourWeather>();

    /*
    * Add a single historical hour observation
    *
    * @param item {@see HistoricalHourWeather}
    * */
    public void addHistoricalHourWeather(HistoricalHourWeather item) {
        historicalData.add(item);
    }

    /*
    * Retrieve the hour historical info in the list.
    *
    * @param hourNum int
    * @return {@see HistoricalHourWeather}
    * */
    public HistoricalHourWeather getHistoricalHourWeather(int hourNum) {
        return historicalData.get(hourNum);
    }

    /*
    * Get the full list
    *
    * @return {@see List} of {@see HistoricalHourWeather}
    * */
    public List<HistoricalHourWeather> getHoistoricalData() {
        return this.historicalData;
    }
}
