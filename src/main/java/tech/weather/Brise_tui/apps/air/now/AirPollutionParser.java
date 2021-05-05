package tech.weather.Brise_tui.apps.air.now;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tech.weather.Brise_tui.apps.tools.GPSCoordParser;
import tech.weather.Brise_tui.settings.Settings;

import java.util.Map;

@Component
public class AirPollutionParser {

    private final AirPollutionAssembler airPollutionAssembler;
    private final RestTemplate restTemplate;
    private final Settings settings;
    private final String appId;
    private final GPSCoordParser GPSCoordParser;

    public AirPollutionParser(AirPollutionAssembler airPollutionAssembler, RestTemplateBuilder restTemplateBuilder, Settings settings, GPSCoordParser GPSCoordParser) {
        this.airPollutionAssembler = airPollutionAssembler;
        this.restTemplate = restTemplateBuilder.build();
        this.settings = settings;
        this.appId = settings.getAppId();
        this.GPSCoordParser = GPSCoordParser;
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
        // Fetch the coordinates of the city
        Map<String, String> coordinates = GPSCoordParser.getCoordinates(city, country, state);
        // Save the city's location infos
        if (command.equals("-s")) {
            settings.saveCoord(city, country, state, coordinates.get("latitude"), coordinates.get("longitude"));
        }

         return generateBulletin(coordinates);
    }


    private String generateBulletin(Map<String, String> coordinates){
        Map<String, Map<String, Object>> jsonResponseForAirPollution =
                restTemplate.getForObject(
                        "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + coordinates.get("latitude")
                                + "&lon=" + coordinates.get("longitude") + "&appid=" + appId
                        , Map.class);
        return airPollutionAssembler.generateInformations(jsonResponseForAirPollution);
    }


}
