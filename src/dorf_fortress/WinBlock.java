package dorf_fortress;

/**
 * The endpoint of a level, touching it calls the Dorf's win method.
 * Created by azureillusions on 5/28/15.
 */
public class WinBlock extends Obstacle{

    public WinBlock(int hitbox_width, int hitbox_height,
                    double x, double y, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
    }

    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite("sprites/basicarchway.png", (int)this
                .width, (int)this.height,this);
    }

    /**
     * TODO: Make hitbox ONLY the center of the gateway!
     */
    @Override
    protected void makeHitbox() {
        this.hitbox = new PlatformHitbox(this.width,this.height);
    }

    @Override
    public void collidesX(Entity projectile) {
        if (projectile instanceof Dorf) {
            projectile.win();
        }
    }

    @Override
    public void collidesY(Entity projectile) {
        if (projectile instanceof Dorf) {
            projectile.win();
        }
    }
}
