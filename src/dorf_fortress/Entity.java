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
    double height;
    double width;
    boolean hitbox_checker; //determines if this object enacts hitbox checking
    Model simulation;


    public Entity (String sprite_location, int hitbox_width, int
            hitbox_height, Model simulation) {
        this.sprite = new Sprite (sprite_location, hitbox_width,
                hitbox_height);
        this.simulation = simulation;
        hitbox_checker = false;

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

    public double getX_velocity(){
        return x_velocity;
    }

    public void setX_velocity(double dx){
        x_velocity = dx;
    }

    public double getY_velocity(){ return y_velocity; }

    public void setY_velocity(double dy){
        y_velocity = dy;
    }

    public double getY() {
        return this.y;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void step() {
        //dealing with framerate issues
        double dx = this.x_velocity/GameController.FRAMES_PER_SECOND;
        this.setX(x + dx);
        if (this.hitbox_checker){
            for (Entity other : simulation.getObjects()) {
                if (this.intersects(other)) {
                    other.collidesX(this);
                }
            }
        }
        double dy = this.y_velocity/GameController.FRAMES_PER_SECOND;
        this.setY(y - dy);
        if (this.hitbox_checker){
            for (Entity other : simulation.getObjects()) {
                if (this.intersects(other)) {
                    other.collidesY(this);
                }
            }
        }

    }

    public void updateSprite() {
        this.step();
        this.sprite.update(this.x, this.y);
    }

    public boolean intersects(Entity e){
        return this.hitbox.intersects(e.hitbox);
    }

    //entity objects need to do stuff with collisions
    public abstract void collidesX(Entity projectile);
    public abstract void collidesY(Entity projectile);
}
