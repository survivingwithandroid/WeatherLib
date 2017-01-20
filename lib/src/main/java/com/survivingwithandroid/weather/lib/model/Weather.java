package com.survivingwithandroid.weather.lib.model;

import com.survivingwithandroid.weather.lib.DefaultValues;
import com.survivingwithandroid.weather.lib.WeatherCode;

/**
 * ${copyright}.
 */

/**
* This class represents the Weather in the Location selected
*
 * @author Francesco Azzola
* */
public class Weather {
    public Location location = new Location();
    public Condition currentCondition = new Condition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain[] rain = {new Rain(), new Rain()};
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();


    public byte[] iconData;

    /*
    * Weather condition. This class holds some weather parameters like condition description,
    * pressure, humidity, visibility and so on
    * Notice that some of this parameters can be null if the provider doesn't support them.
    * The values are represented using the {@link Weather.WeatherUnit}
    * */
    public class Condition {
        private Integer weatherId;
        private String condition = DefaultValues.DEFAULT_STRING;
        private String descr = DefaultValues.DEFAULT_STRING;
        private String icon = DefaultValues.DEFAULT_STRING;
        private Double pressure = DefaultValues.DEFAULT_DOUBLE;
        private Double humidity = DefaultValues.DEFAULT_DOUBLE;
        private Double visibility = DefaultValues.DEFAULT_DOUBLE;
        private Integer pressureTrend = DefaultValues.DEFAULT_INTEGER;
        private Double feelsLike = DefaultValues.DEFAULT_DOUBLE;
        private Double UV = DefaultValues.DEFAULT_DOUBLE;
        private Double dewPoint = DefaultValues.DEFAULT_DOUBLE;
        private String heatIndex = DefaultValues.DEFAULT_STRING;
        private String solarRadiation = DefaultValues.DEFAULT_STRING;
        private Double pressureSeaLevel = DefaultValues.DEFAULT_DOUBLE;
        private Double pressureGroundLevel = DefaultValues.DEFAULT_DOUBLE;
        private WeatherCode weatherCode;

        public Integer getWeatherId() {
            return weatherId;
        }

        public void setWeatherId(Integer weatherId) {
            this.weatherId = weatherId;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        public Double getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(Double feelsLike) {
            this.feelsLike = feelsLike;
        }

        public Double getUV() {
            return UV;
        }

        public void setUV(Double UV) {
            this.UV = UV;
        }

        public Double getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(Double dewPoint) {
            this.dewPoint = dewPoint;
        }

        public String getHeatIndex() {
            return heatIndex;
        }

        public void setHeatIndex(String heatIndex) {
            this.heatIndex = heatIndex;
        }

        public String getSolarRadiation() {
            return solarRadiation;
        }

        public void setSolarRadiation(String solarRadiation) {
            this.solarRadiation = solarRadiation;
        }

        public Double getVisibility() {
            return visibility;
        }

        public void setVisibility(Double visibility) {
            this.visibility = visibility;
        }

        public Integer getPressureTrend() {
            return pressureTrend;
        }

        public void setPressureTrend(Integer pressureTrend) {
            this.pressureTrend = pressureTrend;
        }

        public WeatherCode getWeatherCode() {
            return weatherCode;
        }

        public void setWeatherCode(WeatherCode weatherCode) {
            this.weatherCode = weatherCode;
        }

        public Double getPressureSeaLevel() {
            return pressureSeaLevel;
        }

        public void setPressureSeaLevel(Double pressureSeaLevel) {
            this.pressureSeaLevel = pressureSeaLevel;
        }

        public Double getPressureGroundLevel() {
            return pressureGroundLevel;
        }

        public void setPressureGroundLevel(Double pressureGroundLevel) {
            this.pressureGroundLevel = pressureGroundLevel;
        }
    }

    /*
    * Current Temperature class
    * */
    public class Temperature {

        private Double temp;
        private Double minTemp;
        private Double maxTemp;

        /*
        * Current temperature
        * @return Double
        * */
        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        /*
        * Min temperature todat
        * @return Integer
        * */
        public Double getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(Double minTemp) {
            this.minTemp = minTemp;
        }

        /*
        * Max temperature today
        * @return Integer
        * */
        public Double getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(Double maxTemp) {
            this.maxTemp = maxTemp;
        }

    }

    /*
    * Current Wind conditions
    * */
    public class Wind {
        private Double speed;
        private Double deg;
        private Double chill;
        private Double gust;

        /*
        * Current wind speed
        * @retrun Double
        * */
        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        /*
        * Current wind direction
        * @retrun Double
        * */
        public Double getDeg() {
            return deg;
        }

        public void setDeg(Double deg) {
            this.deg = deg;
        }

        /*
        * Current wind chill
        * @retrun Double
        * */
        public Double getChill() {
            return chill;
        }

        public void setChill(Double chill) {
            this.chill = chill;
        }

        public Double getGust() {
            return gust;
        }

        public void setGust(Double gust) {
            this.gust = gust;
        }
    }

    /*
    * Rain chance and ammount
    * */
    public class Rain {
        private String time;
        private Double ammount;
        private Double chance;

        /**
        * Hour interval
        *
        * @return string
        * */
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        /**
        * Ammount of rain expected
        * @return Double
        * */
        public Double getAmmount() {
            return ammount;
        }

        public void setAmmount(Double ammount) {
            this.ammount = ammount;
        }

        /**
         * Chance of rain
         * @return Double
         * */
        public Double getChance() {
            return chance;
        }

        public void setChance(Double chance) {
            this.chance = chance;
        }
    }

    /*
   * Snow chance and ammount
   * */
    public class Snow {
        private String time;
        private Double ammount;

        /*
        * Hour interval
        *
        * @return string
        * */
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        /*
       * Ammount of snouw expected
       * @return Double
       * */
        public Double getAmmount() {
            return ammount;
        }

        public void setAmmount(Double ammount) {
            this.ammount = ammount;
        }


    }

    /*
    * Current cloud information
    * */
    public class Clouds {
        private Integer perc;

        /*
         * Coverage in %
         *
         * @retrun Integer
         */
        public Integer getPerc() {
            return perc;
        }

        public void setPerc(Integer perc) {
            this.perc = perc;
        }


    }
}
