package tech.weather.Brise_tui.apps.tools;

import org.springframework.stereotype.Service;

@Service
public class WindDirection {

    public String getWindOrigin(Double windDirection) {
        if (windDirection > 337.5 || windDirection < 22.5) {
            return "North";
        } else if (windDirection >= 22.5 && windDirection < 67.5) {
            return "North-West";
        } else if (windDirection >= 65.5 && windDirection < 112.5) {
            return "West";
        } else if (windDirection >= 112.5 && windDirection < 157.5) {
            return "South-West";
        } else if (windDirection >= 157.5 && windDirection < 202.5) {
            return "South";
        } else if (windDirection >= 202.5 && windDirection < 247.5) {
            return "South-East";
        } else if (windDirection >= 247.5 && windDirection < 292.5) {
            return "East";
        } else if (windDirection >= 292.5 && windDirection < 337.5) {
            return "North-East";
        } else {
            return "There's no wind";
        }

    }

    public String getWindOriginAbv(Double windDirection) {
        if (windDirection > 337.5 || windDirection < 22.5) {
            return "N";
        } else if (windDirection >= 22.5 && windDirection < 67.5) {
            return "NW";
        } else if (windDirection >= 65.5 && windDirection < 112.5) {
            return "W";
        } else if (windDirection >= 112.5 && windDirection < 157.5) {
            return "SW";
        } else if (windDirection >= 157.5 && windDirection < 202.5) {
            return "S";
        } else if (windDirection >= 202.5 && windDirection < 247.5) {
            return "SE";
        } else if (windDirection >= 247.5 && windDirection < 292.5) {
            return "E";
        }  else {
            return "NE";
        }

    }
}
