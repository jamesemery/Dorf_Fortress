package observers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public TemperaturePane firstTemperaturePane;
    public TemperaturePane secondTemperaturePane;
    private WeatherDataSource minneapolisDataSource;
    private WeatherDataSource honoluluDataSource;

    public Controller() {
    }

    public void initialize() {
        this.minneapolisDataSource = new WeatherDataSource("Minneapolis", "us");
        this.minneapolisDataSource.addObserver(this.firstTemperaturePane);

        this.honoluluDataSource = new WeatherDataSource("Honolulu", "us");
        this.honoluluDataSource.addObserver(this.secondTemperaturePane);

        this.setWeatherUpdateTimer();
    }

    public void setWeatherUpdateTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        minneapolisDataSource.update();
                        honoluluDataSource.update();
                    }
                });
            }
        };

        final long startTimeInMilliseconds = 0;
        final long repetitionPeriodInMilliseconds = 5000;
        Timer timer = new java.util.Timer();
        timer.schedule(timerTask, startTimeInMilliseconds, repetitionPeriodInMilliseconds);
    }
}
