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

public class GameController implements EventHandler<KeyEvent> {

    final public static double FRAMES_PER_SECOND = 60;
    private Timer timer;
    public Model simulation;
    public BasicInputBuffer inputStore;
    List<Sprite> spriteList;
    Group root;
    private double screenOffset;
    private Text helpText;
    private Text view_timer;
    private Text difficulty;
    private Rectangle background;
    final String backgroundImageLoc = "sprites/BasicTile.png";
    Image tile;
    double tileWidth;
    double tileHeight;
    boolean paused = false;


    public GameController(Group root, double sceneHeight,
                          double difficulty, Color hairColor) {
        System.out.println("Making a controller object...");
        this.root = root;
        spriteList = new ArrayList<Sprite>();
        this.background = setUpBackground();
        updateBackground(0);
        //this.simulation = Model.getInstance(this, sceneHeight, difficulty);
        this.simulation = new Model(this, sceneHeight, difficulty);
        this.simulation.player.colorSprite(hairColor);
        this.helpText = drawHelpText();
        this.view_timer = drawViewTimer();
        this.difficulty = drawDifficulty();
    }

    public void initialize() {

        this.setUpAnimationTimer();
        inputStore = (BasicInputBuffer)BasicInputBuffer.getInstance();
    }

    /**
     * Sets up the tiled background image in its initial position. Returns the
     * Region object as well as adding it to the tree.
     * @return a Region containing the tiled image.
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
     * @param screenOffset is the player's x-coordinate.
     */
    public void updateBackground(double screenOffset) {
        //get the dorf's offset over one tile
        double tileOffset = screenOffset % this.tileWidth;
        //change background position accordingly.
        ImagePattern tilePattern = new ImagePattern(
                (this.tile),-1*tileOffset,0,this.tile.getWidth(),
                tile.getHeight(), false );
        this.background.setFill(tilePattern);

    }

    //TODO: move out of a controller to a view?
    private Text drawHelpText() {
        Text text = new Text(20,400,"Use WASD or arrow keys to move.\n" +
                "Press P or Esc to pause.\n" + "Hold W/Up for a higher jump.");
        text.setFont(new Font("Alegreya SC", 20));
        text.setFill(new Color(1, 1, 1,1));
        text.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(text);
        return text;
    }

    //todo: move out of a controller to a view?
    private Text drawDifficulty() {
        Text text = new Text(290,25,"Level " +
                Integer.toString((int) simulation.getDifficulty() + 1));
        text.setFont(new Font("Alegreya SC", 20));
        text.setFill(new Color(1,1,1,1));
        text.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(text);
        return text;
    }

    //todo: move out of a controller to a view?
    private Text drawViewTimer() {
        Text text = new Text(610,25,
                Integer.toString(simulation.getRemainingTime()));
        text.setFont(new Font("Alegreya SC", 20));
        text.setFill(new Color(1, 1, 1,1));
        text.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(text);
        return text;
    }

    /**
     * This is essentially our view; it sets up an animation timer for the
     * scene.
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

        final long startTimeInMilliseconds = 0;
        final long repetitionPeriodInMilliseconds = 100;
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
     * @param sprite
     */
    public void addSpriteToRoot(Sprite sprite){
        root.getChildren().add(sprite);
        spriteList.add(sprite);
    }

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
            screenOffset = simulation.levelSolver.getX();
        } else {
            screenOffset = simulation.player.getX();
        }
        for (Sprite s : spriteList) {
            s.update(screenOffset);
        }
        //fade out the help text as the player moves farther along the level
        double textOpacity = 0;
        if (this.simulation.getGhostMode() == false) { //ghosts don't need help
            textOpacity = 1 - ( (this.simulation.player.getX() - 50) * .005);
            if (textOpacity < 0) { textOpacity = 0; }
            if (textOpacity > 1) { textOpacity = 1; }
        }
        this.helpText.setFill(new Color(1, 1, 1, textOpacity));

        String time_left = Integer.toString(simulation.getRemainingTime());
        view_timer.setText(time_left.substring(0,time_left.length()-2));

        updateBackground(screenOffset);
    }

    /**
     * Handles key events; checks for both key presses and releases, and passes
     * them on to the appropriate method.
     * @param keyEvent
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
     * @param keyEvent
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
     * @param keyEvent
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
