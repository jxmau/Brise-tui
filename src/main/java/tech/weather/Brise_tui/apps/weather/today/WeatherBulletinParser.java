package tech.weather.Brise_tui.apps.weather.today;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class WeatherBulletinParser {

    private final WeatherBulletinAssembler weatherBulletinAssembler;

    public WeatherBulletinParser(WeatherBulletinAssembler weatherBulletinAssembler) {
        this.weatherBulletinAssembler = weatherBulletinAssembler;
    }

    // Will parse the json and create a map will today's weather informations
    public String jsonParserForToday(Map<String, Map<String, Object>> jsonResponse){
        // Parsing of the city to get the timezone information
        // and to take the list containing all forecasts' map
        Map<String, Object> cityInfos = (Map<String, Object>) jsonResponse.get("city");
        List<Map<String, Object>> forecastInfo = (List<Map<String, Object>>) jsonResponse.get("list");

        // Will iniate the map using the first Map in the list
        Map<String, Object> map = initizalizeMainInformations((Map<String, Object>) forecastInfo.get(0));

        // Scale the time zone information on the location
        Integer timeZone = (Integer) cityInfos.get("timezone");
        LocalDate TODAY = LocalDateTime.now(Clock.systemUTC()).plusSeconds(timeZone).toLocalDate();


        for (Map<String, Object> forecast : forecastInfo) {
            if (getForecastTime((Integer) forecast.get("dt"), timeZone).isBefore(TODAY.plusDays(1))){
                actualizationMap(forecast, map);
            } else {
                break;
            }
        }
        return weatherBulletinAssembler.generateBulletin(map);
    }

    // Will parse the json and create a map will today's weather informations
    public String jsonParserForTomorrow(Map<String, Map<String, Object>> jsonResponse){
        // Parsing of the city to get the timezone information
        // and to take the list containing all forecasts' map
        Map<String, Object> cityInfos = (Map<String, Object>) jsonResponse.get("city");
        List<Map<String, Object>> forecastInfo = (List<Map<String, Object>>) jsonResponse.get("list");

        // To avoid getting the information of today, and getting stuck with it,
        // the token system permit to generate the Map
        // when the script reach the first Map with tomorrow's information
        Map<String, Object> map = new HashMap<>();
        int token = 0;

        // Scale the time zone information on the location
        Integer timeZone = (Integer) cityInfos.get("timezone");
        LocalDate TODAY = LocalDateTime.now(Clock.systemUTC()).plusSeconds(timeZone).toLocalDate();

        for (Map<String, Object> forecast : forecastInfo) {
            if (getForecastTime((Integer) forecast.get("dt"), timeZone).isAfter(TODAY)){
                if (token == 0) {
                    map = initizalizeMainInformations(forecastInfo.get(0));
                    token = 1;
                }
                actualizationMap(forecast, map);
            } else if (getForecastTime((Integer) forecast.get("dt"), timeZone).isAfter(TODAY.plusDays(1))) {
                break;
            }
        }
        return weatherBulletinAssembler.generateBulletin(map);
    }

    // dt corresponds to the unix time of the forecast
    private LocalDate getForecastTime(Integer dt, Integer timeZone){
        return LocalDateTime.of(1970, 01, 01, 0, 0, 0)
                        .plusSeconds(dt).plusSeconds(timeZone).toLocalDate();
    }

    private Map<String, Object> initizalizeMainInformations(Map<String, Object> mapReceived){
        Map<String, Object> main = (Map<String, Object>) mapReceived.get("main");
        Map<String, Object> wind = (Map<String, Object>) mapReceived.get("wind");
        Map<String, Object> map = new HashMap<>();
        map.put("tempMIN", main.get("temp"));
        map.put("tempMAX", main.get("temp"));
        map.put("humidityMIN", main.get("humidity"));
        map.put("humidityMAX", main.get("humidity"));
        map.put("pressureMIN", main.get("pressure"));
        map.put("pressureMAX", main.get("pressure"));
        map.put("windSpeedMIN", wind.get("speed"));
        map.put("windSpeedMAX", wind.get("speed"));
        return map;
    }

    private void actualizationMap(Map<String, Object> mapReceived, Map<String, Object> map){
        Map<String, Object> main = (Map<String, Object>) mapReceived.get("main");
        Map<String, Object> wind = (Map<String, Object>) mapReceived.get("wind");

        if ((Double) main.get("temp") < (Double) map.get("tempMIN")){
            map.put("tempMIN", main.get("temp"));
        } else if ((Double) main.get("temp") > (Double) map.get("tempMIN")){
            map.put("tempMAX",  main.get("temp"));
        }

        if ((Integer) main.get("humidity") < (Integer) map.get("humidityMIN")){
            map.put("humidityMIN",  main.get("humidity"));
        } else if ((Integer) main.get("humidity") > (Integer) map.get("humidityMAX")){
            map.put("humidityMAX",  main.get("humidity"));
        }

        if ((Integer) main.get("pressure") < (Integer) map.get("pressureMIN")){
            map.put("pressureMIN",  main.get("pressure"));
        } else if ((Integer) main.get("pressure") > (Integer) map.get("pressureMAX")){
            map.put("pressureMAX",  main.get("pressure"));
        }

        if ((Double) wind.get("speed") < (Double) map.get("windSpeedMIN")){
            map.put("windSpeedMIN",  wind.get("speed"));
        } else if ((Double) wind.get("speed") > (Double) map.get("windSpeedMAX")){
            map.put("windSpeedMAX",  wind.get("speed"));
        }

    }
}
