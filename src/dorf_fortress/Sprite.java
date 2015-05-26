package dorf_fortress;

import javafx.scene.image.ImageView;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A sprite class; it's the superclass for all visible objects in the gameworld.
 * (With the probable exception of our background image.
 */
public class Sprite extends ImageView {
    /**
     * Calls ImageView's constructor, which takes in a string URL pointing to
     * the location of the image; then creates its own Hitbox object.
     * @param sprite_Location
     */
    public Sprite (String sprite_Location, int hitbox_width, int hitbox_height) {
        //ImageView's constructor
        super(sprite_Location);
        //TODO generate a hitbox object.
    }

    //hitbox stuff. That's really all we need to add here.
    //but look up the ImageView stuff.
}
