package dorf_fortress;

/**
 * Created by Joe on 5/27/2015.
 */
public class Entity {
    Sprite sprite;
    //Hitbox hitbox;
    double x;
    double y;

    public Entity (String sprite_location, int hitbox_width, int hitbox_height) {
        this.sprite = new Sprite (sprite_location, hitbox_width, hitbox_height);
        //this.hitbox = new Hitbox(some arguments or something);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void updateSprite() {
        this.sprite.update(this.x, this.y);
    }
}
