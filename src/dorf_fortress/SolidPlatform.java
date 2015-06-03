package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A rectangular platform upon which a Dorf is able to stand.
 */
public class SolidPlatform extends Platform {

    public SolidPlatform(int hitbox_width, int
            hitbox_height, double x, double y, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.height = hitbox_height;
        this.width = hitbox_width;
        //10,000
        this.JUMP_BOOST = 18000/ GameController.FRAMES_PER_SECOND;
    }

    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite("sprites/128x32platform.png", (int)this
                .width, (int)this.height,this);
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

    public void makeHitbox() {
        this.hitbox = new PlatformHitbox(this.width,this.height);
    }
}

