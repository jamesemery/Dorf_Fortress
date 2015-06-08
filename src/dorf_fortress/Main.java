package dorf_fortress;

import javafx.application.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
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
        }
    }

    /**
     * Colors an image to match a given color. It does so using color-keying;
     * the pixels we want to change are given a green hue (120), which we can
     * then alter. This lets us change a Dorf's hair and beard while leaving
     * the armor untouched. The method is called by both the DorfSprite and
     * MainMenuController, which are in completely different places; hence
     * its location here, in the static context of Main.
     */
    public static Image colorImage(Image image, Color hairColor) {
        //Initialize JavaFX image-related objects
        PixelReader reader = image.getPixelReader();
        WritableImage output = new WritableImage(
                (int) image.getWidth(), (int) image.getHeight());
        PixelWriter dorfOutputWriter = output.getPixelWriter();
        //Go through the pixels, and recolor all the green ones.
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                // reading a pixel from src image,
                // then writing a pixel to dest image
                javafx.scene.paint.Color color = reader.getColor(x, y);
                if (color.getHue() == 120) { //look only at green pixels
                    //keep the hue and saturation from the color,
                    //but use the template's brightness.
                    double hue = hairColor.getHue();
                    double saturation = hairColor.getSaturation();
                    double brightness = color.getBrightness();
                    //dark color inputs shouldn't be lightened too much;
                    //just average them with the template instead.
                    if(hairColor.getBrightness() < 0.5) {
                        brightness += hairColor.getBrightness();
                        brightness /=2;
                    }
                    javafx.scene.paint.Color newColor = color.hsb(hue, saturation, brightness, 1);
                    dorfOutputWriter.setColor(x, y, newColor);
                } else {
                    javafx.scene.paint.Color newColor = color;
                    dorfOutputWriter.setColor(x, y, newColor);
                }
            }
        }
        return output;
    }

    /**
     * Launches the game scene.
     */
    public static void startGame(Stage mainStage, double difficulty, Color hairColor) {

        Group root = new Group();
        Scene gameScene = new Scene(root, SCENE_WIDTH,
                SCENE_HEIGHT);

        //Set up the controller. (Hopefully)
        GameController controller = new GameController(root, SCENE_HEIGHT,
                difficulty, hairColor);
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
     * Loads the win or the lose menu, depending on the value of the boolean
     * given.
     * @param mainScene
     * @param simulation
     * @param winning
     */
    public static void startOverlayMenu(Scene mainScene, Model simulation, boolean winning) {
        System.out.println("Setting up a menu, winning="+winning);
        URL fxmlUrl;
        if (winning) {
            fxmlUrl = Main.class.getResource("WinScreen.fxml");
        } else {
            fxmlUrl = Main.class.getResource("LoseScreen.fxml");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            AnchorPane menuRoot = loader.load();

            OverlayController controller = loader.getController();
            controller.setModel(simulation);

            menuRoot.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    controller.handleKeyPress(keyEvent, winning);
                }
            });

            Group root = (Group) mainScene.getRoot();
            root.getChildren().add(menuRoot);
            menuRoot.requestFocus();

        } catch (Exception e) {
            System.out.println("Menu Could Not Load");
            System.out.println("Exception: " + e);
            javafx.application.Platform.exit();
            System.exit(0);
        }
    }
}