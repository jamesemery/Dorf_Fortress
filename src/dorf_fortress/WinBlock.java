package dorf_fortress;

/**
 * The endpoint of a level, touching it calls the Dorf's win method.
 * Created by azureillusions on 5/28/15.
 */
public class WinBlock extends Obstacle{

    public WinBlock(String sprite_location, int hitbox_width, int hitbox_height,
                    double x, double y, Model simulation) {
        super(sprite_location, hitbox_width, hitbox_height, x, y, simulation);
    }

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
