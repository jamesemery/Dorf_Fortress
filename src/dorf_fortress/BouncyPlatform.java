package dorf_fortress;

/**
 * BouncyPlatform is a type of Platform. It provides a jump boost of almost
 * twice the usual, allowing for more maneuvering.
 */
public class BouncyPlatform extends Platform {

    /**
     * The constructor for BouncyPlatform. Notably, it overrides the default
     * value for JUMP_BOOST.
     * @param hitbox_width   The width of the hitbox.
     * @param hitbox_height   The height of the hitbox.
     * @param x   The sprite's x-coordinate on the stage.
     * @param y   The sprite's y-coordinate on the stage.
     * @param simulation   The singleton Model running in Main.
     */
    public BouncyPlatform(int hitbox_width,
                          int hitbox_height,
                          double x,
                          double y,
                          Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.JUMP_BOOST = 30000/ GameController.FRAMES_PER_SECOND;
    }

    /**
     * Sets the platform's sprite to be distinct from the normal platform.
     * @param x   The platform's x-coordinate.
     * @param y   The platform's x-coordinate.
     * @param simulation   The singleton Model running in Main.
     */
    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite("sprites/basicPlatform.png", this);
    }
}

