/**
 * Created by jeff on 10/30/14.
 */
package observers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;

public class WeatherDataSource extends Observable {
    private String city;
    private String country;
    private double temperatureFahrenheit;
    private double pressure;

    public WeatherDataSource(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public double getTemperatureFahrenheit() {
        return this.temperatureFahrenheit;
    }

    public double getPressureMillibars() {
        return this.pressure;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public void update() {
        String weatherString = "";

        // Get the JSON for the given city from openweathermap.org.
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + this.city + "," + this.country);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                weatherString += inputLine;
            }
            in.close();
        } catch (Exception e) {
        }

        // I should use a JSON parsing library here, but it's not built into
        // Java SE yet (just Java EE), so I'm just totally hacking the thing.
        int startIndex = weatherString.indexOf("temp\":");
        if (startIndex >= 0) {
            startIndex += 6;
        }

        int endIndex = weatherString.indexOf(",", startIndex);
        String s = weatherString.substring(startIndex, endIndex);
        this.temperatureFahrenheit = 9.0 * (Double.parseDouble(s) - 273.15) / 5.0 + 32.0;

        startIndex = weatherString.indexOf("pressure\":");
        if (startIndex >= 0) {
            startIndex += 10;
        }
        endIndex = weatherString.indexOf(",", startIndex);
        s = weatherString.substring(startIndex, endIndex);
        this.pressure = Double.parseDouble(s);

        this.setChanged();
        this.notifyObservers();
        /*
            f = 9*(k - 273.15)/5 + 32

        {"coord":{"lon":-93.26,"lat":44.98},
           "sys":{"type":1,"id":1492,"message":0.1953,"country":"US","sunrise":1414759863,"sunset":1414796534},
       "weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],
          "base":"cmc stations",
          "main":{"temp":279.46,"pressure":1025,"humidity":60,"temp_min":278.15,"temp_max":280.15},
          "wind":{"speed":6.2,"deg":330,"gust":11.8},
        "clouds":{"all":90},
            "dt":1414714500,
            "id":5037649,
          "name":"Minneapolis",
           "cod":200}
        */
    }
}
