package tech.weather.Brise_tui.apps.air;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;

@Component
public class AirPollutionAssembler {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";


    public String generateInformations(Map<String, Map<String, Object>> jsonResponse) {
        List<Map<String, Object>> jsonResponseList = (List<Map<String, Object>>) jsonResponse.get("list");
        Map<String, Object> airQualityInfos = jsonResponseList.get(0);
        Map<String, Object> airPollutantsReceived = (Map<String, Object>) airQualityInfos.get("components");
        Map<String, Double> airPollutants = new HashMap<>();
        for (String key : airPollutantsReceived.keySet()) {
            airPollutants.put(key, Double.valueOf(airPollutantsReceived.get(key).toString()));
        }
        Map<String, Integer> main = (Map<String, Integer>) airQualityInfos.get("main");
        return """
                %s
                > Air Quality : %s
                (type air -limits to know more about the colour code.)
                """.formatted(airPollutantsInformations(airPollutants), airQualityCondition(main.get("aqi")));
    }

    // Generate the pollutants bulletin
    // Variables are received as Double, except when equals as zero, then they are Integer.
    private String airPollutantsInformations(Map<String, Double> airPollutants) {
        return """
                 > Molecules :
                 CO : %s μg/m3 | NO : %s μg/m3
                 NO2 : %s μg/m3  | O3 : %s μg/m3 
                 SO2 : %s μg/m3 | NH3 : %s μg/m3 
                 > Particulates :
                 PM2.5 : %s μg/m3 | PM10 : %s μg/m3
                """.formatted(getCO(airPollutants.get("co")), airPollutants.get("no"),
                getNO2(airPollutants.get("no2")), getO3(airPollutants.get("o3")), getSO2(airPollutants.get("so2")),
                airPollutants.get("nh3"), getPM2_5(airPollutants.get("pm2_5")), getPM10(airPollutants.get("pm10")));

    }

    // get Air Quality Condition
    private String airQualityCondition(Integer condition) {
        switch (condition) {
            case 1: {
                return "Good";
            }
            case 2: {
                return "Moderate to Good";
            }
            case 3: {
                return "Moderate";
            }
            case 4: {
                return "Bad";
            }
            case 5: {
                return "Dangerous";
            }
            default: {
                return "Informations not available at the moment.";
            }
        }

    }

    // Get CO
    private String getCO(Double co) {
        if (co >= 10310) {
            return ANSI_RED + co + ANSI_RESET;
        } else if (co >= 6874) {
            return ANSI_YELLOW + co + ANSI_RESET;
        } else {
            return ANSI_GREEN + co + ANSI_RESET;
        }
    }


    // Get NO2
    private String getNO2(Double no2) {
        if (no2 >= 40) {
            return ANSI_RED + no2 + ANSI_RESET;
        } else {
            return ANSI_GREEN + no2 + ANSI_RESET;
        }
    }

    // Get O3
    private String getO3(Double o3) {
        if (o3 >= 100) {
            return ANSI_RED + o3 + ANSI_RESET;
        } else {
            return ANSI_GREEN + o3 + ANSI_RESET;
        }
    }

    // Get SO2 
    private String getSO2(Double so2) {
        if (so2 >= 29) {
            return ANSI_RED + so2 + ANSI_RESET;
        } else {
            return ANSI_GREEN + so2 + ANSI_RESET;
        }
    }

    // Get PM2_5
    private String getPM2_5(Double pm2_5) {
        if (pm2_5 >= 25) {
            return ANSI_RED + pm2_5 + ANSI_RESET;
        } else if (pm2_5 >= 10) {
            return ANSI_YELLOW + pm2_5 + ANSI_RESET;
        } else {
            return ANSI_GREEN + pm2_5 + ANSI_RESET;
        }
    }

    //
    private String getPM10(Double pm10) {
        if ( pm10 >= 50) {
            return ANSI_RED + pm10 + ANSI_RESET;
        } else if (pm10 >= 20) {
            return ANSI_YELLOW + pm10 + ANSI_RESET;
        } else {
            return ANSI_GREEN + pm10 + ANSI_RESET;
        }
    }
}
