package dorf_fortress;

import javafx.scene.Parent;

/**
 * An abstract sprite class; it's the superclass for all types of visible
 * objects in the game, excepting the tiled background.
 */
public abstract class Sprite extends Parent {
    protected Entity entity;

    /**
     * Makes a Parent node and saves the Entity that the sprite is associated
     * with.
     * @param entity   The sprite is for this entity.
     */
    public Sprite (Entity entity) {
        super();
        this.entity = entity;
    }

    /**
     * Takes the Dorf's absolute x-coordinate and shifts the sprite's relative
     * position on the screen accordingly.
     * @param dorf_x   The Dorf's x-coordinate.
     */
    public void update(double dorf_x) {
        double screenX = dorf_x - Main.SCENE_WIDTH/2;
        this.setY(entity.getY());
        this.setX(entity.getX() - screenX);
    }

    /*
     * Setters & Getters
     */
    public void setX(double x){};
    public void setY(double y){};
}
