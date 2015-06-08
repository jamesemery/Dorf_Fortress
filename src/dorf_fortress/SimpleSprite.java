package dorf_fortress;

import javafx.scene.image.ImageView;

/**
 * Created by jamie on 6/1/15.
 */
public class SimpleSprite extends Sprite {
    ImageView image;

    /**
     * Calls ImageView's constructor, which takes in a string URL pointing to
     * the location of the image; then creates its own Hitbox object.
     *
     * @param sprite_Location
     * @param entity
     */
    public SimpleSprite(String sprite_Location, Entity entity) {
        super(entity);
        image = new ImageView(sprite_Location);
        this.getChildren().add(image);
    }

    @Override
    public void setX(double x) {
        image.setX(x);
    }

    @Override
    public void setY(double y) {
        image.setY(y);
    }
}
