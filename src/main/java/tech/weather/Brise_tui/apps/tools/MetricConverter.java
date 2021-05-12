package tech.weather.Brise_tui.apps.tools;

import org.springframework.stereotype.Service;
import java.lang.Math;

@Service
public class MetricConverter {

    public Double convertKelvinToCelcius(Double tempInKelvin){
        return Math.round( (tempInKelvin - 273.16) * 10.0) / 10.0;
    }

    public Double convertWindMeterPerSecond(Double speedInMeterPerSecond) {
        return Math.round((speedInMeterPerSecond * 3.6) * 100.0) / 100.0;
    }
}
