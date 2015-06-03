package dorf_fortress;

import java.net.URL;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A rectangular platform upon which a Dorf is able to stand.
 */
public abstract class Platform extends Entity {
    protected double JUMP_BOOST;

    public Platform(int hitbox_width, int
            hitbox_height, double x, double y, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.height = hitbox_height;
        this.width = hitbox_width;
    }

    public void makeHitbox() {
        this.hitbox = new PlatformHitbox(this.width,this.height);
    }

    public double getJump() {
        return JUMP_BOOST;
    }

    @Override
    public void collidesX(Entity projectile) {
        // right collision - if the projectile is going faster to the right
        if (projectile.getX_velocity() > this.getX_velocity()) {
            projectile.setX(this.getX() - projectile.width - 0.01);
            projectile.setX_velocity(this.x_velocity);

            // left collision - if the object collided but was going slower than
            // the platform, know this by elimination
        } else {
            projectile.setX(this.getX() + this.width + 0.01);
            projectile.setX_velocity(this.getX_velocity());
        }
    }

    @Override
    public void collidesY(Entity projectile) {
        // top collision - if the projectile is going down relative
        if (projectile.getY_velocity() < this.getY_velocity()) {
            if (projectile instanceof Dorf) {
                ((Dorf)projectile).setCurPlatform(this);
            }
            projectile.setY(this.getY() - projectile.height - 0.01);
            projectile.setY_velocity(this.y_velocity);

            // bottom collision - if the object collided but was going slower than
            // the platform, know this by elimination
        } else {
            projectile.setY(this.getY() + this.height + 0.01);
            projectile.setY_velocity(this.getY_velocity());
        }
    }
}

