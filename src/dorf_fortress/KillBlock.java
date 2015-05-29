package dorf_fortress;

/**
 * The simplest form of an obstacle, a static block that kills the player
 * when touched
 * Created by jamie on 5/28/15.
 */
public class KillBlock extends Obstacle{

  public KillBlock(String sprite_location, int hitbox_width, int hitbox_height, Model simulation, double x, double y) {
    super(sprite_location, hitbox_width, hitbox_height, simulation, x, y);
  }

  @Override
    protected void makeHitbox() {
        this.hitbox = new PlatformHitbox(this.width,this.height);
    }

    @Override
    public void collidesX(Entity projectile) {
        projectile.die();
    }

    @Override
    public void collidesY(Entity projectile) {
        projectile.die();
    }
}
