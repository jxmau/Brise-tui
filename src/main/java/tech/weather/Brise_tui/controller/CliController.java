package tech.weather.Brise_tui.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import tech.weather.Brise_tui.service.AirPollutionService;
import tech.weather.Brise_tui.service.CliHelpService;
import tech.weather.Brise_tui.service.HelloService;
import tech.weather.Brise_tui.service.WeatherService;
import tech.weather.Brise_tui.settings.Settings;

@ShellComponent
public class CliController {

    private final WeatherService weatherService;
    private final CliHelpService cliHelpService;
    private final HelloService helloService;
    private final AirPollutionService airPollutionService;
    private final Settings settings;

    public CliController(WeatherService weatherService, CliHelpService cliHelpService, HelloService helloService, AirPollutionService airPollutionService, Settings settings) {
        this.weatherService = weatherService;
        this.cliHelpService = cliHelpService;
        this.helloService = helloService;
        this.airPollutionService = airPollutionService;
        this.settings = settings;
    }

    // Invoke Hello app
    @ShellMethod("Fetch main weather informations from a major city around the world.")
    public String hello(String city,
                        @ShellOption(defaultValue = "N/A") String country,
                        @ShellOption(defaultValue = "N/A") String state){
        return helloService.fetchHelloInformations(city, country, state);
    }

    // Invoke weather app
    @ShellMethod("Feath weather informations from a city\n-s to save city informations.")
    public String weather(@ShellOption(defaultValue = "N/A") String city,
                        @ShellOption(defaultValue = "N/A") String country,
                        @ShellOption(defaultValue = "N/A") String state,
                        @ShellOption(defaultValue = "N/A") String command){
        if (city.equals("N/A")){
            return weatherService.fetchWeatherInfosForSavedCity();
        } else {
            return weatherService.fetchWeatherInformations(city, country, state, command);
        }
    }

    // Invoke Air Pollution app
    @ShellMethod("Fetch air quality informations from a major city around the world.\n-s to save city informations.")
    public String air(@ShellOption(defaultValue = "N/A") String city,
                      @ShellOption(defaultValue = "N/A") String country,
                      @ShellOption(defaultValue = "N/A") String state,
                      @ShellOption(defaultValue = "N/A") String command){
        return switch (city) {
            case "-names" -> cliHelpService.airNames();
            case "-colours" -> cliHelpService.airColours();
            case "N/A" -> airPollutionService.fetchAirPollutionInfosForSavedCity();
            default -> airPollutionService.fetchAirPollutionInformations(city, country, state, command);
        };
    }

    @ShellMethod("Get help")
    public String help(){
        return cliHelpService.help();
    }

    @ShellMethod("Modify your AppId Key")
    public String key(String key){
        return settings.modifyAppId(key) + "Please, restart the application.";
    }


}
