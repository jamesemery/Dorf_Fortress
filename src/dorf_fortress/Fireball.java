package dorf_fortress;

import java.util.Random;
/**
 * A lethal obstacle that moves vertically up or down the screen, looping when
 * it leaves the screen.
 */
public class Fireball extends Obstacle{
    double upwards_speed;

    /**
     * Constructor. Makes a new Fireball at the given x-coordinate with the
     * given velocity.
     * @param hitbox_width   Width of the Fireball's hitbox.
     * @param hitbox_height   Height of its hitbox.
     * @param x   X-coordinate at which to spawn the Fireball; the Y-coordinate
     *            is zero.
     * @param speed   The vertical speed of the Fireball.
     * @param simulation   A reference to the Model.
     */
    public Fireball(int hitbox_width, int hitbox_height, double
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

    /**
     * The death method for the Fireball, called when it leaves the screen; in
     * this case, it just returns to its starting position slightly offscreen.
     */
    @Override
    public void die() {
        this.reset();
    }

    /**
     * A static method that returns a Fireball with random parameters
     * @param source   The ObstaclePlacer asking for the Fireball; the method
     *                 takes some information from it.
     * @param rand   A random seed for Fireball generation.
     * @return   the Fireball object.
     */
    public static Obstacle getInstance(ObstaclePlacer source, Hitbox h,
                                       Random rand) {
        double finalX = source.getFinalX();
        int x = rand.nextInt((int) finalX);
        int speed = rand.nextInt(100) + 40;
        if (rand.nextBoolean()) {
            speed = speed * -1;
        }
        Fireball flamey = new Fireball
                (32, 32, x, speed, source.getSimulation());
        return flamey;
    }
}
