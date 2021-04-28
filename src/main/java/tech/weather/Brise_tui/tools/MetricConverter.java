package tech.weather.Brise_tui.tools;

import org.springframework.stereotype.Service;
import java.lang.Math;

@Service
public class MetricConverter {

    public long convertKelvinToCelcius(Double tempInKelvin){
        return Math.round( tempInKelvin - 273.16);
    }

    public Double convertWindMeterPerSecond(Double speedInMeterPerSecond) {
        return speedInMeterPerSecond * 3.6;
    }
}
