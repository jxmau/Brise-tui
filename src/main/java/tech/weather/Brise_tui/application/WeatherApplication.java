package tech.weather.Brise_tui.application;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.weather.Brise_tui.tools.MetricConverter;
import tech.weather.Brise_tui.tools.WindDirection;

import java.util.List;
import java.util.Map;

@Component
public class WeatherApplication {

    private final MetricConverter metricConverter;
    private final WindDirection windDirection;

    public WeatherApplication(MetricConverter metricConverter, WindDirection windDirection) {
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

    // generate conditions informations
    private String weatherInformations(Map<String, Object> weatherInfos){
        String condition = (String) weatherInfos.get("description");
        return "Weather conditions : " + condition + ".\n";
    }

    // generate air informations
    private String airInformations(Map<String, Object> airInfos){
        Double tempKelvin = (Double) airInfos.get("temp");
        Integer humidity = (Integer) airInfos.get("humidity");
        Integer pressure = (Integer) airInfos.get("pressure");
        return "Air conditions : \n" +
                "Temperature : " + metricConverter.convertKelvinToCelcius(tempKelvin) + "°C  | " +
                "Humidity : " + humidity + "%\n" +
                "atmospheric pressure : " + pressure + " hPa\n";
    }

    // Generate wind informations
    private String windInformations(Map<String, Object> windInfos){
        Double speed = Double.valueOf(windInfos.get("speed").toString());
        Double degree = Double.valueOf(windInfos.get("deg").toString());
        return "Wind conditions : \n" +
                "Speed : " + metricConverter.convertWindMeterPerSecond(speed) + " kph  | " +
                "Degree : " + degree + " " + windDirection.getWindOriginAbv(degree) + "\n";

    }

}
