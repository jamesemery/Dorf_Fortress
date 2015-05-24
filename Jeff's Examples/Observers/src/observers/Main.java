package observers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
    This application needs work. The goals were:

    1. Introducing the Observer Pattern
    2. A slightly more sophisticated JavaFX application after our simplest ones

    But I wrote this while I was brand new at JavaFX, and also when I
    was overwhelmed with a week out of control.

    Problems include:
    -- The weather data we're scraping doesn't update often enough for an in-class demo
    -- The GridPane is not a great way to lay this out.
    -- The animation I'm trying to do in TemperaturePane.spinnerLabel isn't showing up.
         I figured out why at some point, but I don't remember what the answer was.

    So, I'm adding this project to my cs257_2014 repository so it will be available
    next time I teach CS257. But fix it before using it.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Quit on close
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        // Load
        FXMLLoader loader = new FXMLLoader(getClass().getResource("observers.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = loader.getController();
        controller.initialize();

        primaryStage.setTitle("Observer example");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
