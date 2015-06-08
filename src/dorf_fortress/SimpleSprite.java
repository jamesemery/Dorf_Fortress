package dorf_fortress;

import javafx.scene.image.ImageView;

/**
 * SimpleSprite is a subclass of Sprite designed to handle the easiest cases:
 * an entity with a static sprite that moves with it.
 */
public class SimpleSprite extends Sprite {
    private ImageView image;

    /**
     * Calls ImageView's constructor, which takes in a string URL pointing to
     * the location of the image. Also stores the entity to which the sprite
     * corresponds.
     * @param sprite_Location   The location of the sprite in the repo.
     * @param entity   The entity to which the sprite corresponds.
     */
    public SimpleSprite(String sprite_Location, Entity entity) {
        super(entity);
        image = new ImageView(sprite_Location);
        this.getChildren().add(image);
    }

    /*
     * Setters and getters.
     */
    @Override
    public void setX(double x) {
        image.setX(x);
    }

    @Override
    public void setY(double y) { image.setY(y); }

    public ImageView getImage() { return this.image; }
}
