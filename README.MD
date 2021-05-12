

<h2>Brise-tui </h2>
<b> Current Version = V 0.9.2 </b> <br> <br>
<b>Brise-tui</b> is a simple Terminal User Interface using OpenWeatherMap's Current Weather and Air Pollution APIs.

 <h3>Ressources</h3>
<h5>Dependencies and Libraries Used</h5>
- Spring Shell -> For shell development. <br>
- Rest Template -> For API enquiries. <br>
- Json-simple -> For settings file manipulation. <br>

<h5> APIs </h5>

- Current Weather <br>
- Air Pollution <br>
- 5 days weather forecast

<h3>Disclaimer</h3>
- This TUI use OpenWeatherMap's APIs, but it is not related in any way to it. <br>
- This TUI is for personal use only. <br>
- Information fetched by the TUI are for indications purpose only. <br>

<h2>Prerequisites</h2>

<h5> OpenWeatherMap appId key </h5>

Brise-TUI uses apis from OPM accessible in the free plan. <br>
While free, a key is required for each request from the TUI, that's why that the first time you'll launch Brise, you'll be asked to enter a key. <br>
Don't worry if you've made a mistake, you can modify your key with the "key" command below.

<h2> Known Issues </h2>

* If the settings Json file doesn't exist, the app will start, creating it and asking for your appId key, but then crash. You'll need to restart the application to use it, but the key will be saved.

<h2>Customization</h2>

<h6> Saving a city </h6>

Brise allows you the possibility to save a city in your settings file. To save your city, you'll just need to add "-s" at the end of your command line. <br>

<h2>Commands List </h2>

<h5> Informations </h5>
As one would know, a city can have a multiple words name like San Francisco or Saint Petersburg. To avoid an error, please, replace the space by a "+". <br>
Example : New York -> New+York

<h5> Hello </h5>

```hello <city name> <country code> <state code>``` <br> <br>
Hello is a quick and simple command that will fetch simple weather information as the weather condition, the temperature,
the direction and speed of the wind and the humidity level.<br> <br>
/!\ Please note that you cannot save a city or fetch a save city using this command. /!\

<h5> Weather Information </h5>

<h6> Now </h6>

````weather now <city name> <country code> <state code>````

This command is quite similar to hello command but will get you more information in a more formal way. <br>

* Example <br>
```
 Brise > weather now Washington US DC
Weather bulletin for Washington, DC
Current Weather Condition : broken clouds
Air Conditions :
    Temperature : 17.3°C, but feels like 17.5°C.
    Atmospheric Pressure : 1005 hPa | Humidity : 94
Wind Conditions :
    Speed : 7.42 kph | Degree 200.0 S
```

<h6> Today </h6>

````weather today <city name> <country code> <state code>````

This will fetch you the forecast for the same day.
* Example <br>
```
 Brise > weather today Caen
WEATHER
Temperature : 4.7°C - 10.8°C
Humidity    :    51 - 79
Pressure    :  1012 - 1013 hPa
WIND
Speed   : 12.78 kph - 28.08 kph
```

<h6> Tomorrow </h6>

````weather tomorrow <city name> <country code> <state code>````

This will fetch you the forecast for the next day.
* Example <br>
```
 Brise > weather tomorrow Montréal
WEATHER
Temperature : 4.4°C - 5.3°C
Humidity    :    32 - 91
Pressure    :  1003 - 1015 hPa
WIND
Speed   : 3.35 kph - 19.26 kph
```

<h5> Current Air Pollution Information </h5>

<h6> Now </h6>

````air <city name> <country code> <state code>````

This will get you the current air pollution information of a city.
* Example <br>
```
 Brise > air Paris
 > Molecules :
 CO : 211.95 μg/m3 | NO : 0.73 μg/m3
 NO2 : 2.27 μg/m3  | O3 : 70.81 μg/m3
 SO2 : 2.09 μg/m3 | NH3 : 0.8 μg/m3
 > Particulates :
 PM2.5 : 0.77 μg/m3 | PM10 : 1.17 μg/m3

> Air Quality : Good
```

<h6>sub-commands</h6>

``air -names  -> Will fetch you all pollutants names.``<br>
``air -limits -> Will fetch you the colours code and limits according to the WHO.``

<h5> Key </h5>

```key <appId key>``` <br> <br>
This command is to be used when you want to change the AppId key from OpenWeatherMap. <br>
Once you've used this command, you'll be asked to restart the TUI to load the correct key. <br>

<h5> Help </h5>

```help```

The help command will list every command and sub-commands.

<h2> License Information </h2>

This work is licensed under the Creative Commons Attribution-NonCommercial 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.


<br> <br>Made with ❤ and eun ch'tio peu d'crème fraiche!
