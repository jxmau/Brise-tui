package tech.weather.Brise_tui.apps.weather.now;

import org.springframework.stereotype.Component;
import tech.weather.Brise_tui.apps.tools.MetricConverter;
import tech.weather.Brise_tui.apps.tools.WindDirection;

import java.util.List;
import java.util.Map;

@Component
public class WeatherNowAssembler {

    private final MetricConverter metricConverter;
    private final WindDirection windDirection;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public WeatherNowAssembler(MetricConverter metricConverter, WindDirection windDirection) {
        this.metricConverter = metricConverter;
        this.windDirection = windDirection;
    }

    public String generateInformations(Map<String, Map<String, Object>> jsonResponse) {
        Map<String, Object> airInfos = jsonResponse.get("main");
        Map<String, Object> windInfos = jsonResponse.get("wind");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) jsonResponse.get("weather");
        Map<String, Object> weatherInfos = weatherList.get(0);

        return """
                Current Weather Condition : %s
                %s%s
                """.formatted(weatherInfos.get("description"),airInformations(airInfos), windInformations(windInfos));
    }


    // generate air informations
    private String airInformations(Map<String, Object> airInfos){
        Double tempKelvin = (Double) airInfos.get("temp");
        Double feelsLike = (Double) airInfos.get("feels_like");
        Integer humidity = (Integer) airInfos.get("humidity");
        Integer pressure = (Integer) airInfos.get("pressure");

        return """
                Air Conditions :
                    Temperature : %s, but feels like %s.
                    Atmospheric Pressure : %s hPa | Humidity : %s
                """.formatted(getTemp(tempKelvin), getTemp(feelsLike),
                 pressure, humidity);


    }

    // Generate wind informations
    private String windInformations(Map<String, Object> windInfos){
        Double speed = Double.valueOf(windInfos.get("speed").toString());
        Double degree = Double.valueOf(windInfos.get("deg").toString());

        return """
                Wind Conditions :
                    Speed : %s | Degree %s
                """.formatted(getWindSpeed(speed), getWindDirection(degree));


    }

    // Get temp
    private String getTemp(Double tempKelvin){
        Double temp = metricConverter.convertKelvinToCelcius(tempKelvin);
        if (temp <= 0) {
            return ANSI_BLUE + temp + ANSI_RESET + "°C";
        } else if (temp >= 30.0) {
            return ANSI_RED + temp + ANSI_RESET + "°C";
        } else if (temp >= 20.0) {
            return ANSI_YELLOW + temp + ANSI_RESET + "°C";
        } else {
            return temp + "°C";
        }
    }

    // Get Wind Speed
    private String getWindSpeed(Double speed){
        return metricConverter.convertWindMeterPerSecond(speed) + " kph";
    }

    // Get Wind Direction
    private String getWindDirection(Double degree){
        return degree + " " + windDirection.getWindOriginAbv(degree);
    }

}
