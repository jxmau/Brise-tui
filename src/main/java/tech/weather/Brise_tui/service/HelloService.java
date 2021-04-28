package tech.weather.Brise_tui.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.weather.Brise_tui.application.HelloApplication;
import tech.weather.Brise_tui.settings.Settings;

import java.util.Map;

@Service
public class HelloService {

    private final HelloApplication helloApplication;
    private final RestTemplate restTemplate;
    private final String appId;
    private final Settings settings;

    public HelloService(HelloApplication helloApplication, RestTemplateBuilder restTemplateBuilder, Settings settings) {
        this.helloApplication = helloApplication;
        this.restTemplate = restTemplateBuilder.build();
        this.settings = settings;
        this.appId = settings.GetAppId();
    }

    // Fetch Hello Informations
    public String fetchHelloInformations(String city, String country, String state){

        Map<String, Map<String, Object>> jsonResponse =
                restTemplate.getForObject(
                        urlAssemblerForWeather(city, country, state),
                        Map.class);


        if (jsonResponse != null) {
            if (!state.equals("N/A")){
                return "\nHello from " + city + ", " + state + "\n" + helloApplication.generateInformations(jsonResponse);
            } else {
                return "\nHello from " + city + "\n" + helloApplication.generateInformations(jsonResponse);
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
