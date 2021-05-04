package tech.weather.Brise_tui.apps.help;

import org.springframework.stereotype.Service;

@Service
public class CliHelpService {

    public String help(){
        return """
                hello <cityname>                                       -> Fetch quick weather informations from a major city.
                now <cityname> <country ISO code> <state ISO code>     -> Fetch complete weather informations. add -s to save
                air <city name> <country ISO code> <state ISO code>    -> Fetch air quality informations. add -s to save.
                    -names      -> Give you all pollutants' names.
                    -limits     -> Give the toxicity limits.
                help                                                   -> Gives you the informations about the tui's commands.
                <Pro Tip> If your city is made of multiple words like New York, replace the space by "+" : New+York !
                """;

    }

    public String airNames(){
        return """
                Molecules :\s
                CO -> Carbon Monoxyde     | NO -> Nitric Oxyde
                NO2 -> Nitrogen Dioxide   | O3 -> Ozone
                SO2 -> Sulfur Dioxide     | NH3 -> Ammonia
                Particulates :
                PM 2.5 & 10 -> The number corresponds to the diameter of the particulates.
                """;

    }

    public String airLimits(){
        return """
        LIMITS : (According to WHO)
        Molecules :
        CO -> RED : above 8 hours exposure (10310 μg/m3) - ORANGE : above 24 hours exposure (6874 μg/m3)
        NO -> N/A
        NO2 -> RED : above annual mean exposure (40 μg/m3)
        O3 -> RED : above annual mean exposure (100 μg/m3)
        SO2 -> RED : above 24 hours mean exposure (20 μg/m3)
        NH3 -> N/A
        Particulates :
        PM 2.5 -> RED : above 24 hours mean exposure (10 μg/m3) - ORANGE : above annual mean exposure (25 μg/m3)
        PM 10 -> RED : above 24 hours mean exposure (20 μg/m3) - ORANGE : above annual mean exposure (50 μg/m3)s""";
    }
}
