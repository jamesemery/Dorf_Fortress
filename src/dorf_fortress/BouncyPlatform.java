package dorf_fortress;

import java.net.URL;

/**
 * Created by Michael Stoneman on 5/28/2015.
 * A platform that boosts jumps and makes standing still impossible.
 */
public class BouncyPlatform extends Platform {


    public BouncyPlatform(int hitbox_width, int hitbox_height, double x,
                          double y, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.height = hitbox_height;
        this.width = hitbox_width;
        this.JUMP_BOOST = 22500/ GameController.FRAMES_PER_SECOND;
    }
    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite("sprites/basicPlatform.png", (int)this
                .width, (int)this.height,this);
    }

    @Override
    public void collidesY(Entity projectile) {
        super.collidesY(projectile);
        //Gives the dorf a slight bounce when he's on the platform.
        projectile.setY_velocity(projectile.getY_velocity() +7500/GameController.FRAMES_PER_SECOND);
    }

    public void makeHitbox() {
        this.hitbox = new PlatformHitbox(this.width,this.height);
    }
}

