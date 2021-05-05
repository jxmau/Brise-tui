package tech.weather.Brise_tui.apps.weather.today;

import org.springframework.stereotype.Component;
import tech.weather.Brise_tui.apps.weather.WeatherRessource;

import java.util.Map;

@Component
public class WeatherBulletinAssembler {

    private final WeatherRessource weatherRessource;

    public WeatherBulletinAssembler(WeatherRessource weatherRessource) {
        this.weatherRessource = weatherRessource;
    }

    public String generateBulletin(Map<String, Object> weatherInformations){

        return """
                WEATHER
                Temperature : %s - %s
                Humidity    :    %s - %s 
                Pressure    :  %s - %s hPa
                WIND
                Speed   : %s - %s
                """.formatted(weatherRessource.getTemp((Double) weatherInformations.get("tempMIN")),
                weatherRessource.getTemp((Double) weatherInformations.get("tempMAX")),
                weatherInformations.get("humidityMIN"), weatherInformations.get("humidityMAX"),
                weatherInformations.get("pressureMIN"), weatherInformations.get("pressureMAX"),
                weatherRessource.getWindSpeed((Double) weatherInformations.get("windSpeedMIN")),
                weatherRessource.getWindSpeed((Double) weatherInformations.get("windSpeedMAX")));
    }
}
