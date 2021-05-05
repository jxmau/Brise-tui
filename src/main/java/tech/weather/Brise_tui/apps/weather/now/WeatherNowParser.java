package tech.weather.Brise_tui.apps.weather.now;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import tech.weather.Brise_tui.apps.air.now.AirPollutionAssembler;
import tech.weather.Brise_tui.apps.hello.HelloAssembler;
import org.springframework.web.client.RestTemplate;
import tech.weather.Brise_tui.apps.tools.GPSCoordParser;
import tech.weather.Brise_tui.settings.Settings;


import java.util.Map;

@Service
public class WeatherNowParser {

    private final HelloAssembler helloAssembler;
    private final WeatherNowAssembler weatherNowAssembler;
    private final AirPollutionAssembler airPollutionAssembler;
    private final RestTemplate restTemplate;
    private final GPSCoordParser GPSCoordParser;
    private final Settings settings;
    private final String appId;

    public WeatherNowParser(Settings settings, HelloAssembler helloAssembler, WeatherNowAssembler weatherNowAssembler, AirPollutionAssembler airPollutionAssembler, RestTemplateBuilder restTemplateBuilder, GPSCoordParser GPSCoordParser) {
        this.helloAssembler = helloAssembler;
        this.weatherNowAssembler = weatherNowAssembler;
        this.airPollutionAssembler = airPollutionAssembler;
        this.restTemplate = restTemplateBuilder.build();
        this.GPSCoordParser = GPSCoordParser;
        this.settings = settings;
        this.appId = settings.getAppId();
    }



    // Fetch Json For city
    public String fetchWeatherInformations(String city, String country, String state, String command) {
        if (country.equals("-s") || state.equals("-s")){
            command = "-s";
            country = "N/A";
            state = "N/A";
        }

        Map<String, Map<String, Object>> jsonResponse =
                restTemplate.getForObject(
                        urlAssemblerForWeather(city, country, state),
                        Map.class);

        if (command.equals("-s")) {
            Map<String, String> coordinates = GPSCoordParser.jsonParserToGetCoordInfos(jsonResponse);
            settings.saveCoord(city, country, state, coordinates.get("latitude"), coordinates.get("longitude"));
        }

        return generateBulletin(city, state, jsonResponse);
    }


    //Fetch weather informations for saved city
    public String fetchWeatherInfosForSavedCity(){
        Map<String, String> coord = settings.getCoord();
        String city = coord.get("city");
        String state = coord.get("state");
        Map<String, Map<String, Object>> jsonResponse =
                restTemplate.getForObject(
                        urlAssemblerForWeather(city, coord.get("country"), state),
                        Map.class);


        return generateBulletin(city, state, jsonResponse);
    }

    // Generate bulletin
    public String generateBulletin(String city, String state, Map<String, Map<String, Object>> jsonResponse ){
        if (city != null) {
            if (!state.equals("N/A")) {
                return "\nWeather bulletin for " + cityParser(city) + ", " + state + "\n" + weatherNowAssembler.generateInformations(jsonResponse);
            } else {
                return "\nWeather bulletin for " + cityParser(city) + "\n" + weatherNowAssembler.generateInformations(jsonResponse);
            }
        } else {
            return "Sorry, the city couldn't be find!";
        }

    }


    private String urlAssemblerForWeather(String city, String country, String state){
        if (!country.equals("N/A") && state.equals("N/A")){

            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country
                    + "&appid=" + appId;
        } else if (!country.equals("N/A") && !state.equals("N/A")){

            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + state
                    + "," + country + "&appid=" + appId;
        } else {
            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + appId;
        }

    }

    // Will replace "+" with space
    private String cityParser(String city){
        return city.replace("+", " ");

    }



}
