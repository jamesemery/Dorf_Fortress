package dorf_fortress;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * SpinningHead is perhaps the most annoying obstacle. It consists of a head,
 * which kills the player, and a hitbox-less chain, one end of which is
 * connected to the head and the other to a platform.
 */
public class SpinningHead extends Obstacle{
    double centerX;
    double centerY;
    double length;
    int frameOffset;
    int rotationRate; // in frames per rotation
    boolean goingRight;

    /**
     * A constructor for a spinning head obstacle, passes parameters up the
     * inheritance tree and accepts its own movement parameters.
     *
     * @param hitbox_width Width of the hitbox for the object
     * @param hitbox_height Height of the hitbox for the object
     * @param x Starting x position for the skull head
     * @param y Starting y position for the skull head
     * @param rate Number of frames to complete one rotation around its center
     * @param length Length of the rope tying the skull to the platform
     * @param offset Frame offset from running frame for rotation
     * @param direction True for clockwise spinning, false for counterclockwise
     * @param simulation the model that owns this object
     */
    public SpinningHead(int hitbox_width, int hitbox_height, double x,
                         double y, int rate, double length, int offset,
                        boolean direction, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.frameOffset = offset;
        this.rotationRate = rate;
        this.length = length;
        this.centerX = x;
        this.centerY = y;
        this.goingRight = direction;
        ((SpinningHeadSprite)sprite).setCenterX(this.centerX);
        ((SpinningHeadSprite)sprite).setCenterY(this.centerY);
    }


    @Override
    protected void makeSprite() {
        this.sprite = new SpinningHeadSprite(this, this.centerX, this.centerY);
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

    /**
     * Calculates the position that the head should be at any particular
     * frame based on the current simulation frame and the offset and uses
     * sine and cosine calculations to determine the offset from the
     * rotation center the skull should be and moves it there.
     */
    @Override
    public void step() {
        int currentFrame = frameOffset + simulation.getCurrentFrame();
        double angle = ((double)(currentFrame%rotationRate)/
                (double)rotationRate) * 360;
        double x = length*Math.cos(angle) + this.centerX;
        double y = length*Math.sin(angle) + this.centerY;
        if (goingRight) {
            x = this.centerX - length*Math.cos(angle);
        }
        this.setY(y);
        this.setX(x);
    }

    /**
     * Places a spinning head object with the base centered on a random
     * platform within a 400 pixels in either direction of the player then
     * then randomly selects a length and rate of rotation as well as an
     * offset ensure they don't all start in the same orientation.
     *
     * @param source
     * @param h
     * @param rand random number generator
     * @return
     */
    public static Obstacle getInstance(ObstaclePlacer source, Hitbox h, Random rand) {
        List<Platform> platfomrsInRange = new ArrayList<Platform>();

        // Loops through the entities and finds a platform to latch n to
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

        // Selects parameters for the object
        double y = base.getY();
        int xoffset = rand.nextInt((int) base.width);
        double x = xoffset + base.getX();
        double length = 100 + rand.nextInt(50);
        int rate = 6000 + rand.nextInt(3000);
        int offset = rand.nextInt(rate);
        boolean direction = rand.nextBoolean();
        SpinningHead instance = new SpinningHead(20, 20, x, y, rate, length,
                offset,direction, source.getSimulation());
        return instance;
    }

}

