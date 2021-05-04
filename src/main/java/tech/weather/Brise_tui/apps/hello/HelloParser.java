package tech.weather.Brise_tui.apps.hello;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.weather.Brise_tui.settings.Settings;

import java.util.Map;

@Service
public class HelloParser {

    private final HelloAssembler helloAssembler;
    private final RestTemplate restTemplate;
    private final String appId;
    private final Settings settings;

    public HelloParser(HelloAssembler helloAssembler, RestTemplateBuilder restTemplateBuilder, Settings settings) {
        this.helloAssembler = helloAssembler;
        this.restTemplate = restTemplateBuilder.build();
        this.settings = settings;
        this.appId = settings.getAppId();
    }

    // Fetch Hello Informations
    public String fetchHelloInformations(String city, String country, String state){

        Map<String, Map<String, Object>> jsonResponse =
                restTemplate.getForObject(
                        urlAssemblerForWeather(city, country, state),
                        Map.class);


        if (jsonResponse != null) {
            if (!state.equals("N/A")){
                return "\nHello from " + city + ", " + state + "\n" + helloAssembler.generateInformations(jsonResponse);
            } else {
                return "\nHello from " + city + "\n" + helloAssembler.generateInformations(jsonResponse);
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

}
