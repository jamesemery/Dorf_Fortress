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
    final private double FRAMES_PER_SECOND = 20.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Dorf Fortress");
        primaryStage.setResizable(false);

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainmenu" +
//                ".fxml"));
//        Parent menuRoot = (Parent) loader.load();
//        final MainMenuController mainMenuController = loader.getController();

        // Code snippet from Jeff Ondich's example, to make sure the
        // application closes when the window does.
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

        //Make a Dorf!
//        Dorf ferdinand = new Dorf("sprites/BasicDorf.png", 32, 32, "Ferdinand", this);
//        ferdinand.setX(34);
//        ferdinand.setY(100);
//        root.getChildren().add(ferdinand.getSprite());

        //Make a Platform?
//        Platform platty = new Platform("sprites/128x32platform.png",128,32,400,
//                400);
//        platty.setX(34);
//        platty.setY(400);
//        root.getChildren().add(platty.getSprite());
//
//        Platform platty2 = new Platform("sprites/128x32platform.png",128,32,400,
//                400);
//        platty2.setX(162);
//        platty2.setY(400);
//        platty2.updateSprite();
//        root.getChildren().add(platty2.getSprite());


        //Set up the controller. (Hopefully)
        GameController controller = new GameController(root);
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