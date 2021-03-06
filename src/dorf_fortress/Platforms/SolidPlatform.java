package dorf_fortress;

/**
 * SolidPlatform is the default variety of platform: a rectangular platform on
 * which a Dorf is able to stand.
 */
public class SolidPlatform extends Platform {

    /**
     * The constructor for SolidPlatform. Passes the information up the
     * inheritance tree to Platform's constructor.
     *
     * @param hitbox_width   The width of the hitbox.
     * @param hitbox_height   The height of the hitbox.
     * @param x   The sprite's x-coordinate on the stage.
     * @param y   The sprite's y-coordinate on the stage.
     * @param simulation   The singleton Model running in Main.
     */
    public SolidPlatform(int hitbox_width,
                         int hitbox_height,
                         double x,
                         double y,
                         Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
    }

    /**
     * Sets the platform's sprite to the default platform sprite.
     */
    @Override
    protected void makeSprite() {
        this.sprite = new SimpleSprite("sprites/128x32platform.png", this);
    }
}

