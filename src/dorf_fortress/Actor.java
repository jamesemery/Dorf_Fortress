package dorf_fortress;

import javafx.scene.Node;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Superclass that contains both the controllable character and enemy objects.
 */
public class Actor extends Sprite {
    public String name = "";

    /**
     * Calls Sprite's constructor, which makes the image and a hitbox object.
     * The hitbox object will be centered on the x-axis of the image, and
     * grounded onto the lower bound of its y-coordinates.
     * @param sprite_Location
     * @param hitbox_width
     * @param hitbox_height
     */
    public Actor(String sprite_Location, int hitbox_width, int hitbox_height) {
        super(sprite_Location, hitbox_width, hitbox_height);
    }

    public void step() {
        //do stuff
    }

    public void die() {
        //do stuff
    }
}
