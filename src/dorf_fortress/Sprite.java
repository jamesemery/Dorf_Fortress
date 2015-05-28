package dorf_fortress;

import javafx.scene.image.ImageView;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A sprite class; it's the superclass for all visible objects in the gameworld.
 * (With the probable exception of our background image.
 */
public class Sprite extends ImageView {
    private Entity entity;

    /**
     * Calls ImageView's constructor, which takes in a string URL pointing to
     * the location of the image; then creates its own Hitbox object.
     * @param sprite_Location
     */
    public Sprite (String sprite_Location, int hitbox_width, int hitbox_height, Entity entity) {
        //ImageView's constructor
        super(sprite_Location);
        this.entity = entity;
        //TODO generate a hitbox object.
    }

    public void update(double dorf_x) {
        double screenX = dorf_x - Main.SCENE_WIDTH/2;
        this.setY(entity.getY());
        this.setX(entity.getX() - screenX);

    }
}
