package dorf_fortress;

/**
 * The endpoint of a level, touching it calls the Dorf's win method.
 */
public class WinBlock extends Obstacle{

    /**
     * Passes everything up the constructor.
     */
    public WinBlock(int hitbox_width, int hitbox_height,
                    double x, double y, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
    }

    /**
     * Makes the appropriate sprite.
     */
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite("sprites/basicarchway.png",this);
    }

    /**
     * Makes a RectangleHitbox for the gateway.
     */
    @Override
    protected void makeHitbox() {
        this.hitbox = new RectangleHitbox(this.width,this.height);
    }

    /**
     * Overrides the collision method to call the Dorf's win() method if it
     * touches the gateway.
     * @param projectile   The Dorf or Ghost.
     */
    @Override
    public void collidesX(Entity projectile) {
        if (projectile instanceof Dorf) {
            projectile.win();
        }
    }

    /**
     * Overrides the collision method to call the Dorf's win() method if it
     * touches the gateway.
     * @param projectile   The Dorf or Ghost.
     */
    @Override
    public void collidesY(Entity projectile) {
        if (projectile instanceof Dorf) {
            projectile.win();
        }
    }
}
