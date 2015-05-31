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
import javafx.scene.paint.Color;
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

        //Set up the scene. The commented lines are for testing.
        //startGame(primaryStage);
        startMenu(primaryStage);
        //startWinMenu(primaryStage);
        //startLoseMenu(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
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

            Scene mainMenu = new Scene(root, SCENE_WIDTH,
                    SCENE_HEIGHT);
            MainMenuController controller = loader.getController();
            root.requestFocus();

            mainStage.setScene(mainMenu);
            mainStage.show();
        } catch (Exception e) {
            System.out.println("Fxml file not found.");
            System.out.println(e);
            javafx.application.Platform.exit();
            System.exit(0);
        }
        System.out.println("setup Start Menu");

    }

    /**
     * Launches the game scene.
     */
    public static void startGame(Stage mainStage,
                String name, double difficulty, Color beardColor) {
        System.out.println("Debug message from Main.startGame(). Params:");
        System.out.println("Name: " + name);
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Color: " + beardColor);
        Group root = new Group();
        Scene gameScene = new Scene(root, SCENE_WIDTH,
                SCENE_HEIGHT);

        //Set up the controller. (Hopefully)
        GameController controller = new GameController(root, SCENE_HEIGHT, difficulty);
        controller.initialize();

        // Set up a KeyEvent handler so we can respond to keyboard activity.
        root.setOnKeyPressed(controller);
        root.setOnKeyReleased(controller);
        root.requestFocus();

        mainStage.setScene(gameScene);
        mainStage.show();
        System.out.println("setup Game");
    }


    /**
     * Launches the lose menu scene.
     */
    public static void startLoseMenu(Scene mainScene) {
        URL fxmlUrl = Main.class.getResource("LoseScreen.fxml");
        System.out.println(fxmlUrl);
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            AnchorPane loseRoot = loader.load();

            LoseScreenController controller = loader.getController();
            Group root = (Group) mainScene.getRoot();
            root.getChildren().add(loseRoot);
            loseRoot.requestFocus();

        } catch (Exception e) {
            System.out.println("Fxml file not found!");
            javafx.application.Platform.exit();
            System.exit(0);
        }
        System.out.println("setup Lose Menu");

    }


    /**
     * Launches the win menu scene.
     */
    public static void startWinMenu(Scene mainScene) {
        System.out.println("Setting up win menu");
        URL fxmlUrl = Main.class.getResource("WinScreen.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            AnchorPane winRoot = loader.load();
            Group root = (Group) mainScene.getRoot();
            root.getChildren().add(winRoot);
            winRoot.requestFocus();

        } catch (Exception e) {
            System.out.println("Fxml file not found!");
            System.out.println("Exception: " + e);
            javafx.application.Platform.exit();
            System.exit(0);
        }
        System.out.println("setup Win Menu");
    }


}