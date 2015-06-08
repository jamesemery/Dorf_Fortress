package dorf_fortress;

import java.util.Random;

/**
 * The simplest form of an obstacle, a static block that kills the player
 * when touched. This is the most generic obstacle and is capable of placing
 * itself within an x y range of the player so that it will be at least
 * somewhat challenging to the player
 */
public class KillBlock extends Obstacle {

  public KillBlock(int hitbox_width, int hitbox_height, double x, double y,
                   Model simulation) {
    super(hitbox_width, hitbox_height, x, y, simulation);
  }

    @Override
    protected void makeSprite() {
        this.sprite = new SimpleSprite(
                "sprites/staticObstacleTransparent.png", this);
    }

    @Override
    protected void makeHitbox() {
        this.hitbox = new RectangleHitbox(this.width,this.height);
    }

    @Override
    public void collidesX(Entity projectile) {
        projectile.die();
    }

    @Override
    public void collidesY(Entity projectile) {
        projectile.die();
    }

    /**
     * Creates an instance of killblock such that it does not intersect with
     * the given killblock but is also within 200 pixels x and 150 pixels y
     * of the players path for challenge.
     *
     * @param source ObstaclePlacer object that allow access to perameters
     *               about the current state of the simulation
     * @param target the Hitbox the Killblock is "aiming" for
     * @param rand a random generator it uses to give parameters
     * @return returns a KillBlock object that doesn't intersect with the hitbox
     */
    public static Obstacle getInstance(ObstaclePlacer source, Hitbox target,
                                       Random rand) {
        double xTarget = target.getX();
        double yTarget = target.getY();
        KillBlock instance = null;

        //making sure the generated block doesnt already intersect the target
        while ((instance==null)||(instance.hitbox.intersects(target))) {
            double x;
            double y;
            x = rand.nextInt(200);
            y = rand.nextInt(200);
            if (rand.nextBoolean()) {
                x = -1*x;
            }
            if (rand.nextBoolean()) {
                y = -1*y;
            }
            x += xTarget;
            y += yTarget;
            instance = new KillBlock(32,32,x,y,source.getSimulation());
        }
        return instance;
    }
}
