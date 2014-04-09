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
package com.survivingwithandroid.weather.lib.model;

import com.survivingwithandroid.weather.lib.WeatherCode;

/**
 * This class represents the current weather condition as retrieved by the provider.
 * An instance of this class is returned by the providir using {@link com.survivingwithandroid.weather.lib.WeatherClient.WeatherClientListener}
 *
 */
public class CurrentWeather extends Weather {

    public Location location = new Location();

	public Condition currentCondition = new Condition();
	public Temperature temperature = new Temperature();
	public Wind wind = new Wind();
	public Rain rain = new Rain();
	public Snow snow = new Snow()	;
	public Clouds clouds = new Clouds();


	public byte[] iconData;

    /*
    * Current Weather condition. This class holds some weather paraters like condition description,
    * pressure, humidity, visibility and so on
    * Notice that some of this parameters can be null if the provider doesn't support them.
    * The values are represented using the {@link Weather.WeatherUnit}
    * */
	public class Condition {
		private int weatherId;
		private String condition;
		private String descr;
		private String icon;
		private float pressure;
		private float humidity;
		private float visibility;
        private int pressureTrend;
        private WeatherCode weatherCode;

		public int getWeatherId() {
			return weatherId;
		}
		public void setWeatherId(int weatherId) {
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
		public float getPressure() {
			return pressure;
		}
		public void setPressure(float pressure) {
			this.pressure = pressure;
		}
		public float getHumidity() {
			return humidity;
		}
		public void setHumidity(float humidity) {
			this.humidity = humidity;
		}

        public float getVisibility() {
            return visibility;
        }

        public void setVisibility(float visibility) {
            this.visibility = visibility;
        }

        public int getPressureTrend() {
            return pressureTrend;
        }

        public void setPressureTrend(int pressureTrend) {
            this.pressureTrend = pressureTrend;
        }

        public WeatherCode getWeatherCode() {
            return weatherCode;
        }

        public void setWeatherCode(WeatherCode weatherCode) {
            this.weatherCode = weatherCode;
        }
    }

    /*
    * Current Temperature class
    * */
	public  class Temperature {


		private float temp;

		private float minTemp;
		private float maxTemp;
        /*
        * Current temperature
        * @return float
        * */
		public float getTemp() {
			return temp;
		}
		public void setTemp(float temp) {
			this.temp = temp;
		}
        /*
        * Min temperature todat
        * @return int
        * */
		public int getMinTemp() {
			return (int) minTemp;
		}
		public void setMinTemp(float minTemp) {
			this.minTemp = minTemp;
		}
        /*
        * Max temperature today
        * @return int
        * */
		public int getMaxTemp() {
			return (int) maxTemp;
		}
		public void setMaxTemp(float maxTemp) {
			this.maxTemp = maxTemp;
		}
		
	}

    /*
    * Current Wind conditions
    * */
	public  class Wind {
		private float speed;
		private float deg;
        private float chill;
        private float gust;

        /*
        * Current wind speed
        * @retrun float
        * */
		public float getSpeed() {
			return speed;
		}
		public void setSpeed(float speed) {
			this.speed = speed;
		}
        /*
        * Current wind direction
        * @retrun float
        * */
        public float getDeg() {
			return deg;
		}
		public void setDeg(float deg) {
			this.deg = deg;
		}

        /*
        * Current wind chill
        * @retrun float
        * */
        public float getChill() {
            return chill;
        }

        public void setChill(float chill) {
            this.chill = chill;
        }

        public float getGust() {
            return gust;
        }

        public void setGust(float gust) {
            this.gust = gust;
        }
    }

    /*
    * Rain chance and ammount
    * */
	public  class Rain {
		private String time;
		private float ammount;
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
        * Ammount of rain expected
        * @return float
        * */
		public float getAmmount() {
			return ammount;
		}
		public void setAmmount(float ammount) {
			this.ammount = ammount;
		}
	}

    /*
   * Snow chance and ammount
   * */
	public  class Snow {
		private String time;
		private float ammount;

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
       * @return float
       * */
		public float getAmmount() {
			return ammount;
		}
		public void setAmmount(float ammount) {
			this.ammount = ammount;
		}
		
		
	}

    /*
    * Current cloud information
    * */
	public  class Clouds {
		private int perc;

        /*
         * Coverage in %
         *
         * @retrun int
         */
		public int getPerc() {
			return perc;
		}

		public void setPerc(int perc) {
			this.perc = perc;
		}
		
		
	}

}
