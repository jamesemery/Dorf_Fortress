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

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {


    final static double SCENE_WIDTH = 640;
    final static double SCENE_HEIGHT = 480;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set up the stage
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

        //Set up the scene
        //startGame(primaryStage);
        startMenu(primaryStage);
    }

    /**
     * Launches the menu scene.
     */
    public static void startMenu(Stage mainStage) {
        URL fxmlUrl = Main.class.getResource("mainmenu.fxml");
        System.out.println(fxmlUrl);
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            Parent root = (Parent) loader.load();

            Scene platformerBasics = new Scene(root, SCENE_WIDTH,
                    SCENE_HEIGHT);
            MainMenuController controller = loader.getController();
            root.requestFocus();

            mainStage.setScene(platformerBasics);
            mainStage.show();
        } catch (Exception e) {
            System.out.println("Fxml file not found");
            javafx.application.Platform.exit();
            System.exit(0);
        }
        System.out.println("setup Menu");

    }

    /**
     * Launches the game scene.
     */
    public static void startGame(Stage mainStage) {
        Group root = new Group();
        Scene platformerBasics = new Scene(root, SCENE_WIDTH,
                SCENE_HEIGHT);

        //Set up the controller. (Hopefully)
        GameController controller = new GameController(root, SCENE_HEIGHT);
        controller.initialize();

        // Set up a KeyEvent handler so we can respond to keyboard activity.
        root.setOnKeyPressed(controller);
        root.setOnKeyReleased(controller);
        root.requestFocus();

        mainStage.setScene(platformerBasics);
        mainStage.show();
        System.out.println("setup Game");
    }

    public static void main(String[] args) {
        launch(args);
    }

}