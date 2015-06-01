package dorf_fortress;

import java.util.Random;
/**
 * Created by Joe Adkisson on 5/24/2015.
 * Abstract superclass containing all non-actor obstacles for the player.
 * These can move, but cannot be killed or otherwise altered by the player,
 * hence the difference from Enemy.
 */

public abstract class Obstacle extends Entity {

    public Obstacle(String sprite_location, int hitbox_width, int hitbox_height, double x, double y,
                    Model simulation) {
        super(sprite_location, hitbox_width, hitbox_height, x, y, simulation);
    }

    public static Obstacle getInstanceFactory(ObstaclePlacer source, Hitbox
            h, String type, Random rand) {
        if (type == "box") {
            return KillBlock.getInstance(source, h, rand);
        } else if (type == "simpleBall") {
            return SimpleUpwardsKillBall.getInstance(source, h, rand);
        }
        return null;
    }
}
