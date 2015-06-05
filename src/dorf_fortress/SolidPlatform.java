package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A rectangular platform upon which a Dorf is able to stand.
 */
public class SolidPlatform extends Platform {

    public SolidPlatform(int hitbox_width, int
            hitbox_height, double x, double y, Model simulation) {

        super(hitbox_width, hitbox_height, x, y, simulation);
        this.height = hitbox_height;
        this.width = hitbox_width;
        //10,000
        this.JUMP_BOOST = 18000/ GameController.FRAMES_PER_SECOND;
    }

    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleSprite("sprites/128x32platform.png", (int)this
                .width, (int)this.height,this);
    }

    public void makeHitbox() {
        this.hitbox = new RectangleHitbox(this.width,this.height);
    }
}

