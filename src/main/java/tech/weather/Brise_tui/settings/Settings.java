package tech.weather.Brise_tui.settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class Settings {


    //Create Settings Json File
    public void createSettingsFile(){

        JSONObject settings = new JSONObject();
        settings.put("appId", "");
        settings.put("city", "");
        settings.put("country", "");
        settings.put("state", "");
        settings.put("latitude", "");
        settings.put("longitude", "");


        //Write JSON file
        try (FileWriter file = new FileWriter("settings.json")) {

            file.write(settings.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Fetch the coord informations
    public Map<String, String> getCoord(){
        try {
            JSONParser parser = new JSONParser();
            Map<String, String> settings = (Map<String, String>) parser.parse(new FileReader("settings.json"));
            Map<String, String> coord = new HashMap<>();
            coord.put("city", settings.get("city"));
            coord.put("state", settings.get("state"));
            coord.put("country", settings.get("country"));
            coord.put("latitude", settings.get("latitude"));
            coord.put("longitude", settings.get("longitude"));


            if (coord.get("city").length() > 0) {
                return coord;
            } else {
                throw new IllegalStateException("No city saved.");
            }
        } catch (FileNotFoundException e) {
            createSettingsFile();
            throw new IllegalStateException("Settings files cannot be found.");
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("There has been an issue");
        }
    }


    public String GetAppId(){
        try {
            JSONParser parser = new JSONParser();
            Map<String, String> settings = (Map<String, String>) parser.parse(new FileReader("settings.json"));
            String appId = settings.get("appId");
            if (appId.length() < 1){
                System.out.println("No appId saved. Please, enter an OpenWeatherMap appId Key. ");
                Scanner scanner = new Scanner(System.in);
                String newKey = scanner.nextLine();
                return modifyAppId(newKey);
            } else {
                return settings.get("appId");
            }

        } catch (FileNotFoundException e) {
            createSettingsFile();
            throw new IllegalStateException("Settings files cannot be found.");
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("There has been an issue");
        }
    }


    // Modify the AppId
    public String modifyAppId(String appId){
        JSONParser parser = new JSONParser();

        try {
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));
            settings.put("appId", appId);
            FileWriter file = new FileWriter("settings.json");
            file.write(settings.toJSONString());
            file.flush();
            return "Your key has been saved.";
        } catch (FileNotFoundException e) {
            createSettingsFile();
            throw new IllegalStateException("Settings files cannot be found.");
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("There has been an issue");
        }
    }

    public void saveCoord(String city, String country, String state, String latitude, String longitude){
        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));

            settings.put("city", city);
            if (!country.equals("-s") && !country.equals("N/A")) {
                settings.put("country", country);
            } else if (country.equals("N/A")){
                settings.put("country", "N/A");
            }
            if (!state.equals("-s") && !state.equals("N/A")) {
                settings.put("state", state);
            }else if (country.equals("N/A")){
                settings.put("state", "N/A");
            }
            settings.put("latitude", latitude);
            settings.put("longitude", longitude);

            FileWriter file = new FileWriter("settings.json");
            file.write(settings.toJSONString());
            file.flush();

        } catch (FileNotFoundException e) {
            createSettingsFile();
            throw new IllegalStateException("Settings files cannot be found.");
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("There has been an issue");
        }

    }
}
