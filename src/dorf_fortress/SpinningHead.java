package dorf_fortress;

import javafx.scene.shape.Line;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by jamie on 5/31/15.
 */
public class SpinningHead extends Obstacle{
    double centerX;
    double centerY;
    double length;
    int frameOffset;
    int rotationRate; // in frames per rotation
    Sprite line;

    public SpinningHead(int hitbox_width, int hitbox_height, double x,
                         double y, int rate, double length, int offset, Model
                        simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.frameOffset = offset;
        this.rotationRate = rate;
        this.length = length;
        centerX = x;
        centerY = y;
        ((SpinningHeadSprite)sprite).setCenterX(centerX);
        ((SpinningHeadSprite)sprite).setCenterY(centerY);
    }


    @Override
    protected void makeSprite() {
//        this.sprite = new spinningHeadSprite(x,y,this);
        this.sprite = new SpinningHeadSprite("sprites/Skull.png",
                centerY, centerX, this);
    }

    @Override
    protected void makeHitbox() {
        this.hitbox = new RectangleHitbox(this.width, this.height);
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
        int currentFrame = frameOffset + simulation.getCurrentFrame();
        double angle = ((double)(currentFrame%rotationRate)/
                (double)rotationRate) * 360;
        double x = length*Math.cos(angle) + centerX;
        double y = length*Math.sin(angle) + centerY;
        this.setY(y);
        this.setX(x);
    }

    public static Obstacle getInstance(ObstaclePlacer source, Hitbox h, Random rand) {
        List<Platform> platfomrsInRange = new ArrayList<Platform>();

        // Loops through the entities and finds
        for (Entity e : source.getSimulation().getObjects()) {
            if (e instanceof Platform) {
                double xdiff = Math.abs(e.getX() - h.getX());
                double ydiff = Math.abs(e.getY() - h.getY());
                if ((ydiff < 400) && (xdiff < 400)) {
                    h.getY();
                    platfomrsInRange.add((Platform) e);
                }
            }
        }
        // Selects a random platform from the shorter list
        int n = rand.nextInt(platfomrsInRange.size());
        Platform base = platfomrsInRange.get(n);
        double y = base.getY();
        int xoffset = rand.nextInt((int) base.width);
        double x = xoffset + base.getX();
        double length = 100 + rand.nextInt(50);
        int rate = 6000 + rand.nextInt(3000);
        int offset = rand.nextInt(rate);
        SpinningHead instance = new SpinningHead(20, 20, x, y, rate, length,
                offset, source.getSimulation());
        return instance;
    }

//    private class spinningHeadSprite extends Sprite {
//        public spinningHeadSprite(double x, double y, SpinningHead spinningHead) {
//            super();
//        }
//    }
}

