package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A rectangular platform upon which a Dorf is able to stand.
 */
public abstract class Platform extends Entity {
    double JUMP_BOOST = 0;

    public Platform(String sprite_location, int hitbox_width, int
            hitbox_height, double x, double y, Model simulation) {
        super(sprite_location, hitbox_width, hitbox_height, x, y, simulation);
        this.height = hitbox_height;
        this.width = hitbox_width;
    }

    public void makeHitbox() {
        this.hitbox = new PlatformHitbox(this.width,this.height);
    }

    public double getJump() {
        return JUMP_BOOST;
    }
}

