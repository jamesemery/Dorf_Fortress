package dorf_fortress;

import java.util.Random;
/**
 * Created by jamie on 5/28/15.
 */
public class SimpleUpwardsKillBall extends Obstacle{
    double upwards_speed;

    public SimpleUpwardsKillBall(int hitbox_width, int hitbox_height, double
            x, double speed, Model simulation) {
        super(hitbox_width, hitbox_height, x, simulation.SCENE_HEIGHT,
                simulation);
        upwards_speed = speed;
        if (speed < 0) {
            this.setY(0);
            this.initial_y = this.getY();
        }
        this.initial_y_velocity = speed;
        setY_velocity(upwards_speed);
        screen_death = true;
    }

    @Override
    protected void makeSprite() {
        this.sprite = new SimpleSprite("sprites/fireball.png", this);
    }

    @Override
    protected void makeHitbox() {
        this.hitbox = new RectangleHitbox(this.width, this.height);
    }

    @Override
    public void collidesX(Entity projectile) {
        projectile.die();
    }

    @Override
    public void collidesY(Entity projectile) {
        projectile.die();
    }

    @Override
    public void die() {
        this.reset();
    }

    // This class is agnostic to the specific hitbox it is passed.
    public static Obstacle getInstance(ObstaclePlacer source, Hitbox h,
                                       Random rand) {
        double finalX = source.getFinalX();
        int x = rand.nextInt((int) finalX);
        int speed = rand.nextInt(100) + 40;
        if (rand.nextBoolean()) {
            speed = speed * -1;
        }
        SimpleUpwardsKillBall jumpy = new SimpleUpwardsKillBall
                (32, 32, x, speed, source.getSimulation());
        return jumpy;
    }
}
