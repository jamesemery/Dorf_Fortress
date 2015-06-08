package dorf_fortress;

/**
 * ConveyorPlatform is a type of platform. It adds x velocity while the Dorf is
 * on it, leading to some pretty cool moves.
 */

public class ConveyorPlatform extends Platform {

    /**
     * The constructor for ConveyorPlatform. Passes the information up the
     * inheritance tree to Platform's constructor.
     *
     * @param hitbox_width   The width of the hitbox.
     * @param hitbox_height   The height of the hitbox.
     * @param x   The sprite's x-coordinate on the stage.
     * @param y   The sprite's y-coordinate on the stage.
     * @param simulation   The singleton Model running in Main.
     */
    public ConveyorPlatform(int hitbox_width,
                            int hitbox_height,
                            double x,
                            double y,
                            Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
    }

    /**
     * If a collision occurs from the top or bottom, this method pushes the
     * colliding Entity back by a hair, nullifying its velocity in the process.
     * Replaces the empty method in Entity.
     *
     * As this is called while the Dorf or Ghost is standing or walking on the
     * platform, this modifies its x velocity appropriately.
     * @param projectile   The colliding actor whose velocity is modified.
     */
    @Override
    public void collidesY(Entity projectile) {
        Double uppedSpeed = (projectile.getX_velocity()
                            + 500/GameController.FRAMES_PER_SECOND);
        projectile.setX_velocity(uppedSpeed);
        super.collidesY(projectile);
    }

    /**
     * Sets the platform's sprite to the default sprite
     * @param x   The platform's x-coordinate.
     * @param y   The platform's x-coordinate.
     * @param simulation   The singleton Model running in Main.
     */
    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        //these are in reverse order because I screwed up the sprites, and
        //it was easier to change the order here than to reorder the files.
        String[] images = {"sprites/conveyer1.png", "sprites/conveyer2.png",
                "sprites/conveyer3.png", "sprites/conveyer4.png"};
        this.sprite = new AnimatedSprite(images, (int) this.width,
                (int) this.height, this, 3);
    }
}
