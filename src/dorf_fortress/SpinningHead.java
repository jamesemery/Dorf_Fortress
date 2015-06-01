package dorf_fortress;

import java.util.Random;

/**
 * Created by jamie on 5/31/15.
 */
public class SpinningHead extends Obstacle{
    double centerX;
    double centerY;
    double length;
    int frameOffset;
    int rotationRate; // in frames per rotation

    public SpinningHead(int hitbox_width, int hitbox_height, double x,
                         double y, int rate, double length, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.frameOffset = 0;
        this.rotationRate = rate;
        this.length = length;
        centerX = x;
        centerY = y;
    }

    public void setFrameOffset(int frame) {
        frameOffset = frame;
    }

    @Override
    protected void makeSprite(double x, double y, Model simulation) {
//        this.sprite = new spinningHeadSprite(x,y,this);
        this.sprite = new Sprite("sprites/GreyDorf.png",
                (int)this.width, (int)this.height, this);
    }

    @Override
    protected void makeHitbox() {
        this.hitbox = new PlatformHitbox(this.width, this.height);
    }

    @Override
    public void collidesX(Entity projectile) {
        projectile.die();
    }

    @Override
    public void collidesY(Entity projectile) {
        projectile.die();
    }

    @Override
    public void step() {
        int currentFrame = simulation.getCurrentFrame();
        double angle = ((double)(currentFrame%rotationRate)/
                (double)rotationRate) * 360;
        double x = length*Math.cos(angle) + centerX;
        double y = length*Math.sin(angle) + centerY;
        this.setY(y);
        this.setX(x);
    }

    public static Obstacle getInstance(ObstaclePlacer source, Hitbox h, Random rand) {
        return null;
    }

//    private class spinningHeadSprite extends Sprite {
//        public spinningHeadSprite(double x, double y, SpinningHead spinningHead) {
//            super();
//        }
//    }
}

