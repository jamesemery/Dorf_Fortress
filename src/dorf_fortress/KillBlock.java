package dorf_fortress;

import java.util.Random;

/**
 * The simplest form of an obstacle, a static block that kills the player
 * when touched
 * Created by jamie on 5/28/15.
 */
public class KillBlock extends Obstacle {

  public KillBlock(int hitbox_width, int hitbox_height, double x, double y,
                   Model simulation) {
    super(hitbox_width, hitbox_height, x, y, simulation);
  }

    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new Sprite("sprites/staticObstacleTransparent.png",
                (int)this.width, (int)this.height, this);
    }

    @Override
    protected void makeHitbox() {
        this.hitbox = new PlatformHitbox(this.width,this.height);
    }

    @Override
    public void collidesX(Entity projectile) {
        projectile.die();
    }

    @Override
    public void collidesY(Entity projectile) {
        projectile.die();
    }

    public Entity getCloseInstance(Hitbox target, int frameCount) {
        double xTarget = target.getX();
        double yTarget = target.getY();
        Random rand = new Random();
        return null;
    } //TODO finish filling this in

    public static Obstacle getInstance(ObstaclePlacer source, Hitbox target,
                                       Random rand) {
        double xTarget = target.getX();
        double yTarget = target.getY();
        KillBlock instance = null;

        //making sure the generated block doesnt already intersect the target
        while ((instance==null)||(instance.hitbox.intersects(target))) {
            double x;
            double y;
            x = rand.nextInt(100);
            y = rand.nextInt(70);
            if (rand.nextBoolean()) {
                x = -1*x;
            }
            if (rand.nextBoolean()) {
                y = -1*y;
            }
            x += xTarget;
            y += yTarget;
            System.out.println(x + " " + y);
            instance = new KillBlock(32,32,x,y,source.getSimulation());
            System.out.println("foo");
        }
        return instance;
    }
}
