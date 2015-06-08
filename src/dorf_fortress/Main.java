package dorf_fortress;

import javafx.application.*;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.File;
import java.net.URL;

/**
 * The main application for DorfFortress. Main contains all the code that spans
 * multiple Scenes: it has the ability to create new scenes, it plays the
 * background noise, and it handles the interactions between different menus.
 */
public class Main extends Application {

    public final static double SCENE_WIDTH = 640;
    public final static double SCENE_HEIGHT = 480;
    private final static String ambientNoiseLoc = "src/sprites/dungeonSound.wav";
    private static MediaPlayer noisePlayer;
    public static int deadDorfs = 0;

    /**
     * Sets up the stage, then makes the Main Menu.
     * @param primaryStage   The main Stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
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

        loadMainMenu(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Launches the menu scene, with its own controller.
     */
    public static void loadMainMenu(Stage mainStage) {
        deadDorfs = 0; //reset the casualty counter; we're starting a new game.
        URL fxmlUrl = Main.class.getResource("mainmenu.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            Parent root = (Parent) loader.load();

            Scene mainMenu = new Scene(root, SCENE_WIDTH,
                    SCENE_HEIGHT);
            MainMenuController controller = loader.getController();
            root.requestFocus(); //Make sure we can click the buttons...
            mainStage.setScene(mainMenu);
            mainStage.show();
            //Play ambient dungeon sounds on loop
            Media ambientNoise = new Media(new File(ambientNoiseLoc).toURI().toString());
            noisePlayer = new MediaPlayer(ambientNoise);
            noisePlayer.setCycleCount(MediaPlayer.INDEFINITE);
            if(!noisePlayer.statusProperty().equals(MediaPlayer.Status.PLAYING)) {
                noisePlayer.play();
            }
        } catch (Exception e) {
            System.out.println("Problem loading Main Menu:");
            System.out.println(e);
            javafx.application.Platform.exit();
            System.exit(0);
        }

    }

    /**
     * Launches the game scene.
     */
    public static void startGame(Stage mainStage, double difficulty, Color hairColor) {
        Group root = new Group();
        Scene gameScene = new Scene(root, SCENE_WIDTH,
                SCENE_HEIGHT);

        // Set up the controller. GameController's methods will take it from
        // there.
        GameController controller = new GameController(root, SCENE_HEIGHT,
                difficulty, hairColor);
        controller.initialize();

        // Set up a KeyEvent handler so we can respond to keyboard activity.
        root.setOnKeyPressed(controller);
        root.setOnKeyReleased(controller);
        root.requestFocus();

        mainStage.setScene(gameScene);
        mainStage.show();
    }

    /**
     * Loads the win or the lose menu, depending on the value of the boolean
     * given. The menu is overlaid onto a paused game Scene.
     * @param mainScene   A reference to the current Scene.
     * @param simulation   A reference to the model.
     * @param winning   Stores whether or not the menu was called b/c of a win.
     */
    public static void startOverlayMenu(Scene mainScene, Model simulation, boolean winning) {
        URL fxmlUrl;
        if (winning) {
            fxmlUrl = Main.class.getResource("WinScreen.fxml");
        } else {
            fxmlUrl = Main.class.getResource("LoseScreen.fxml");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            // Create the menu, stemming from an AnchorPane parent
            AnchorPane menuRoot = loader.load();
            OverlayController controller = loader.getController();
            controller.setModel(simulation);
            menuRoot.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    controller.handleKeyPress(keyEvent, winning);
                }
            });
            // Add the menu's root to the root of the game Scene, thus
            //overlaying it above the game.
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

    /**
     * Colors an image to match a given color. It does so using color-keying;
     * the pixels we want to change are given a green hue (120), which we can
     * then alter. This lets us change a Dorf's hair and beard while leaving
     * the armor pixels untouched.
     * The method is called by both the DorfSprite and MainMenuController, which
     * are in completely different places; hence its location here, in the
     * static context of the Main class.
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
                javafx.scene.paint.Color color = reader.getColor(x, y);
                if (color.getHue() == 120) { //look only at green pixels
                    //keep the hue and saturation from the color,
                    //but use the template's brightness.
                    double hue = hairColor.getHue();
                    double saturation = hairColor.getSaturation();
                    double brightness = color.getBrightness();

                    //dark color inputs shouldn't be lightened too much;
                    //we'll average them with the hair template instead.
                    if(hairColor.getBrightness() < 0.5) {
                        brightness += hairColor.getBrightness();
                        brightness /=2;
                    }
                    javafx.scene.paint.Color newColor = color.hsb(hue,
                            saturation, brightness, 1); //The 1 = 100% opacity
                    dorfOutputWriter.setColor(x, y, newColor);
                } else {
                    javafx.scene.paint.Color newColor = color;
                    dorfOutputWriter.setColor(x, y, newColor);
                }
            }
        }
        return output;
    }
}