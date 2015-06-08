package dorf_fortress;

/**
 * TrampolinePlatform is a type of Platform. On collision, it inverts the
 * colliding entity's y-velocity, giving the Dorf a chance at some truly
 * daring leaps.
 *
 * To avoid letting the player stock up on velocity and jump over half the
 * level, there is a max possible velocity when departing this platform.
 */
public class TrampolinePlatform extends Platform {
    private Double maxSpeed = 30000/GameController.FRAMES_PER_SECOND;

    /**
     * The constructor for TrampolinePlatform. Passes the information up the
     * inheritance tree to Platform's constructor. Also seriously nerfs the
     * JUMP_BOOST, because this can be very overpowered.
     * @param hitbox_width   The width of the hitbox.
     * @param hitbox_height   The height of the hitbox.
     * @param x   The sprite's x-coordinate on the stage.
     * @param y   The sprite's y-coordinate on the stage.
     * @param simulation   The singleton Model running in Main.
     */
    public TrampolinePlatform(int hitbox_width,
                              int hitbox_height,
                              double x,
                              double y,
                              Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.JUMP_BOOST = 0 / GameController.FRAMES_PER_SECOND;
    }

    /**
     * Sets the platform's sprite to be distinct from the normal platform.
     */
    @Override
    protected void makeSprite() {
        /*String[] imageArray = {"sprites/trampoline1.png", //"sprites/trampoline2.png",
                    "sprites/trampoline3.png", "sprites/trampoline4.png"};
        this.sprite = new AnimatedSprite(imageArray, 128, 32,  this, 6);*/
        this.sprite = new SimpleSprite("sprites/bounce1.png", this); //TODO: get a TrampolinePlatform.png, use it here.
    }

    /**
     * Overrides the default collision method, giving the colliding entity the
     * inverse of its original Y velocity. To avoid letting the player stock
     * up on velocity and jump over half the level, there is a max possible
     * velocity when departing this platform.
     * @param projectile   The entity having its velocity inverted.
     */
    @Override
    public void collidesY(Entity projectile) {
        // If the projectile's coming from above.
        if (projectile.getY_velocity() < this.getY_velocity()) {
            if (projectile instanceof Dorf) {
                ((Dorf)projectile).setCurPlatform(this);
            }
            projectile.setY(this.getY() - projectile.height - 0.01);
            if (-projectile.getY_velocity() < this.maxSpeed) {
                projectile.setY_velocity(-projectile.getY_velocity());
            } else {
                projectile.setY_velocity(this.maxSpeed);
            }

        // If the projectile's coming from below.
        } else {
            projectile.setY(this.getY() + this.height + 0.01);
            projectile.setY_velocity(-projectile.getY_velocity());
        }
    }
}