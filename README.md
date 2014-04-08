WeatherLib
==========

Android weather lib for creating easily weather app. The lib implements several protocols to get weather information from the most important weather data provider.


This is a library project that helps developers to create **easily** weather app without worrying about querying and extracting information from remote weather servers.
It is very simple to use and you can easily extend it to support different weather provider or change the library behaviour. It is highly **customizable** and it is implemented in pure Java.

**Using this lib you can focus your mind only on the app and how it should provides the weather information to the final user without dealing with internal details about protocols, connections and so on.**

This library provides:

* *Remote service invocation*
* *Handling HTTP requests and response in a separate thread respect to the main Android thread, so that you don't have to worry about ANR (Application Not Responding) problems* 
* *Implement specific protocol to extract information from remote weather server*
* *Querying the city using name pattern*
* *Querying the city using geographic coordinates*
* *Querying actual weather condition*
* *Querying forecast weather condition*
* *Retrieve default weather condition icon for each weather provider*
* *Abstract app implementation from the specific weather provider selected, so that you can reuse the same app with different weather provider*
* *Handling errors*
* *Implementing a notifition/listener mechanism so that your requests don't get locked while the library is processing the response or while the server is contacted*
* *You can easily extend the lib and implement your weather provider*


At the moment the library supports [Yahoo! Weather] and [Openweathermap]  as weather provider. 

## Version
1.0

## App
To provide an example about how to use the libray this project contains a fully working app that shows weather information based on this library (see the demo module).

## Note
Please notice that some weather provider requires you to get a private API key before using their services, moreover some services aren't free. So if you want to use the Lib with these weather providers you should get your private KEY registering your account.


## Getting started
The library is based on two main concepts that stands at its base:
* WeatherClient  : The library entry point. It deals with HTTP request/respnse

* WeatherProvider : Specific protocol implementation (i.e Openweathermap or Yahoo! Weather)

``` java
WeatherClient client = WeatherClientDefault.getInstance();
WeatherConfig config = new WeatherConfig(); // Default configuration
IWeatherProvider provider = WeatherProviderFactory.createProvider(new YahooProviderType(), config);
client.setProvider(provider);
client.init(context);
// Now we can make our request
client.getCurrentCondition(cityId, new WeatherClient.WeatherEventListener() {
            @Override
            public void onWeatherRetrieved(CurrentWeather weather) {
             // The current weather data is ready and we can update our view
            }
            @Override
            public void onWeatherError(WeatherLibException t) {
               // Error parsing the response
            }

            @Override
            public void onConnectionError(Throwable t) {
                // Error during the connection
            }
});

// If you want to get the weather forecast
client.getForecastWeather(cityId, new WeatherClient.ForecastWeatherEventListener() {
            @Override
            public void onWeatherRetrieved(WeatherForecast forecast) {
               // Update our view with the data retrieved
            }

            @Override
            public void onWeatherError(WeatherLibException t) {
                // Error parsing the response
            }

             @Override
            public void onConnectionError(Throwable t) {
                // Error during the connection
            }
});

// Done!!!
```

As you can notice, building a weather app using this library if very simple.

## Dev tutorial
**WeatherClient** is an abstract class, implemented by WeatherClientDefault, that is the entry point to cotact a remote weather server and query information. The lib provides, as said, a default implementation called **WeatherClientDefault** that uses Volley lib to make HTTP request. You can provides your WeatherClient implementation and uses different mechanism to handle HTTP request and response. So the first step is creating an instance of this class:

``` java
WeatherClient client = WeatherClientDefault.getInstance();
```

The WeatherClient (or its implementation) handles all the details related to making HTTP request, retrieve the response and handle errors that can accour. In the WeatherClientDefault implementation the request and response are handled in a separate thread and you should do the same if you want to implement your custom weather client. There are some interface that you should implement to get notify when the data you required is ready. Generally speaking you can for:

* searching for a city list by geographic coordinates
``` 
public abstract void searchCityByLocation(Criteria criteria, final CityEventListener listener) throws LocationProviderNotFoundException;
``` 
* searching for a city list by name pattern
```
public abstract void searchCity(String pattern, final CityEventListener listener)  throws ApiKeyRequiredException;
```
* get current weather information
```
public abstract void getCurrentCondition(String location, final WeatherEventListener listener) throws ApiKeyRequiredException ;
```
* get forecast weather information
```
public abstract void getForecastWeather(String location, final ForecastWeatherEventListener listener)  throws ApiKeyRequiredException;
```

* get the default weather condition icon provided by the weather provider
```
public abstract void getDefaultProviderImage(String icon, final WeatherImageListener listener);
```

>**This kind of requests can be done for all the weather provider and you can expect to receive the same information with the same structure even if you use different provides. The lib abstracts the protocol details and the information returned presented them to you in a unified manner.**

**WeatherProvider** is a specific implementation that handles all the details related to the protocol used by the weather provider (i.e Openweathermap and so on). You can change at runtime the WeatherProvider without changing your code. All the weather provider implements **IWeatherProvider** that provides some methods to parse the data received.

#Community
If you like there is a Community to talk about new features, suggest tips or report bugs. Please join:

[![](http://4.bp.blogspot.com/-Bfh2unbdc84/UcGqVJKdMwI/AAAAAAAAAOc/W4kGiTU-fYk/s1600/google_plus_58.png)](https://plus.google.com/communities/117946761543584564970)

#Credits 
*Author:* Francesco Azzola (JFrankie) ([survivingwithandroid@gmail.com](mailto:survivingwithandroid@gmail.com))

[![](http://4.bp.blogspot.com/-Bfh2unbdc84/UcGqVJKdMwI/AAAAAAAAAOc/W4kGiTU-fYk/s1600/google_plus_58.png)](http://www.google.com/+FrancescoAzzola)  [![](http://3.bp.blogspot.com/-_JSQStno9N8/UcGWEW7V9AI/AAAAAAAAAOM/_qFVUjIaySg/s1600/linkedin.png)](http://it.linkedin.com/in/francescoazzola)

#Licence
```
Copyright 2012-2014 Francesco Azzola (Surviving with Android)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[Yahoo! Weather]:http://developer.yahoo.com/weather/
[Openweathermap]:http://openweathermap.org/


    
