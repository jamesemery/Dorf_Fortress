package dorf_fortress;

/**
 * ConveyorPlatform is a type of platform. It adds rightwards x velocity while
 * the Dorf is on it, leading to some pretty cool moves.
 */

public class ConveyorPlatform extends Platform {
    private int numMade;
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
                            int numToMake,
                            double x,
                            double y,
                            Model simulation) {
        super(numToMake * hitbox_width, hitbox_height, x, y, simulation);
        numMade = numToMake;
        makeSprite();
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
     * Sets the conveyors sprite to be a ConveyorPlatformSprite object.
     */
    @Override
    protected void makeSprite() {
        String[] images = {"sprites/conveyer1.png", "sprites/conveyer2.png",
                "sprites/conveyer3.png", "sprites/conveyer4.png"};
        this.sprite = new ConveyorPlatformSprite(images,96, numMade, this, 3);
        sprite.setX(getX());
    }
}

