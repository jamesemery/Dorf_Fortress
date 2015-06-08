package dorf_fortress;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.List;
import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A class representing the combined view and controller for a game level. The
 * GameController manages the game loop and passes input to the model as well
 * as displaying sprites and such.
 *
 */
public class GameController implements EventHandler<KeyEvent> {

    final public static double FRAMES_PER_SECOND = 60;
    private Timer timer;
    public Model simulation;
    public BasicInputBuffer inputStore;
    List<Sprite> spriteList;
    Group root;
    private double dorf_x;
    private Text helpText;
    private Text view_timer;
    private Text difficulty;
    private Text dead_dorfs;
    private Rectangle background;
    final String backgroundImageLoc = "sprites/BasicTile.png";
    Image tile;
    double tileWidth;
    double tileHeight;
    boolean paused = false;

    /**
     * Constructor for GameController;
     * @param root   The root node of the Scene we're controlling
     * @param sceneHeight   The height of the scene; we need this information to
     *                      pass to the model we create.
     * @param difficulty   The difficulty of the level.
     * @param hairColor   The hair color of the Dorf; again, we pass this to the
     *                    model.
     */
    public GameController(Group root, double sceneHeight,
                          double difficulty, Color hairColor) {
        this.root = root;
        spriteList = new ArrayList<Sprite>();
        //Creates the background
        this.background = setUpBackground();
        updateBackground(0);
        //Initializes the simulation
        this.simulation = new Model(this, sceneHeight, difficulty);
        this.simulation.player.colorSprite(hairColor);
        //Initializes various view elements
        this.helpText = drawHelpText();
        this.view_timer = drawViewTimer();
        this.difficulty = drawDifficulty();
        this.dead_dorfs = drawDeadDorfs();
    }

    /**
     * Initializes the controller. Here we set up the animation loop and the
     * input, both tasks that require connection to the JavaFX Scene.
     */
    public void initialize() {
        this.setUpAnimationTimer();
        inputStore = (BasicInputBuffer)BasicInputBuffer.getInstance();
    }

    /**
     * Sets up the tiled background image in its initial position. Returns the
     * Region object as well as adding it to the tree.
     * @return   A Region containing the tiled image.
     */
    private Rectangle setUpBackground() {
        Image backgroundImage = new Image(backgroundImageLoc);
        //We have to make the rectangle a little bigger because when it pans
        //some of it goes off the screen.
        Rectangle backgroundRect = new Rectangle(0, 0,
                Main.SCENE_WIDTH,
                Main.SCENE_HEIGHT);
        this.tileWidth = backgroundImage.getWidth();
        this.tileHeight = backgroundImage.getHeight();
        this.tile = backgroundImage;
        ImagePattern tile = new ImagePattern(
                (backgroundImage),0,0,backgroundImage.getWidth(),
                 backgroundImage.getHeight(), false );
        backgroundRect.setFill(tile);
        root.getChildren().add(backgroundRect);

        return backgroundRect;
    }

    /**
     * Updates the background's tiling to match the platforms.
     * @param dorf_x   The player's x-coordinate.
     */
    public void updateBackground(double dorf_x) {
        //get the dorf's offset over one tile
        double tileOffset = dorf_x % this.tileWidth;
        //change background position accordingly.
        ImagePattern tilePattern = new ImagePattern(
                (this.tile),-1*tileOffset,0,this.tile.getWidth(),
                tile.getHeight(), false );
        this.background.setFill(tilePattern);

    }

    /**
     * Draws the hard-coded help text into a Text object.
     * @return   A Text object to be added to the Scene's XML tree.
     */
    private Text drawHelpText() {
        Text text = new Text(20,410,"Use WASD or arrow keys to move.\n" +
                "Press P or Esc to pause.\n" + "Hold W/Up for a higher jump.");
        text.setFont(new Font("Georgia", 20));
        text.setFill(new Color(1, 1, 1,1));
        text.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(text);
        return text;
    }

    /**
     * Draws the casualty count into a Text object.
     * @return   A Text object to be added to the Scene's XML tree.
     */
    private Text drawDeadDorfs() {
        String displayText = "Dead dorfs: " + Main.deadDorfs;
        Text text = new Text(15,25, displayText);
        text.setFont(new Font("Georgia", 20));
        text.setFill(new Color(1,1,1,1));
        text.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(text);
        return text;
    }

    /**
     * Draws the difficulty setting into a Text object.
     * @return   A Text object to be added to the Scene's XML tree.
     */
    private Text drawDifficulty() {
        String displayText = "Difficulty: ";
        if (simulation.getDifficulty() < 50) {
            displayText += Integer.toString((int) simulation.getDifficulty());
        } else {
            displayText += "MAX";
        }
        Text text = new Text(265,25, displayText);
        text.setFont(new Font("Georgia", 20));
        text.setFill(new Color(1,1,1,1));
        text.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(text);
        return text;
    }

    /**
     * Draws the timer into a Text object.
     * @return   A Text object to be added to the Scene's XML tree.
     */
    private Text drawViewTimer() {
        double timeLeft = simulation.getRemainingTime()/60;
        Text text = new Text(520, 25, "Time left: " +
                             Integer.toString((int) timeLeft));
        text.setFont(new Font("Georgia", 20));
        text.setFill(new Color(1, 1, 1,1));
        text.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(text);
        return text;
    }

    /**
     * Sets up an animation timer that composes the basic game loop.
     */
    private void setUpAnimationTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer = new java.util.Timer();
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Calls the model's simulateFrame() method, which updates the objects in
     * the scene.
     */
    private void updateAnimation() {
        simulation.simulateFrame();
        updateScreen();
    }

    /**
     * Pauses the animation.
     */
    public void pause() {
        if(this.paused == false) {
            this.paused = true;
            this.timer.cancel();
        }
    }

    /**
     * Unpauses the animation.
     */
    public void unpause() {
        if(this.paused == true) {
            this.setUpAnimationTimer();
            this.paused = false;
        }
    }

    /**
     * Adds a sprite to the scene, both to the root node and the controller's
     * list of sprites.
     * @param sprite   Sprite to be added.
     */
    public void addSpriteToRoot(Sprite sprite){
        root.getChildren().add(sprite);
        spriteList.add(sprite);
    }

    /**
     * Removes a sprite from both the controller's spriteList and the root node.
     * @param sprite   Sprite to be removed.
     */
    public void removeSpriteFromRoot(Sprite sprite) {
        root.getChildren().remove(sprite);
        spriteList.remove(sprite);
    }

    /**
     * Handles logic for determining screen scroll left and right based on
     * the Dorf's x coordinate then it loops through the sprites in the list
     * and asks them to update given the new offset
     */
    public void updateScreen() {
        if (simulation.getGhostMode()) {
            dorf_x = simulation.levelSolver.getX();
        } else {
            dorf_x = simulation.player.getX();
        }
        for (Sprite s : spriteList) {
            s.update(dorf_x);
        }
        //fade out the help text as the player moves farther along the level
        double textOpacity = 0;
        if (!this.simulation.getGhostMode()) { //ghosts don't need help
            textOpacity = 1 - ( (this.simulation.player.getX() - 50) * .005);
            if (textOpacity < 0) { textOpacity = 0; }
            if (textOpacity > 1) { textOpacity = 1; }
        }
        this.helpText.setFill(new Color(1, 1, 1, textOpacity));

        double timeLeft = simulation.getRemainingTime()/60;
        view_timer.setText("Time left: " + Integer.toString((int) timeLeft));

        dead_dorfs.setText("Dead dorfs: " + Main.deadDorfs);

        updateBackground(dorf_x);
    }

    /**
     * Handles key events; checks for both key presses and releases, and passes
     * them on to the appropriate method.
     * @param keyEvent   The key-input received.
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.getEventType() == keyEvent.KEY_PRESSED) {
            handleKeyPress(keyEvent);
        } else if (keyEvent.getEventType() == keyEvent.KEY_RELEASED) {
            handleKeyRelease(keyEvent);
        }
    }

    /**
     * Handles key presses of appropriate keys by passing them on to our input
     * buffer. The boolean value "true" indicates a key press rather than a
     * key release.
     * @param keyEvent   The key-press received.
     */
    private void handleKeyPress(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            inputStore.addInput("left", true);
            keyEvent.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            inputStore.addInput("right", true);
            keyEvent.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W || code == KeyCode.SPACE) {
            inputStore.addInput("up", true);
            keyEvent.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            inputStore.addInput("down", true);
            keyEvent.consume();
        } else if (code == KeyCode.ESCAPE || code == KeyCode.P) {
            if(this.paused) {
                unpause();
            } else {
                pause();
            }
        }
    }

    /**
     * Handles releases of appropriate keys by passing them on to our input
     * buffer. The boolean value "true" indicates a key release rather than a
     * key press.
     * @param keyEvent   The key release received.
     */
    private void handleKeyRelease(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            inputStore.addInput("left", false);
            keyEvent.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            inputStore.addInput("right", false);
            keyEvent.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W || code == KeyCode.SPACE) {
            inputStore.addInput("up", false);
            keyEvent.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            inputStore.addInput("down", false);
            keyEvent.consume();
        }
    }

}
