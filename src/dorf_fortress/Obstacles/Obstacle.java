package dorf_fortress;

import java.util.Random;
/**
 * Abstract superclass containing all non-actor obstacles for the player.
 * These can move, but cannot be killed or otherwise altered by the player,
 * hence the difference from Enemy.
 */

public abstract class Obstacle extends Entity {
    /**
     * Constructor. Nothing special here; it just passes all the info to
     * Entity.
     */
    public Obstacle(int hitbox_width, int hitbox_height, double x, double y,
                    Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
    }

    /**
     * Factory method that creates a given type of obstacle based on the type
     * given. (Strings are perhaps not the best variable type for this, but
     * they pay dividends in code readability)
     * @return   An Obstacle of the requested type.
     */
    public static Obstacle getInstanceFactory(ObstaclePlacer source, Hitbox
            h, String type, Random rand) {
        if (type == "box") {
            return KillBlock.getInstance(source, h, rand);
        } else if (type == "simpleBall") {
            return Fireball.getInstance(source, h, rand);
        } else if (type == "spinningHead") {
            return SpinningHead.getInstance(source, h, rand);
        } else if (type == "oscillatingSpider") {
            return Spider.getInstance(source, h, rand);
        } else if (type == "disappearingGhost") {
            return DisappearingGhost.getInstance(source, h, rand);
        }
        return null;
    }
}
