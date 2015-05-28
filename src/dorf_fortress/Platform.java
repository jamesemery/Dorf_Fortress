package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A rectangular platform upon which a Dorf is able to stand.
 */
public class Platform extends Entity {

    public Platform(String sprite_location, int hitbox_width, int
            hitbox_height, double x, double y, Model simulation) {
        super(sprite_location, hitbox_width, hitbox_height, simulation);
        hitbox = new PlatformHitbox(x,y,hitbox_width,hitbox_height);
        this.height = hitbox_height;
        this.width = hitbox_width;
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void collidesY(Entity projectile) {
        // top collision - if the projectile is going faster to the right
        if (projectile.getY_velocity() < this.getY_velocity()) {
            if (projectile instanceof Actor) {
                ((Actor) projectile).setOnPlatform(true);
            }
            projectile.setY(this.getY() - projectile.height - 0.1);
            projectile.setY_velocity(this.y_velocity);

        //bottom collision - if the object collided but was going slower than
        // the platform, know this by elimination
        } else {
            projectile.setY(this.getY() + this.height + 0.1);
            projectile.setY_velocity(this.getY_velocity());
        }
    }

    @Override
    public void collidesX(Entity projectile) {
        // top collision - if the projectile is going faster to the right
        if (projectile.getX_velocity() > this.getX_velocity()) {
            projectile.setX(this.getX() - projectile.width - 0.1);
            projectile.setX_velocity(this.x_velocity);

            //bottom collision - if the object collided but was going slower than
            // the platform, know this by elimination
        } else {
            projectile.setX(this.getX() + this.width + 0.1);
            projectile.setX_velocity(this.getX_velocity());
        }
    }
}

