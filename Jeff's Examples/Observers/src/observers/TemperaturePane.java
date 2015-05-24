/**
 * Created by jeff on 10/30/14.
 */
package observers;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.Observable;
import java.util.Observer;

public class TemperaturePane extends GridPane implements Observer {
    private Label cityLabel;
    private Label temperatureLabel;
    private Label spinnerLabel;
    private int spinnerState;

    public TemperaturePane() {
        this.cityLabel = new Label();
        this.add(this.cityLabel, 0, 0);
        this.temperatureLabel = new Label();
        this.add(this.temperatureLabel, 0, 1);
        this.spinnerLabel = new Label();
        this.add(this.spinnerLabel, 0, 2);
        this.spinnerState = 0;
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof WeatherDataSource) {
            WeatherDataSource dataSource = (WeatherDataSource) observable;

            String city = dataSource.getCity();
            this.cityLabel.setText(city);

            String temperatureText = String.format("%.2f °F", dataSource.getTemperatureFahrenheit());
            this.temperatureLabel.setText(temperatureText);

            if (this.spinnerState % 2 == 0) {
                this.spinnerLabel.setText("-");
                System.out.println("-");
            } else {
                this.spinnerLabel.setText("|");
                System.out.println("|");
            }
            this.spinnerState++;
        }
    }
}
