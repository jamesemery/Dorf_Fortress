package dorf_fortress;

/**
 * Interface class for all of the simulation components in the code. This
 * interface contains a constructor and simulation code for basic simulation
 * components, with the expectation that these methods are overridden by
 * subclasses when behavior is particularly complicated. Also entails a
 * series of getters and setters for the general variables that every Entity
 * will have.
 */
public abstract class Entity {
    public Sprite sprite;
    private double x;
    private double y;
    protected double x_velocity = 0;
    protected double y_velocity = 0;
    protected double initial_x;
    protected double initial_y;
    protected double initial_x_velocity;
    protected double initial_y_velocity;
    protected Hitbox hitbox;
    protected double height;
    protected double width;
    boolean hitbox_checker; //determines if this object enacts hitbox checking
    public Model simulation;
    //if the object "dies" when if falls off the edge of the screen
    protected boolean screen_death;

    /**
     * Constructor. Creates a new Entity with the given properties, and
     * several defaults.
     * @param hitbox_width   Width of the Entity's hitbox.
     * @param hitbox_height   Height of the Entity's hitbox.
     * @param x   Starting X-coordinate.
     * @param y   Starting Y-coordinate.
     * @param simulation   A reference to the model.
     */
    public Entity (int hitbox_width, int hitbox_height, double x, double y,
                   Model simulation) {
        this.simulation = simulation;
        hitbox_checker = false;
        this.height = hitbox_height;
        this.width = hitbox_width;
        makeSprite();
        makeHitbox();
        this.setX(x);
        this.setY(y);
        initial_x = x;
        initial_y = y;
        initial_x_velocity = x_velocity;
        initial_y_velocity = y_velocity;
        this.screen_death = false;

    }

    protected abstract void makeSprite();

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
     * velocity, one axis at a time, and checks for collisions. Segments
     * movement into x collisions first then y collision so that the
     * collision handler knows where the object came from
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

    /**
     * Default collision code; this can be overridden by subclasses as
     * necessary. Simply checks for collision with all other entities in the
     * scene.
     */
    protected void handleCollisionX() {
        if (this.hitbox_checker){
            for (Entity other : simulation.getObjects()) {
                if (this.intersects(other)) {
                    other.collidesX(this);
                }
            }
        }
    }

    /**
     * Default collision code; this can be overridden by subclasses as
     * necessary. Simply checks for collision with all other entities in the
     * scene.
     */
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
        return (this.y > simulation.SCENE_HEIGHT | this.y < (-this.height));
    }

    /**
     * Checks if the Entity intersects with another Entity
     * @param e   The other Entity
     * @return   Whether or not they intersect.
     */
    public boolean intersects(Entity e){
        return this.hitbox.intersects(e.hitbox);
    }

    /**
     * Runs when the character meets its demise.
     */
    public void die() {};

    /**
     * Runs when the character wins the game.
     */
    public void win() {};


    //All Entities need to have collision methods.
    public abstract void collidesX(Entity projectile);
    public abstract void collidesY(Entity projectile);

    public Hitbox getHitbox() {
        return hitbox;
    }
}
