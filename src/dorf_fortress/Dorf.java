package dorf_fortress;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Controllable hero of the game; subclass of Actor.
 */
public class Dorf extends Entity {
    double STEP_SIZE_X = 480/ GameController.FRAMES_PER_SECOND;
    double STEP_SIZE_Y = 180/ GameController.FRAMES_PER_SECOND;
    public final double FRICTION_CONSTANT = 240/ GameController.FRAMES_PER_SECOND;
    public final double MAX_HORIZ_SPEED = 12000 / GameController.FRAMES_PER_SECOND;
    public final double GRAVITY_CONSTANT = 10;
    public final double TERMINAL_VELOCITY = -600;
    Platform curPlatform;
    private boolean victorious = false;
    String name;
    InputBuffer inputSource;


    /**
     * Calls Entity's constructor with no name.
     * @param hitbox_width
     * @param hitbox_height
     */
    public Dorf(int hitbox_width, int hitbox_height, double x, double y,
                Model model) {
        super(hitbox_width, hitbox_height, x, y, model);
        this.name = "";
        inputSource = BasicInputBuffer.getInstance();
        hitbox = new DorfHitbox( hitbox_width, hitbox_height);
        height = 32;
        width = 22;
        hitbox_checker = true;
        this.screen_death = true;
    }

    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        String[] rightImages = {"sprites/ColoredDorfRight1.png",
                "sprites/ColoredDorfRight2.png","sprites/ColoredDorfRight3.png"};
        String[] leftImages = {"sprites/ColoredDorfLeft1.png",
                "sprites/ColoredDorfLeft2.png","sprites/ColoredDorfLeft3.png"};
        System.out.println("Calling makeSprite...");
        this.sprite = new DorfSprite(leftImages,rightImages,(int)this.width,
                (int)this.height, this);

    }

    public void colorSprite(Color hairColor) {
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
        this.x_velocity -= STEP_SIZE_X;
        if (this.x_velocity < MAX_HORIZ_SPEED * -1) {
            this.x_velocity = MAX_HORIZ_SPEED * -1;
        }
    }

    /**
     * Adds to the rightwards velocity.
     */
    public void right() {
        this.x_velocity += STEP_SIZE_X;
        if (this.x_velocity > MAX_HORIZ_SPEED) {
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
        System.out.println("Running die() in Dorf.java");
        System.out.println(this.getSprite().getScene().getWindow());
        simulation.reset();
        if (!(this instanceof Ghost)) {
            simulation.pause();
        }
        Scene mainScene = this.getSprite().getScene();
        //set up the lose menu
        Main.startOverlayMenu(mainScene, simulation, false);
    }

    /**
     * Completes the level and initiates the win screen.
     */
    public void win() {
        System.out.println("Beginning win() method");
        simulation.reset();
        if (!(this instanceof Ghost)) {
            simulation.pause();
            System.out.println("pausing");
        }
        if(this.victorious == false) {
            this.victorious = true;
            Scene mainScene = this.getSprite().getScene();
            //set up the win menu
            Main.startOverlayMenu(mainScene, simulation, true);
            System.out.println("Ending win() method");
        }
    }

    //does stuff
    public void reset() {
        super.reset();
        this.victorious = false;
        inputSource.clear();
    }

    /**
     * Change in behavior for dorf so it doesn't worry about going off the top
     */
    @Override
    protected boolean isOffScreen() {
        //under the bottom first then over the top second
        if (this.getY() > simulation.SCENE_HEIGHT) {
            return true;
        }
        return false;
    }

    // In the current implementation an Actor will never act upon something else
    // colliding with it (it will instead call the collidesX/Y method of the
    // object it's colliding with) but it might in the future, and it needs
    // to fill the abstract methods from Entity.
    public void collidesX(Entity projectile) {};
    public void collidesY(Entity projectile) {};

    public void setCurPlatform(Platform platform) {
        curPlatform = platform;
    }
}
