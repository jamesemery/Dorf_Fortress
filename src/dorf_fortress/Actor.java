package dorf_fortress;

import javafx.scene.Node;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Superclass that contains both the controllable character and enemy objects.
 */
public abstract class Actor extends Entity {
    public String name = "";
    public final double GRAVITY_CONSTANT = 5;
    public final double TERMINAL_VELOCITY = -300;
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
                 Model simulation, double x, double y) {
        super(sprite_location, hitbox_width, hitbox_height, simulation, x, y);
        this.name = "";
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
