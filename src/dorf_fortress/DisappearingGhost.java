package dorf_fortress;

import com.oracle.webservices.internal.api.message.DistributedPropertySet;

import java.util.Random;

/**
 * TODO COMMENT THIS
 * Created by jamie on 6/6/15.
 */
public class DisappearingGhost extends Obstacle implements OpacityChanger {
    private int dangerFrameEnd;
    private int goneFrameStart;
    private int goneFrameEnd;
    private int cycleLength;
    private double currentOpacity;
    private int offset;


    public DisappearingGhost(int hitbox_width, int hitbox_height, double x, double y,
                             int framesDangerous, int framesGone, int
                                     totalCycle, int offset, Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        this.dangerFrameEnd = framesDangerous;
        this.cycleLength = totalCycle;
        int remander = cycleLength-framesDangerous - framesGone;
        goneFrameStart = (remander/2) + dangerFrameEnd;
        goneFrameEnd = goneFrameStart + framesGone;
        this.offset = offset;
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void step() {
        int currentFrame = (simulation.getCurrentFrame()+offset) % cycleLength;

        // If the ghost is fully visible
        if (currentFrame < dangerFrameEnd) {
            currentOpacity = 1;

        // If the ghost is between being visible and invisible
        } else if (currentFrame <= goneFrameStart) {
            double difference = goneFrameStart - dangerFrameEnd;
            double opacityGradient = ((double)currentFrame - dangerFrameEnd)
                    /difference;
            currentOpacity = 1.0 - opacityGradient;

        // If it is appearing during the last phase of the cycle
        } else if (currentFrame > goneFrameEnd) {
            double difference = cycleLength - goneFrameEnd;
            double opacityGradient = ((double)currentFrame - goneFrameEnd)
                    /difference;
            currentOpacity = opacityGradient;
        }

        // If its below a certian visiblity its hitbox is unreachable
        if (currentOpacity < .60) {
            hitbox.setY(simulation.SCENE_HEIGHT + 60);
        } else {
            hitbox.setY(getY());
        }
    }

    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleOpacitySprite("sprites/Mummy.png", this);
    }

    @Override
    protected void makeHitbox() {
        this.hitbox = new DorfHitbox(this.width, this.height);
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
    public double getOpacity() {
        return currentOpacity;
    }


    public static DisappearingGhost getInstance(ObstaclePlacer source, Hitbox
            target, Random rand) {
        double xTarget = target.getX();
        double yTarget = target.getY();
        double x = rand.nextInt(150);
        double y = rand.nextInt(100);
        if (rand.nextBoolean()) {
            x = -1 * x;
        }
        if (rand.nextBoolean()) {
            y = -1 * y;
        }
        x += xTarget;
        y += yTarget;
        int framesDangerous = 50 + rand.nextInt(100);
        int framesGone = 50 + rand.nextInt(100);
        int cycleLength = framesDangerous + framesGone + 120;
        int offset = rand.nextInt(cycleLength);

        DisappearingGhost instance = new DisappearingGhost(32, 32, x, y,
                framesDangerous, framesGone, cycleLength, offset,
                source.getSimulation());

        return instance;
    }
}
