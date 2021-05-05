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

    // Fetch saved informations
    public String weatherTodayForSavedCity(){
        Map<String, String> location = settings.getCoord();
        return generateBulletinForToday(location.get("city"), location.get("country"), location.get("state"));
    }

    // Fetch Informations
    public String weatherTodayForCity(String city, String country, String state){
        return generateBulletinForToday(city, country, state);
    }

    // Fetch Json Response
    private String generateBulletinForToday(String city, String country, String state){
        Map<String, Map<String, Object>> jsonResponse =
                restTemplate.getForObject(urlAssembler(city, state, country), Map.class);

        return weatherBulletinParser.jsonParserForToday(jsonResponse);
    }

    // Fetch saved informations
    public String weatherTomorrowForSavedCity(){
        Map<String, String> location = settings.getCoord();
        return generateBulletinForTomorrow(location.get("city"), location.get("country"), location.get("state"));
    }

    // Fetch Informations
    public String weatherTomorrowForCity(String city, String country, String state){
        return generateBulletinForTomorrow(city, country, state);
    }

    // Fetch Json Response
    private String generateBulletinForTomorrow(String city, String country, String state){
        Map<String, Map<String, Object>> jsonResponse =
                restTemplate.getForObject(urlAssembler(city, state, country), Map.class);

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
