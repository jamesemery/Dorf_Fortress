package dorf_fortress;

/**
 * FadingPlatform is a variety of platform that fades in and out of existence,
 * as does its hitbox. Dorfs beware!
 */
public class FadingPlatform extends Platform {


    /**
     * The constructor for FadingPlatform. Passes the information up the
     * inheritance tree to Platform's constructor.
     * @param hitbox_width   The width of the hitbox.
     * @param hitbox_height   The height of the hitbox.
     * @param x   The sprite's x-coordinate on the stage.
     * @param y   The sprite's y-coordinate on the stage.
     * @param simulation   The singleton Model running in Main.
     */
    public FadingPlatform(int hitbox_width,
                         int hitbox_height,
                         double x,
                         double y,
                         Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
    }

    /**
     * Sets the platform's sprite to the default sprite
     * @param x   The platform's x-coordinate.
     * @param y   The platform's x-coordinate.
     * @param simulation   The singleton Model running in Main.
     */
    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite(
                "sprites/128x32platform.png",
                (int)this.width,
                (int)this.height,
                this
        );
    }
}