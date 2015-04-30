package com.survivingwithandroid.weather.lib.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.survivingwithandroid.weather.lib.WeatherCode;

/**
 * ${copyright}.
 */

/**
* This class represents the Weather in the Location selected
*
 * @author Francesco Azzola
* */
public class Weather implements Parcelable {
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
    public static class Condition implements Parcelable {
        private int weatherId;
        private String condition;
        private String descr;
        private String icon;
        private float pressure;
        private float humidity;
        private float visibility;
        private int pressureTrend;
        private float feelsLike;
        private float UV;
        private float dewPoint;
        private String heatIndex;
        private String solarRadiation;
        private float pressureSeaLevel;
        private float pressureGroundLevel;
        private WeatherCode weatherCode;

        public Condition() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(weatherId);
            dest.writeString(condition);
            dest.writeString(descr);
            dest.writeString(icon);
            dest.writeFloat(pressure);
            dest.writeFloat(humidity);
            dest.writeFloat(visibility);
            dest.writeInt(pressureTrend);
            dest.writeFloat(feelsLike);
            dest.writeFloat(UV);
            dest.writeFloat(dewPoint);
            dest.writeString(heatIndex);
            dest.writeString(solarRadiation);
            dest.writeFloat(pressureSeaLevel);
            dest.writeFloat(pressureGroundLevel);
        }

        public static final Parcelable.Creator<Condition> CREATOR
                = new Parcelable.Creator<Condition>() {
            public Condition createFromParcel(Parcel in) {
                return new Condition(in);
            }

            public Condition[] newArray(int size) {
                return new Condition[size];
            }
        };

        private Condition(Parcel in) {
            weatherId = in.readInt();
            condition = in.readString();
            descr = in.readString();
            icon = in.readString();
            pressure = in.readFloat();
            humidity = in.readFloat();
            visibility = in.readFloat();
            pressureTrend = in.readInt();
            feelsLike = in.readFloat();
            UV = in.readFloat();
            dewPoint = in.readFloat();
            heatIndex = in.readString();
            solarRadiation = in.readString();
            pressureSeaLevel = in.readFloat();
            pressureGroundLevel = in.readFloat();
        }

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

        public float getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(float feelsLike) {
            this.feelsLike = feelsLike;
        }

        public float getUV() {
            return UV;
        }

        public void setUV(float UV) {
            this.UV = UV;
        }

        public float getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(float dewPoint) {
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

        public float getPressureSeaLevel() {
            return pressureSeaLevel;
        }

        public void setPressureSeaLevel(float pressureSeaLevel) {
            this.pressureSeaLevel = pressureSeaLevel;
        }

        public float getPressureGroundLevel() {
            return pressureGroundLevel;
        }

        public void setPressureGroundLevel(float pressureGroundLevel) {
            this.pressureGroundLevel = pressureGroundLevel;
        }
    }

    /*
    * Current Temperature class
    * */
    public static class Temperature implements Parcelable {

        private float temp;
        private float minTemp;
        private float maxTemp;

        public Temperature() {
        }

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
        public float getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }

        /*
        * Max temperature today
        * @return int
        * */
        public float getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }

        // Parcelable

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(temp);
            dest.writeFloat(minTemp);
            dest.writeFloat(maxTemp);
        }

        public static final Parcelable.Creator<Temperature> CREATOR
                = new Parcelable.Creator<Temperature>() {
            public Temperature createFromParcel(Parcel in) {
                return new Temperature(in);
            }

            public Temperature[] newArray(int size) {
                return new Temperature[size];
            }
        };

        private Temperature(Parcel in) {
            temp = in.readFloat();
            minTemp = in.readFloat();
            maxTemp = in.readFloat();
        }


    }

    /*
    * Current Wind conditions
    * */
    public static class Wind implements  Parcelable {
        private float speed;
        private float deg;
        private float chill;
        private float gust;


        public Wind() {
        }

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

        // Parcelable

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(speed);
            dest.writeFloat(deg);
            dest.writeFloat(chill);
            dest.writeFloat(gust);
        }

        public static final Parcelable.Creator<Wind> CREATOR
                = new Parcelable.Creator<Wind>() {
            public Wind createFromParcel(Parcel in) {
                return new Wind(in);
            }

            public Wind[] newArray(int size) {
                return new Wind[size];
            }
        };

        private Wind(Parcel in) {
            speed = in.readFloat();
            deg = in.readFloat();
            chill = in.readFloat();
            gust = in.readFloat();
        }

    }

    /*
    * Rain chance and ammount
    * */
    public static class Rain implements Parcelable {
        private String time;
        private float ammount;
        private float chance;

        public Rain() {
        }

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
        * @return float
        * */
        public float getAmmount() {
            return ammount;
        }

        public void setAmmount(float ammount) {
            this.ammount = ammount;
        }

        /**
         * Chance of rain
         * @return float
         * */
        public float getChance() {
            return chance;
        }

        public void setChance(float chance) {
            this.chance = chance;
        }

        // Parcelable

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(time);
            dest.writeFloat(ammount);
            dest.writeFloat(chance);
        }

        public static final Parcelable.Creator<Rain> CREATOR
                = new Parcelable.Creator<Rain>() {
            public Rain createFromParcel(Parcel in) {
                return new Rain(in);
            }

            public Rain[] newArray(int size) {
                return new Rain[size];
            }
        };

        private Rain(Parcel in) {
            time = in.readString();
            ammount = in.readFloat();
            chance = in.readFloat();
        }
    }

    /*
   * Snow chance and ammount
   * */
    public static class Snow implements Parcelable {
        private String time;
        private float ammount;

        public Snow() {
        }

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

        // Parcelable

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(time);
            dest.writeFloat(ammount);
        }

        public static final Parcelable.Creator<Snow> CREATOR
                = new Parcelable.Creator<Snow>() {
            public Snow createFromParcel(Parcel in) {
                return new Snow(in);
            }

            public Snow[] newArray(int size) {
                return new Snow[size];
            }
        };

        private Snow(Parcel in) {
            time = in.readString();
            ammount = in.readFloat();
        }

    }

    /*
    * Current cloud information
    * */
    public static class Clouds implements Parcelable {
        private int perc;

        public Clouds() {
        }

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

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(perc);

        }

        // Parcelable

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Parcelable.Creator<Clouds> CREATOR
                = new Parcelable.Creator<Clouds>() {
            public Clouds createFromParcel(Parcel in) {
                return new Clouds(in);
            }

            public Clouds[] newArray(int size) {
                return new Clouds[size];
            }
        };

        private Clouds(Parcel in) {
            perc = in.readInt();
        }

    }



    // Parcellable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(location, flags);
        dest.writeParcelable(currentCondition, flags);
        dest.writeParcelable(temperature, flags);
        dest.writeParcelable(wind, flags);
        dest.writeArray(rain);
        dest.writeParcelable(snow, flags);
        dest.writeParcelable(clouds, flags);
    }

    public static final Parcelable.Creator<Weather> CREATOR
            = new Parcelable.Creator<Weather>() {
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    private Weather(Parcel in) {
        location = (Location) in.readParcelable(Location.class.getClassLoader());
        currentCondition = (Condition) in.readParcelable(Condition.class.getClassLoader());
        temperature = (Temperature) in.readParcelable(Temperature.class.getClassLoader());
        wind = (Wind) in.readParcelable(Wind.class.getClassLoader());
        rain = (Rain[]) in.readArray(Rain.class.getClassLoader());
        snow = (Snow) in.readParcelable(Snow.class.getClassLoader());
        clouds = (Clouds) in.readParcelable(Clouds.class.getClassLoader());
    }

    public Weather() {}
}
