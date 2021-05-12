package tech.weather.Brise_tui.apps.weather.today;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tech.weather.Brise_tui.settings.Settings;

import java.util.Map;

@Component
public class WeatherBulletinRequest {

    private final Settings settings;
    private final RestTemplate restTemplate;
    private final WeatherBulletinParser weatherBulletinParser;

    public WeatherBulletinRequest(Settings settings, RestTemplateBuilder restTemplateBuilder, WeatherBulletinParser weatherBulletinParser) {
        this.settings = settings;
        this.restTemplate = restTemplateBuilder.build();
        this.weatherBulletinParser = weatherBulletinParser;
    }

    //// TODAY REQUEST

    // Fetch saved informations
    public String weatherTodayForSavedCity(){
        Map<String, String> location = settings.getCoord();
        return generateBulletinForToday(location.get("city"), location.get("country"), location.get("state"), "N/A");
    }

    // Fetch Informations
    public String weatherTodayForCity(String city, String country, String state, String command){
        if (country.equals("-s") || state.equals("-s")){
            command = "-s";
            country = "N/A";
            state = "N/A";
        }

        return generateBulletinForToday(city, country, state, command);
    }

    // Fetch Json Response
    private String generateBulletinForToday(String city, String country, String state, String command){
        Map<String, Map<String, Object>> jsonResponse =
                restTemplate.getForObject(urlAssembler(city, state, country), Map.class);

        if (command.equals("-s")) {
            Map<String, Object> cityMap = jsonResponse.get("city");
            Map<String, Object> coordinates = (Map<String, Object>) cityMap.get("coord");
            settings.saveCoord(city, country, state,
                    coordinates.get("lat").toString(), coordinates.get("lon").toString());
        }

        return weatherBulletinParser.jsonParserForToday(jsonResponse);
    }



    ////// TOMORROW REQUEST


    // Fetch saved informations
    public String weatherTomorrowForSavedCity(){
        Map<String, String> location = settings.getCoord();
        return generateBulletinForTomorrow(location.get("city"), location.get("country"), location.get("state"), "N/A");
    }

    // Fetch Informations
    public String weatherTomorrowForCity(String city, String country, String state, String command){
        if (country.equals("-s") || state.equals("-s")){
            command = "-s";
            country = "N/A";
            state = "N/A";
        }


        return generateBulletinForTomorrow(city, country, state, command);
    }

    // Fetch Json Response
    private String generateBulletinForTomorrow(String city, String country, String state, String command){
        Map<String, Map<String, Object>> jsonResponse =
                restTemplate.getForObject(urlAssembler(city, state, country), Map.class);

        if (command.equals("-s")) {
            Map<String, Object> cityMap = jsonResponse.get("city");
            Map<String, Object> coordinates = (Map<String, Object>) cityMap.get("coord");
            settings.saveCoord(city, country, state,
                    coordinates.get("lat").toString(), coordinates.get("lon").toString());
        }

        return weatherBulletinParser.jsonParserForTomorrow(jsonResponse);
    }

    // URL assembler
    private String urlAssembler(String city, String state, String country){
        if (!country.equals("N/A") && state.equals("N/A")){

            return "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country
                    + "&appid=" + settings.getAppId();
        } else if (!country.equals("N/A") && !state.equals("N/A")){

            return "htttps://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + state
                    + "," + country + "&appid=" + settings.getAppId();
        } else {
            return "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + settings.getAppId();
        }
    }


}
