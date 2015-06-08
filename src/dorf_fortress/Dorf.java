package dorf_fortress;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * Controllable hero of the game; subclass of Entity.
 */
public class Dorf extends Entity {
    public final double STEP_SIZE_X = 480/ GameController.FRAMES_PER_SECOND;
    public final double STEP_SIZE_Y = 180/ GameController.FRAMES_PER_SECOND;
    public final double GRAVITY_CONSTANT = 10;
    public final double TERMINAL_VELOCITY = -600;
    public final double FRICTION_CONSTANT = 240 / GameController.FRAMES_PER_SECOND;
    public final double MAX_HORIZ_SPEED = 12000 / GameController.FRAMES_PER_SECOND;
    protected Platform curPlatform;
    private boolean victorious = false;
    protected InputBuffer inputSource;
    public Color hairColor;


    /**
     * Constructor.
     * @param hitbox_width   The width of the Dorf's hitbox.
     * @param hitbox_height   The height of the Dorf's hitbox.
     */
    public Dorf(int hitbox_width, int hitbox_height, double x, double y,
                Model model) {
        super(hitbox_width, hitbox_height, x, y, model);
        inputSource = BasicInputBuffer.getInstance();
        hitbox = new DorfHitbox( hitbox_width, hitbox_height);
        hitbox_checker = true; // Makes it so the dorf does collision
        // detection on other objects
        this.screen_death = true; // Makes it so the dorf will die if it
        // falls off the screen
    }

    /**
     * Saves the type of the Dorf's current platform. This is used for two
     * purposes:
     * - to get the appropriate size jump boost when the Dorf leaves a platform
     * - to ignore the maximum velocity constraints when on or leaving a
     *   conveyor platform.
     * @param platform   The platform on which the Dorf is standing.
     */
    public void setCurPlatform(Platform platform) {
        curPlatform = platform;
    }

    /**
     * Makes the Dorf's sprite, a special Sprite subclass called DorfSprite.
     * The image sources are hard-coded in.
     */
    @Override
    protected void makeSprite() {
        String[] rightImages = {"sprites/ColoredDorfRight1.png",
                "sprites/ColoredDorfRight2.png","sprites/ColoredDorfRight3.png"};
        String[] leftImages = {"sprites/ColoredDorfLeft1.png",
                "sprites/ColoredDorfLeft2.png","sprites/ColoredDorfLeft3.png"};
        this.sprite = new DorfSprite(leftImages,rightImages,this);
    }

    /**
     * Colors the DorfSprite; this method simply passes the request for
     * coloration, and the desired Color, to the sprite itself.
     * @param hairColor   The new color for the Dorf's hair/beard.
     */
    public void colorSprite(Color hairColor) {
        this.hairColor = hairColor;
        ((DorfSprite)this.sprite).colorSprites(hairColor);
    }

    /**
     * Makes a specifc dorf hitbox
     */
    protected void makeHitbox() {
        this.hitbox = new DorfHitbox(this.width, this.height);
    }


    /**
     * Pulls the frame's input from the InputBuffer, then calls the appropriate
     * methods to modify its velocity. Then:
     * - applies friction
     * - calls Actor's step() method, which applies gravity and
     * - calls Entity's step() method, which changes position based on velocity
     *   and handles collision checking.
     */
    @Override
    public void step() {
        if (inputSource.getInput("left")) {
            this.left();
        }
        if (inputSource.getInput("right")) {
            this.right();
        }
        if (inputSource.getInput("up")) {
            this.up();
        }
        if (inputSource.getInput("down")) {
            this.down();
        }

        applyFriction();
        fall();
        super.step();
    }

    /**
     * Applies friction by subtracting a friction constant from the velocity.
     * To do that, we check the sign of the velocity, then make sure the
     * friction doesn't overshoot.
     */
    private void applyFriction() {
        if(this.x_velocity > 0) {
            this.x_velocity -= FRICTION_CONSTANT;
            //We don't want to overshoot on velocity, just bring it to zero.
            if (this.x_velocity < 0) { this.x_velocity = 0;}
        } else if (this.x_velocity < 0) {
            this.x_velocity += FRICTION_CONSTANT;
            //We don't want to overshoot on velocity, just bring it to zero.
            if (this.x_velocity > 0) { this.x_velocity = 0;}
        }
    }

    /**
     * Applies the effects of gravity; simply adds or subtracts to the velocity
     * based on the gravitational constant (up to terminal velocity.) It's worth
     * noting that y coordinates are measured from the top of the screen.
     */
    public void fall() {
        this.curPlatform = null;
        if (y_velocity >= TERMINAL_VELOCITY) {
            y_velocity -= GRAVITY_CONSTANT;
        } else {
            y_velocity = TERMINAL_VELOCITY;
        }
    }

    /**
     * Adds to the leftwards velocity.
     */
    public void left() {
        if (-MAX_HORIZ_SPEED < this.x_velocity) {
            this.x_velocity -= STEP_SIZE_X;
        } else {
            this.x_velocity = -MAX_HORIZ_SPEED;
        }
    }

    /**
     * Adds to the rightwards velocity. Has an exception, such that the Dorf or
     * Ghost is not automatically reset to MAX_HORIZ_SPEED if it's in the air
     * or on a ConveyorPlatform.
     */
    public void right() {
        boolean conveyor_check = (curPlatform instanceof ConveyorPlatform ||
                                  curPlatform == null);
        if (this.x_velocity < MAX_HORIZ_SPEED) {
            this.x_velocity += STEP_SIZE_X;
        } else if (!conveyor_check && this.x_velocity > MAX_HORIZ_SPEED) {
            this.x_velocity = MAX_HORIZ_SPEED;
        }
    }

    /**
     * Adds to the upwards velocity.
     */
    public void up() {
        if (curPlatform != null) {
            this.y_velocity += curPlatform.getJump();
        }
        if (this.y_velocity >= 50) {
            this.y_velocity += STEP_SIZE_Y;
        }
    }

    /**
     * Adds to the downwards velocity.
     */
    public void down() {
        this.y_velocity -= STEP_SIZE_Y;
    }

    /**
     * Resets the level if the dorf dies.
     */
    public void die() {
        simulation.reset();
        if (!(this instanceof Ghost)) {
            simulation.pause();
        }
        Main.deadDorfs++;
        Scene mainScene = this.getSprite().getScene();
        //set up the lose menu
        Main.startOverlayMenu(mainScene, simulation, false);
    }

    /**
     * Completes the level and initiates the win screen.
     */
    public void win() {
        simulation.reset();
        simulation.pause();
        if(this.victorious == false) {
            this.victorious = true;
            Scene mainScene = this.getSprite().getScene();
            //set up the win menu
            Main.startOverlayMenu(mainScene, simulation, true);
        }
    }

    /**
     * Resets the Dorf to its starting position, with no input.
     */
    public void reset() {
        super.reset();
        this.victorious = false;
        inputSource.clear();
    }

    /**
     * Checks whether the Dorf is off the bottom of the screen.
     */
    @Override
    protected boolean isOffScreen() {
        return (this.getY() > simulation.SCENE_HEIGHT);
    }

    /**
     * Dorf will never actually have its collidesX/Y methods called; it's the
     * collision methods of the thing it hits that matter. However, as a
     * subclass of Entity, it can't NOT have them. So they're empty.
     * @param projectile   A hypothetical, nonexistent entity.
     */
    public void collidesX(Entity projectile) {}
    public void collidesY(Entity projectile) {}
}
