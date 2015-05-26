package dorf_fortress;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {


    final private double SCENE_WIDTH = 500;
    final private double SCENE_HEIGHT = 400;
    final private double FRAMES_PER_SECOND = 20.0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Code snippet from Jeff Ondich's example, to make sure the
        //application closes when the window does.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                javafx.application.Platform.exit();
                System.exit(0);
            }
        });
        //Example code for loading from an fxml file. We might need this for
        //the main menu?
        //FXMLLoader.load(getClass().getResource("sample.fxml"));
        //In the meantime, we'll just do this...
        Group root = new Group();
        //...and fill in the objects by hand. That's much nicer to do for our
        //actual platformer.

        primaryStage.setTitle("Dorf Fortress");
        Scene platformerBasics = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        //Testing basic drawing functionality
        Actor dorfActor = new Actor("sprites/BasicDorf.png", 32, 32);
        dorfActor.setX(34);
        dorfActor.setY(100);
        root.getChildren().add(dorfActor);

        primaryStage.setScene(platformerBasics);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
