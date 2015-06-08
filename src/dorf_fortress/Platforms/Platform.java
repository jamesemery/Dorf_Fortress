package dorf_fortress;

/**
 * A rectangular surface for the Dorf or Ghost to maneuver off of. Inherits
 * from Entity, giving it a sprite and a RectangleHitbox. Has default collision
 * handling methods, which may be overwritten by its subclasses.
 *
 * Every Platform has a JUMP_BOOST constant, which contains the amount added to
 * a Dorf or Ghost's upward velocity when they try to jump off of it. This may
 * also be modified by the subclasses.
 */
public abstract class Platform extends Entity {

    protected double JUMP_BOOST = 18000/ GameController.FRAMES_PER_SECOND;

    /**
     * The constructor for Platform. Calls the constructor for Entity and lets
     * it handle everything.
     * @param hitbox_width   The width of the hitbox.
     * @param hitbox_height   The height of the hitbox.
     * @param x   The sprite's x-coordinate on the stage.
     * @param y   The sprite's y-coordinate on the stage.
     * @param simulation   The singleton Model running in Main.
     */
    public Platform(int hitbox_width,
                    int hitbox_height,
                    double x,
                    double y,
                    Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
    }

    /**
     * Constructs a RectangleHitbox with the same dimensions as the platform.
     */
    public void makeHitbox() {
        this.hitbox = new RectangleHitbox(this.width,this.height);
    }

    /**
     * A getter method for the jump boost constant.
     * @return JUMP_BOOST
     */
    public double getJump() {
        return JUMP_BOOST;
    }

    /**
     * If a collision occurs from the side, this method pushes the colliding
     * Entity back by a hair, nullifying its velocity in the process. Replaces
     * the empty method in Entity.
     * @param projectile   The colliding actor whose velocity is modified.
     */
    @Override
    public void collidesX(Entity projectile) {
        // right collision - if the projectile is going faster to the right
        if (projectile.getX_velocity() > this.getX_velocity()) {
            projectile.setX(this.getX() - projectile.width - 0.001);
            projectile.setX_velocity(this.x_velocity);

            // left collision - if the object collided but was going slower than
            // the platform, know this by elimination
        } else {
            projectile.setX(this.getX() + this.width + 0.001);
            projectile.setX_velocity(this.getX_velocity());
        }
    }

    /**
     * If a collision occurs from the top or bottom, this method pushes the
     * colliding Entity back by a hair, nullifying its velocity in the process.
     * Replaces the empty method in Entity. Allows the Dorf or Ghost to rest
     * and walk on top of the platform.
     * @param projectile   The colliding actor whose velocity is modified.
     */
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
}

