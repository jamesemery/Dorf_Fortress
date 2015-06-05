package dorf_fortress;

/**
 * Created by azureillusions on 6/4/15.
 */
public class TrampolinePlatform extends Platform {

    public TrampolinePlatform(int hitbox_width, int hitbox_height, double x,
                          double y, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.height = hitbox_height;
        this.width = hitbox_width;
        this.JUMP_BOOST = 22500/ GameController.FRAMES_PER_SECOND;
    }

    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite(
                "sprites/basicPlatform.png", //TODO: get a TrampolinePlatform.png, use it here.
                (int)this.width,
                (int)this.height,
                this
        );
    }

    @Override
    public void collidesY(Entity projectile) {
        super.collidesY(projectile);
        //Gives the dorf a slight bounce when he's on the platform.
        projectile.setY_velocity(-projectile.getY_velocity());
    }
}
