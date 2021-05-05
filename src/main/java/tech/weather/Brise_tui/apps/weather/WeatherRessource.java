package tech.weather.Brise_tui.apps.weather;

import org.springframework.stereotype.Component;
import tech.weather.Brise_tui.apps.tools.MetricConverter;
import tech.weather.Brise_tui.apps.tools.WindDirection;

@Component
public class WeatherRessource {

    private final MetricConverter metricConverter;
    private final WindDirection windDirection;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public WeatherRessource(MetricConverter metricConverter, WindDirection windDirection) {
        this.metricConverter = metricConverter;
        this.windDirection = windDirection;
    }

    // Get temp
    public String getTemp(Double tempKelvin){
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
    public String getWindSpeed(Double speed){
        return metricConverter.convertWindMeterPerSecond(speed) + " kph";
    }

    // Get Wind Direction
    public String getWindDirection(Double degree){
        return degree + " " + windDirection.getWindOriginAbv(degree);
    }
}
