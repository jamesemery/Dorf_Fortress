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
    }

    @Override
    public void collides(Entity projectile) {
        double x = projectile.getX();
        double y = projectile.getY();
        System.out.println("Collision!" + x +" "+ y);
    }
}

