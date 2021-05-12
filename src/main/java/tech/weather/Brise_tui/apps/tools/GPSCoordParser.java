package tech.weather.Brise_tui.apps.tools;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.weather.Brise_tui.settings.Settings;

import java.util.HashMap;
import java.util.Map;

@Service
public class GPSCoordParser {

    private final Settings settings;
    private final RestTemplate restTemplate;

    public GPSCoordParser(Settings settings, RestTemplateBuilder restTemplateBuilder) {
        this.settings = settings;
        this.restTemplate = restTemplateBuilder.build();
    }

    public Map<String, String> getCoordinates(String city, String country, String state) {

        Map<String, Map<String, Object>> jsonResponseForCoordinates =
                restTemplate.getForObject(
                        urlAssembler(city, country, state),
                        Map.class);

        Map<String, String> coordinates = jsonParserToGetCoordInfos(jsonResponseForCoordinates);
        return coordinates;
    }

    // Must remain public as the Weather Now API uses it when saving a city.
    public Map<String, String> jsonParserToGetCoordInfos(Map<String, Map<String, Object>> jsonResponse){
        Map<String, Object> coordinatesReceived = jsonResponse.get("coord");

        Map<String, String> coordinates = new HashMap<>();
        coordinates.put("longitude",  coordinatesReceived.get("lon").toString());
        coordinates.put("latitude",  coordinatesReceived.get("lat").toString());
        return coordinates;
    }


    private String urlAssembler(String city, String country, String state){
        if (!country.equals("N/A") && state.equals("N/A")){

            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country
                    + "&appid=" + settings.getAppId();
        } else if (!country.equals("N/A") && !state.equals("N/A")){

            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + state
                    + "," + country + "&appid=" + settings.getAppId();
        } else {
            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + settings.getAppId();
        }

    }


}
