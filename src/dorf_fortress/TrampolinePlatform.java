package dorf_fortress;

/**
 * TrampolinePlatform is a type of Platform. On collision, it inverts the
 * colliding entity's y-velocity, giving the Dorf a single chance at some truly
 * daring leaps.
 */
public class TrampolinePlatform extends Platform {

    /**
     * The constructor for TrampolinePlatform. Passes the information up the
     * inheritance tree to Platform's constructor.
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
    }

    /**
     * Sets the platform's sprite to be distinct from the normal platform.
     * @param x   The platform's x-coordinate.
     * @param y   The platform's x-coordinate.
     * @param simulation   The singleton Model running in Main.
     */
    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite(
                "sprites/basicPlatform.png", //TODO: get a TrampolinePlatform.png, use it here.
                (int)this.width,
                (int)this.height,
                this
        );
    }

    /**
     * Overrides the default collision method, giving the colliding entity the
     * inverse of its original Y velocity. TODO: MAKE VIABLE.
     * @param projectile   The entity having its velocity inverted.
     */
    @Override
    public void collidesY(Entity projectile) {
        super.collidesY(projectile);
        projectile.setY_velocity(-projectile.getY_velocity());
    }
}