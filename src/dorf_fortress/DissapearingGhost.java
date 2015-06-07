package dorf_fortress;

/**
 * Created by jamie on 6/6/15.
 */
public class DissapearingGhost extends Obstacle {


    public DissapearingGhost(int hitbox_width, int hitbox_height, double x, double y, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
    }

    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite("sprites/Mummy.png",
                (int)this.width, (int)this.height, this);
    }

    @Override
    protected void makeHitbox() {
        this.hitbox = new DorfHitbox(this.width, this.height);
    }

    @Override
    public void collidesX(Entity projectile) {

    }

    @Override
    public void collidesY(Entity projectile) {

    }
}
