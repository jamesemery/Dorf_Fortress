package dorf_fortress;

import javafx.scene.Node;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Superclass that contains both the controllable character and enemy objects.
 */
public class Actor extends Entity {
    public String name = "";
    public final double GRAVITY_CONSTANT = .98;
    public final double TERMINAL_VELOCITY = -160;
    public boolean isOnPlatform = true;
    boolean onPlatform;

    /**
     * Creates an unnamed Actor, by passing everything up to Entity's
     * constructor.
     * @param sprite_location
     * @param hitbox_width
     * @param hitbox_height
     */
    public Actor(String sprite_location, int hitbox_width, int hitbox_height,
                 Model simulation) {
        super(sprite_location, hitbox_width, hitbox_height, simulation);
        this.name = "";
    }

    /**
     * Creates an Actor with a given name, again by passing its parameters up
     * to Entity.
     * @param sprite_location
     * @param hitbox_width
     * @param hitbox_height
     * @param name
     */
    public Actor(String sprite_location, int hitbox_width, int hitbox_height,
                 String name, Model simulation) {
        super(sprite_location, hitbox_width, hitbox_height, simulation);
        this.name = name;
    }

    /**
     * Applies the effects of gravity; simply adds or subtracts to the velocity
     * based on the gravitational constant (up to terminal velocity.) It's worth
     * noting that y coordinates are measured from the top of the screen.
     */
    public void fall() {
        if (y_velocity >= TERMINAL_VELOCITY) {
            y_velocity -= GRAVITY_CONSTANT;
        } else {
            y_velocity = TERMINAL_VELOCITY;
        }
    }

    /**
     * Actor's step() method accounts for the effects of gravity and then simply
     * moves according to its velocity, via Entity's step() method.
     */
    public void step() {
        fall();
        setOnPlatform(false);
        super.step();
    }

    /**
     * Runs when the character meets its demise. TODO IMPLEMENT/DEAL WITH THIS
     */
    public void die() {
        //do stuff
    }

    // In the current implementation an Actor will never act upon something else
    // colliding with it (it will instead call the collidesX/Y method of the
    // object it's colliding with) but it might in the future, and it needs
    // to fill the abstract methods from Entity.
    public void collidesX(Entity projectile) {};
    public void collidesY(Entity projectile) {};

    public void setOnPlatform(boolean val) {
        onPlatform = val;
    }
}
