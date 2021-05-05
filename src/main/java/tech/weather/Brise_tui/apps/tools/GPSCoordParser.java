package tech.weather.Brise_tui.apps.tools;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FetchGPSCoordinates {

    public Map<String, String> fetchCoordinates(Map<String, Map<String, Object>> jsonResponse){
        Map<String, Object> coordinatesReceived = jsonResponse.get("coord");

        Map<String, String> coordinates = new HashMap<>();
        coordinates.put("longitude",  coordinatesReceived.get("lon").toString());
        coordinates.put("latitude",  coordinatesReceived.get("lat").toString());
        return coordinates;
    }
}
