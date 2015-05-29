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
    private double initial_x;
    private double initial_y;
    protected double initial_x_velocity;
    protected double initial_y_velocity;
    protected Hitbox hitbox;
    protected double height;
    protected double width;
    boolean hitbox_checker; //determines if this object enacts hitbox checking
    Model simulation;
    //if the object "dies" when if falls off the edge of the screen
    protected boolean screen_death;


    public Entity (String sprite_location, int hitbox_width, int
            hitbox_height, Model simulation, double x, double y) {
        this.sprite = new Sprite (sprite_location, hitbox_width,
                hitbox_height, this);
        this.simulation = simulation;
        hitbox_checker = false;
        this.height = hitbox_height;
        this.width = hitbox_width;
        makeHitbox();
        this.setX(x);
        this.setY(y);
        initial_x = x;
        initial_y = y;
        initial_x_velocity = x_velocity;
        initial_y_velocity = y_velocity;
        this.screen_death = false;

    }

    protected abstract void makeHitbox();

    public void reset() {
        setX(initial_x);
        setY(initial_y);
        setX_velocity(initial_x_velocity);
        setY_velocity(initial_y_velocity);

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

    /**
     * Baseline step() method for all Entities; moves the Entity along its
     * velocity, one axis at a time, and checks for collisions.
     */
    public void step() {
        //dealing with framerate issues
        double dx = this.x_velocity/GameController.FRAMES_PER_SECOND;
        this.setX(x + dx);
        handleCollisionX();
        double dy = this.y_velocity/GameController.FRAMES_PER_SECOND;
        this.setY(y - dy);
        handleCollisionY();
        if (screen_death) {
            if (isOffScreen()) {
                this.die();
            }
        }
    }

    // collision code is such that it can be overridden by subclasses if
    // necessary
    protected void handleCollisionX() {
        if (this.hitbox_checker){
            for (Entity other : simulation.getObjects()) {
                if (this.intersects(other)) {
                    other.collidesX(this);
                }
            }
        }
    }
    protected void handleCollisionY() {
        if (this.hitbox_checker){
            for (Entity other : simulation.getObjects()) {
                if (this.intersects(other)) {
                    other.collidesY(this);
                }
            }
        }
    }


    /**
     * Determines if the Entity has fallen off either the top or the bottom
     * of the screen
     */
    protected boolean isOffScreen() {
        //under the bottom first then over the top second
        if (this.y > simulation.SCENE_HEIGHT | this.y < (-this.height)) {
            return true;
        }
        return false;
    }


    /**
     * Updates the sprite's position on the screen, based on the Dorf's x
     * coordinate and therefore the relative position of the view.
     *
     * At the moment, our view doesn't move, so it's just making sure the
     * Sprite's coordinates line up with those of the Entity.
     * TODO: Figure this stuff out.
     */
    public void updateSprite() {
        this.step();
        this.sprite.update(this.simulation.player.getX());
    }

    /**
     * TODO: Jamie, you understand what this is doing. I really don't.
     * It'd be nice if you could write the comment to explain this one.
     * -Joe
     * @param e
     * @return
     */
    public boolean intersects(Entity e){
        return this.hitbox.intersects(e.hitbox);
    }

    /**
     * Runs when the character meets its demise. TODO IMPLEMENT/DEAL WITH THIS
     */
    public void die() {};



    //entity objects need to do stuff with collisions
    public abstract void collidesX(Entity projectile);
    public abstract void collidesY(Entity projectile);
}
