package dorf_fortress;

import java.util.Random;

/**
 * Class for a spider obstacle that moves up for a little while, waits for a
 * moment, then moves down for a little while, then waits for a moment and
 * then repeats the process
 * Created by jamie on 6/5/15.
 */
public class OscillatingSpider extends Obstacle{
    private int waitFrames; // How many frames the spider holds its position for
    private int moveFrames;
    private int currentFrame;
//    private int offsetFrame; TODO ask if it is neccisary
    private double moving_x_velocity;
    private double moving_y_velocity;

    public OscillatingSpider(int hitbox_width, int hitbox_height, double x,
                             double y, double x_velocity, double y_velocity,
                             int waitFrames,int moveFrames, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.waitFrames = waitFrames;
//        this.offsetFrame = offset;
        this.moveFrames = moveFrames;
        this.moving_x_velocity = x_velocity*GameController.FRAMES_PER_SECOND;
        this.moving_y_velocity = y_velocity*GameController.FRAMES_PER_SECOND;
    }

    @Override
    protected void makeSprite() {
        //this.sprite = new SimpleSprite("sprites/BasicSpider.png", 35, 35, this);
        String[] imageList = {"sprites/SpiderForward1.png",
                "sprites/SpiderForward2.png","sprites/SpiderForward3.png",
                "sprites/SpiderForward4.png", "sprites/SpiderForward4.png",
                "sprites/SpiderForward5.png", "sprites/SpiderForward6.png",
                "sprites/SpiderForward7.png", "sprites/SpiderForward8.png",
                "sprites/SpiderForward9.png", "sprites/SpiderForward10.png"};
        this.sprite = new AnimatedSprite(imageList, 35, 35, this, 5 );
    }

    @Override
    protected void makeHitbox() {
        this.hitbox = new RectangleHitbox(35, 35);
    }

    /**
     * For each frame determines what the velocity should be and sets it to
     * that velocity. Works by determining what part of the cycle the object
     * is currently in and then sets velocity, (0 for first phase, positive
     * values for second phase, 0 for third phase, and negative values for
     * last phase)
     */
    @Override
    public void step() {
        int totalCycleLength = 2*waitFrames + 2*moveFrames;
        int frameInCycle = (currentFrame)%totalCycleLength;
        ((AnimatedSprite)this.sprite).setAnimationState(true);
        if (frameInCycle<waitFrames) {
            setX_velocity(0);
            setY_velocity(0);
            ((AnimatedSprite)this.sprite).setAnimationState(false);
        } else if (frameInCycle<(waitFrames+moveFrames)) {
            setX_velocity(moving_x_velocity);
            setY_velocity(moving_y_velocity);
        } else if (frameInCycle<((2*waitFrames)+moveFrames)) {
            setX_velocity(0);
            setY_velocity(0);
            ((AnimatedSprite)this.sprite).setAnimationState(false);
        } else {
            setX_velocity(-1 * moving_x_velocity);
            setY_velocity(-1*moving_y_velocity);
        }
        currentFrame++;
        super.step();
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
    public void reset() {
        currentFrame = 0;
        super.reset();
    }

    /**
     * Gets a specific instance of this object given an ObstaclePlacer object
     * and a given hitbox. Will place the spider such that it is not
     * currently intersecting the dorf and so it will have a random movement
     */
    public static Obstacle getInstance(ObstaclePlacer source, Hitbox h, Random rand) {
        // first finds the stariting x position and then keeps generating y
        // positions until it at least 100 away from the level edges
        double xStart = h.getX() + rand.nextInt(200) - 100;
        double yStart = 0;
        while ((yStart<100)||(yStart>(source.getSimulation()
                .SCENE_HEIGHT-100))) {
            yStart = h.getY() + rand.nextInt(200) - 100;
        }
        int pauseFrames = 10 + rand.nextInt(60);
        int moveFrames = 80 + rand.nextInt(50);

        // Choses a distance for the object to move then an angle and uses
        // trigonometry to choose an angle and final position. Then makes
        // sure that the ending y is on the screen properly and recalculates
        // if necissary;
        int distance = 150 + rand.nextInt(250);
        int angle = rand.nextInt(361);
        double endY = yStart - distance*Math.sin(angle);
        while ((endY<0)||
                (endY>source.getSimulation().SCENE_HEIGHT -32)) {
            distance = 100 + rand.nextInt(300);
            angle = rand.nextInt(361);
            endY = yStart - distance*Math.sin(angle);
        }

        double x_velocity = ((double)distance/(double)moveFrames)*Math.cos
                (angle);
        double y_velocity = ((double)distance/(double)moveFrames)*Math.sin
                (angle);

        // 50% chance to flip the starting point for the spider
        if (0.50>rand.nextDouble()) {
            x_velocity = -1*x_velocity;
            y_velocity = -1*y_velocity;
            xStart = xStart + distance*Math.cos(angle);
            yStart = endY;
        }

        Obstacle instance = new OscillatingSpider(32, 32, xStart, yStart,
                x_velocity,y_velocity, pauseFrames, moveFrames, source
                .getSimulation());
        return instance;


    }

}
