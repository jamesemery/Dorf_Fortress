package dorf_fortress;

import javafx.scene.Node;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Superclass that contains both the controllable character and enemy objects.
 */
public class Actor extends Entity {
    public String name = "";
    public final double GRAVITY_CONSTANT = 0.1;
    public final double TERMINAL_VELOCITY = -160;
    public boolean isOnPlatform = true;
    boolean onPlatform;

    /**
     * Calls Sprite's constructor, which makes the image and a hitbox object.
     * The hitbox object will be centered on the x-axis of the image, and
     * grounded onto the lower bound of its y-coordinates. Additionally fills
     * in the Actor's name.
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
     * Calls Sprite's constructor, which makes the image and a hitbox object.
     * The hitbox object will be centered on the x-axis of the image, and
     * grounded onto the lower bound of its y-coordinates. Additionally fills
     * in the Actor's name.
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
     * A method for actors affected by gravity; called during the step() method.
     * This will probably be moved or overwritten, but I want somewhere to work
     * on the gravity.
     */
    public void fall() {
        if (y_velocity >= TERMINAL_VELOCITY) {
            y_velocity -= GRAVITY_CONSTANT;
        }
    }

    /**
     * Actor's step() method accounts for the effects of gravity and then just
     * moves according to its velocity.
     */
    public void step() {
        fall();
        super.step();
        System.out.println("false");
        setOnPlatform(false);
    }

    public void die() {
        //do stuff
    }

    //in current implementation the dorf will never act upon something else
    // colliding with it but it might in the future so this is left here
    public void collidesX(Entity projectile) {};
    public void collidesY(Entity projectile) {};

    public void setOnPlatform(boolean val) {
        isOnPlatform = val;
    }
}
