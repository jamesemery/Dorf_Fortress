package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A rectangular platform upon which a Dorf is able to stand.
 */
public class Platform extends Entity {

    public Platform(String sprite_location, int hitbox_width, int
            hitbox_height, double x, double y) {
        super(sprite_location, hitbox_width, hitbox_height);
        hitbox = new DorfHitbox(x,y,hitbox_width,hitbox_height);
        this.height = hitbox_height;
        this.width = hitbox_width;
    }

    @Override
    public void collides(Entity projectile) {
        projectile.setY(368);
        //System.out.println(projectile.height);
        projectile.setY(this.getY() - projectile.height);
        projectile.setY_velocity(this.y_velocity);
        double y = projectile.getY();
    }
}

