package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Abstract superclass containing all non-actor obstacles for the player.
 * These can move, but cannot be killed or otherwise altered by the player,
 * hence the difference from Enemy.
 */

public abstract class Obstacle extends Entity {

    public Obstacle(String sprite_location, int hitbox_width, int hitbox_height,
                 Model simulation) {
        super(sprite_location, hitbox_width, hitbox_height, simulation);
    }

    public abstract void collidesX(Dorf ferdinand);
    public abstract void collidesY(Dorf ferdinand);
}
