package dorf_fortress;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {


    final private double SCENE_WIDTH = 640;
    final private double SCENE_HEIGHT = 480;
    final private double FRAMES_PER_SECOND = 20.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        //Not sure what would happen if people started messing around with the
        //window size mid-game, but I don't particularly want to find out.
        primaryStage.setResizable(false);
        Scene platformerBasics = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        //Make a tiled background.
        BackgroundImage myBI = new BackgroundImage(
                new Image("sprites/BasicTile.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Region platformerBasicsRegion = new Region();
        platformerBasicsRegion.setPrefSize(SCENE_WIDTH+20, SCENE_HEIGHT+20);
        platformerBasicsRegion.setBackground(new Background(myBI));
        root.getChildren().add(platformerBasicsRegion);

        //Make a Dorf!
        Dorf ferdinand = new Dorf("sprites/BasicDorf.png", 32, 32, "Ferdinand");
        ferdinand.setX(34);
        ferdinand.setY(100);
        root.getChildren().add(ferdinand.getSprite());

        //Set up the controller. (Hopefully)
        GameController controller = new GameController(ferdinand);
        controller.initialize();

        // Set up a KeyEvent handler so we can respond to keyboard activity.
        root.setOnKeyPressed(controller);
        root.requestFocus();

        primaryStage.setScene(platformerBasics);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}