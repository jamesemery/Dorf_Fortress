package dorf_fortress;

import javafx.scene.Node;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Superclass that contains both the controllable character and enemy objects.
 */
public class Actor extends Entity {
    public String name = "";

    /**
     * Calls Sprite's constructor, which makes the image and a hitbox object.
     * The hitbox object will be centered on the x-axis of the image, and
     * grounded onto the lower bound of its y-coordinates. Additionally fills
     * in the Actor's name.
     * @param sprite_location
     * @param hitbox_width
     * @param hitbox_height
     */
    public Actor(String sprite_location, int hitbox_width, int hitbox_height) {
        super(sprite_location, hitbox_width, hitbox_height);
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
                 String name) {
        super(sprite_location, hitbox_width, hitbox_height);
        this.name = name;
    }

    public void step() {
        //do stuff
    }

    public void die() {
        //do stuff
    }
}
