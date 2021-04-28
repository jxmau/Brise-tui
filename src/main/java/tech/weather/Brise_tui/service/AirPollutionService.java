package tech.weather.Brise_tui.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.weather.Brise_tui.application.AirPollutionApplication;
import tech.weather.Brise_tui.service.ressource.FetchGPSCoordinates;
import tech.weather.Brise_tui.settings.Settings;

import java.util.HashMap;
import java.util.Map;

@Component
public class AirPollutionService {

    private final AirPollutionApplication airPollutionApplication;
    private final RestTemplate restTemplate;
    private final Settings settings;
    private final String appId;
    private final FetchGPSCoordinates fetchGPSCoordinates;

    public AirPollutionService(AirPollutionApplication airPollutionApplication, RestTemplateBuilder restTemplateBuilder, Settings settings, FetchGPSCoordinates fetchGPSCoordinates) {
        this.airPollutionApplication = airPollutionApplication;
        this.restTemplate = restTemplateBuilder.build();
        this.settings = settings;
        this.appId = settings.GetAppId();
        this.fetchGPSCoordinates = fetchGPSCoordinates;
    }

    public String fetchAirPollutionInfosForSavedCity(){
        Map<String, String> coord = settings.getCoord();

        return "\nAir Pollutions of " + coord.get("city") + generateBulletin(coord);
    }

    // Fetch Air Informations
    public String fetchAirPollutionInformations(String city, String country, String state, String command) {
        if (country.equals("-s") || state.equals("-s")){
            command = "-s";
            state = "N/A";
            country = "N/A";
        }

            // Fetch city's gps coordinates
            Map<String, Map<String, Object>> jsonResponseForCoordinates =
                    restTemplate.getForObject(
                            urlAssemblerForWeather(city, country, state),
                            Map.class);
        Map<String, String> coordinates = fetchGPSCoordinates.fetchCoordinates(jsonResponseForCoordinates);


        if (command.equals("-s")) {
            settings.saveCoord(city, country, state, coordinates.get("latitude"), coordinates.get("longitude"));
        }

         return generateBulletin(coordinates);
    }



    public String generateBulletin(Map<String, String> coordinates){
        Map<String, Map<String, Object>> jsonResponseForAirPollution =
                restTemplate.getForObject(
                        "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + coordinates.get("latitude")
                                + "&lon=" + coordinates.get("longitude") + "&appid=" + appId
                        , Map.class);
        return airPollutionApplication.generateInformations(jsonResponseForAirPollution);
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

}
