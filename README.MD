

<h2>Brise-tui </h2>
<b> Current Version = V 0.9 </b> <br> <br>
<b>Brise-tui</b> is a simple Terminal User Interface using OpenWeatherMap's Current Weather and Air Pollution APIs.

 <h3>Ressources</h3>
<h5>Dependencies and Libraries Used</h5>
- Spring Shell -> For shell development.
- Rest Template -> For API enquiries.
- Json-simple -> For settings files manipulation.

<h5> APIs </h5>
- Current Weather <br>
- Air Pollution

<h3>Disclaimer</h3>
- This TUI use OpenWeatherMap's APIs, but it is not related in any way to it.
- This TUI is for personal use only.
- Informations fetched by the TUI are for indications purpose only.

<h2>Prerequisites</h2>

<h5> OpenWeatherMap appId key </h5>

Brise-TUI uses apis from OPM accessible in the free plan. <br>
While free, a key is required for each request from the TUI, that's why that the first time you'll launch Brise, you'll be asked to enter a key. <br>
Don't worry if you've made a mistake, you can modify your key with the "key" command below.

<h2>Commands List </h2>

<h5> Hello! </h5>
- hello [city] [country] [state] <br>
Hello is a quick and simple command that will fetch simple weather information as the weather condition, the temperature,
the direction and speed of the wind and the humidity level.

<h5> Weather Information </h5>
- weather [city] [country] [state] <br>
Similar to the hello command, the weather command fetched more information and let you save your choice in a setting file by adding "-s" at the end of the command line. <br>
Once you've saved a city, you can just type "weather" to fetch the information of your city.

<h5> Air Pollution Information </h5>
- air [city] [country] [state] <br>
Air is the equivalent of weather for the air pollution. It works the same way, the city saved while using the weather command, works also for the air command.
<br>
You can know more about the pollutants listed by typing "air -pollutants", or about their toxicity with "air -colours".

<h5> Key </h5>
- key [appId key] <br>
This command is to be used when you want to change the AppId key from OpenWeatherMap. <br>
Once you've used this command, you'll be asked to restart the TUI to load the correct key. <br>

<h5> Help </h5>
- help <br>
The help command will list every command and sub-commands.

<h2> License Information </h2>

This work is licensed under the Creative Commons Attribution-NonCommercial 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.


<br> <br>Made with ❤ and eun ch'tio peu d'crème fraiche!
