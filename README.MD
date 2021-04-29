

<h2>Brise-tui </h2>
<b> Current Version = V 0.9.1 </b> <br> <br>
<b>Brise-tui</b> is a simple Terminal User Interface using OpenWeatherMap's Current Weather and Air Pollution APIs.

 <h3>Ressources</h3>
<h5>Dependencies and Libraries Used</h5>
- Spring Shell -> For shell development. <br>
- Rest Template -> For API enquiries. <br>
- Json-simple -> For settings file manipulation. <br>

<h5> APIs </h5>
- Current Weather <br>
- Air Pollution

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
- hello [city] [country] [state] <br>
Hello is a quick and simple command that will fetch simple weather information as the weather condition, the temperature,
the direction and speed of the wind and the humidity level.<br> <br>
/!\ Please note that you cannot save a city or fetch a save city using this command. /!\

<h5> Current Weather Information </h5>
- now [city] [country] [state] <br>
This command is quite similar to the previous one, but it will fetch more information for you. <br>
On the opposite, the now command allows you to save a city to access it quicker by just typing "now". You'll just need to type "-s" at the end like : "now Washington US DC -s" .


<h5> Current Air Pollution Information </h5>
- air [city] [country] [state] <br>
Air is the equivalent of the "now" command for air pollution, with the same features such as saving your city or just typing "air" to get your city's air information.
<br>
<h6>sub-commands</h6>
air -names -> Will fetch you all pollutants names.<br>
air -limits -> Will fetch you the colours code and limits according to the WHO.

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
