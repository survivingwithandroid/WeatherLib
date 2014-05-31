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


At the moment the library supports [Yahoo! Weather], [Openweathermap]  and Weather Underground API as weather provider. 

## Version
1.3.1

## What's new
1. Add Weather Underground API support (Current Weather, Forecast, Astronomy, City search)
2. Standard HTTP client, based only on HttpConnection without Volley support
3. Add new features regarding OpenWeatherMap
4. Bug fixing

## App
To provide an example about how to use the libray this project contains a fully working app that shows weather information based on this library (see the demo module).

## Note
Please notice that some weather provider requires you to get a private API key before using their services, moreover some services aren't free. So if you want to use the Lib with these weather providers you should get your private KEY registering your account.


## Getting started
Before using this lib, you have to modify build.gradle adding these lines:

``` java
dependencies {    
    compile 'com.survivingwithandroid:weatherlib:1.3.2'
}
```

#Tutorial
If you want to know how to use the lib give a look at [wiki page]

#Eclipse users
The project is built using Android Studio, anyway if you want to use the lib with Eclipse you can download the library jar [here]

#Community
If you like there is a Community to talk about new features, suggest tips or report bugs. Please join:

[![](http://4.bp.blogspot.com/-Bfh2unbdc84/UcGqVJKdMwI/AAAAAAAAAOc/W4kGiTU-fYk/s1600/google_plus_58.png)](https://plus.google.com/communities/117946761543584564970)

#Credits 
*Author:* Francesco Azzola (JFrankie) ([survivingwithandroid@gmail.com](mailto:survivingwithandroid@gmail.com))

[![](http://4.bp.blogspot.com/-Bfh2unbdc84/UcGqVJKdMwI/AAAAAAAAAOc/W4kGiTU-fYk/s1600/google_plus_58.png)](http://www.google.com/+FrancescoAzzola)  [![](http://3.bp.blogspot.com/-_JSQStno9N8/UcGWEW7V9AI/AAAAAAAAAOM/_qFVUjIaySg/s1600/linkedin.png)](http://it.linkedin.com/in/francescoazzola)


#Contribute
The library source code is free so that everyone that wants to contribute to the library is welcome.If you want to contribute to the library you can use <a href="https://github.com/survivingwithandroid/WeatherLib">github</a> and pull a new request so that I can merge your code. If you find bugs in the code you can open an issue on github or contact me.
In case you use the library and you are satisfied with it I would like you could send some screenshots of the app you created. If someone wants to contribute freely to the library providing a nice logo I would be very happy. <br />
<br />

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
[wiki page]:https://github.com/survivingwithandroid/WeatherLib/wiki/Tutorial
[here]:https://github.com/survivingwithandroid/WeatherLib/tree/master/lib/release
    
