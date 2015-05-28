package dorf_fortress;

/**
 * Created by Joe on 5/27/2015.
 */
public abstract class Entity {
    Sprite sprite;
    private double x;
    private double y;
    protected double x_velocity = 0;
    protected double y_velocity = 0;
    protected Hitbox hitbox;

    public Entity (String sprite_location, int hitbox_width, int hitbox_height) {
        this.sprite = new Sprite (sprite_location, hitbox_width, hitbox_height);
    }

    public void setX(double x) {
        this.x = x;
        hitbox.setX(x);
    }

    public void setY(double y) {
        this.y = y;
        hitbox.setY(y);
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

    public void step() {
        this.setX(x + this.x_velocity);
        this.setY(y - this.y_velocity);
    }
    public void updateSprite() {
        this.step();
        this.sprite.update(this.x, this.y);
    }

    public boolean intersects(Entity e){
        return this.hitbox.intersects(e.hitbox);
    }

    //
    public abstract void collides(Entity projectile);
}
