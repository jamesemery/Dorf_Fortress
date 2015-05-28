package dorf_fortress;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {


    final private double SCENE_WIDTH = 640;
    final private double SCENE_HEIGHT = 480;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Dorf Fortress");
        primaryStage.setResizable(false);

        // Code snippet taken from example code by Jeff Ondich, to make sure the
        // application closes when the window does.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                javafx.application.Platform.exit();
                System.exit(0);
            }
        });

        //We're making an empty scene with just the root, then populating it
        //with objects.
        Group root = new Group();
        Scene platformerBasics = new Scene(root, SCENE_WIDTH,
                SCENE_HEIGHT);

        //Make a tiled background.
        BackgroundImage myBI = new BackgroundImage(
                new Image("sprites/BasicTile.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Region platformerBasicsRegion = new Region();
        platformerBasicsRegion.setPrefSize(SCENE_WIDTH+20, SCENE_HEIGHT+20);
        platformerBasicsRegion.setBackground(new Background(myBI));
        root.getChildren().add(platformerBasicsRegion);

        //Set up the controller. (Hopefully)
        GameController controller = new GameController(root, SCENE_HEIGHT);
        controller.initialize();

        // Set up a KeyEvent handler so we can respond to keyboard activity.
        root.setOnKeyPressed(controller);
        root.setOnKeyReleased(controller);
        root.requestFocus();

        primaryStage.setScene(platformerBasics);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}