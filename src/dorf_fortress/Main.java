package dorf_fortress;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.File;
import java.net.URL;

/*
 * TODO LIST - do NOT submit until these are finished!
 * - Delete unused imports, methods, classes, etc.
 * - Delete all print statements.
 * - Double-check that every part of the code is commented.
 * - Check all inheritance trees for redundancies that can be deleted!
 */

public class Main extends Application {

    final static double SCENE_WIDTH = 640;
    final static double SCENE_HEIGHT = 480;
    final static String ambientNoiseLoc = "src/sprites/dungeonSound.wav";
    static MediaPlayer noisePlayer;


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
        loadMainMenu(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Launches the menu scene, with its own controller.
     */
    public static void loadMainMenu(Stage mainStage) {
        URL fxmlUrl = Main.class.getResource("mainmenu.fxml");
        System.out.println("fxml url: " + fxmlUrl);
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
        //Play ambient dungeon sounds on loop
        Media ambientNoise = new Media(new File(ambientNoiseLoc).toURI().toString());
        noisePlayer = new MediaPlayer(ambientNoise);
        noisePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        System.out.println(noisePlayer.statusProperty());
        if(!noisePlayer.statusProperty().equals(MediaPlayer.Status.PLAYING)) {
            noisePlayer.play();
            System.out.println("playing media");
        }
    }

    /**
     * Launches the game scene.
     */
    public static void startGame(Stage mainStage, String name,
                                 double difficulty, Color beardColor) {

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
    public static void startLoseMenu(Scene mainScene, Model simulation) {
        URL fxmlUrl = Main.class.getResource("LoseScreen.fxml");
        System.out.println(fxmlUrl);
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            AnchorPane loseRoot = loader.load();

            OverlayController controller = loader.getController();
            controller.setModel(simulation);

            loseRoot.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    controller.handleKeyPress(keyEvent);
                }
            });

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
    public static void startWinMenu(Scene mainScene, Model simulation) {
        System.out.println("Setting up win menu");
        URL fxmlUrl = Main.class.getResource("WinScreen.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            AnchorPane winRoot = loader.load();

            OverlayController controller = loader.getController();
            controller.setModel(simulation);

            winRoot.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    controller.handleKeyPress(keyEvent);
                }
            });

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