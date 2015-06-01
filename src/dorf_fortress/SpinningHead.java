package dorf_fortress;

import java.util.Random;

/**
 * Created by jamie on 5/31/15.
 */
public class SpinningHead extends Obstacle{
    double centerX;
    double centerY;
    double lenth;
    int frameOffset;
    int rotationRate; // in frames per rotation

    private SpinningHead(int hitbox_width, int hitbox_height, double x,
                         double y, int rate, double length, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.rotationRate = rate;
        this.lenth = length;
    }

    public void setFrameOffset(int frame) {

    }

    @Override
    protected void makeSprite(double x, double y, Model simulation) {

    }

    @Override
    protected void makeHitbox() {

    }

    @Override
    public void collidesX(Entity projectile) {

    }

    @Override
    public void collidesY(Entity projectile) {

    }


    public static Obstacle getInstance(ObstaclePlacer source, Hitbox h, Random rand) {
        return null;
    }
}

