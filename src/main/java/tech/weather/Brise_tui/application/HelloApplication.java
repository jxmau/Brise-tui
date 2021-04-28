package tech.weather.Brise_tui.application;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.weather.Brise_tui.tools.MetricConverter;
import tech.weather.Brise_tui.tools.WindDirection;

import java.util.List;
import java.util.Map;

@Component
public class HelloApplication {

    private final MetricConverter metricConverter;
    private final WindDirection windDirection;

    public HelloApplication(MetricConverter metricConverter, WindDirection windDirection) {
        this.metricConverter = metricConverter;
        this.windDirection = windDirection;
    }

    public String generateInformations(Map<String, Map<String, Object>> jsonResponse) {
        Map<String, Object> airInfos = jsonResponse.get("main");
        Map<String, Object> windInfos = jsonResponse.get("wind");
         List<Map<String, Object>> weatherList = (List<Map<String, Object>>) jsonResponse.get("weather");
        Map<String, Object> weatherInfos = weatherList.get(0);


        return weatherInformations(weatherInfos) + airInformations(airInfos) + windInformations(windInfos);
    }

    // Generate Weather informations
    private String weatherInformations(Map<String, Object> weatherInfos){
        return "We have a " + weatherInfos.get("description") + "! \n";
    }

    // Generation Air Informations
    private String airInformations(Map<String, Object> airInfos){
        Double temp = (Double) airInfos.get("temp");
        Integer humidity = (Integer) airInfos.get("humidity");
        return "It's currently " + metricConverter.convertKelvinToCelcius(temp) +
                "°C, with " + humidity + "% of humidity.\n";
    }

    // Generation Wind Informations
    private String windInformations(Map<String, Object> windInfos) {
        Double windSpeed = Double.valueOf(windInfos.get("speed").toString());
        Double windDir = Double.valueOf(windInfos.get("deg").toString());

       if (windSpeed > 0) {
            return "The wind blows from the " + windDirection.getWindOrigin(windDir) +
                    " at " + metricConverter.convertWindMeterPerSecond(windSpeed) + "kph.\n";
        } else {
            return "There is no wind.\n";
        }
    }
}
