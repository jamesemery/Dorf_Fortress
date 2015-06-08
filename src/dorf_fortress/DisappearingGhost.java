package dorf_fortress;

import java.util.Random;

/**
 * This obstacle is a ghost that appears and disappears from the screen
 * gradually. When the ghost is not visible it can't hurt the dorf but when
 * it is visible it can hurt the dorf. It has two hang times, one for being
 * fully visible and one for being completely invisible as well as a total
 * cycle length where it loops through being corporial and incorporial.
 */
public class DisappearingGhost extends Obstacle implements OpacityChanger {
    private int dangerFrameEnd;
    private int goneFrameStart;
    private int goneFrameEnd;
    private int cycleLength;
    private double currentOpacity;
    private int offset;


    /**
     * Builds a new object and uses the total cycle length to determine when
     * the start and end of each framestate must be.
     *
     * @param hitbox_width width of the object
     * @param hitbox_height height of the object
     * @param x starting x coordinate of the object
     * @param y starting y coordinate of the object
     * @param framesDangerous length of time (in framse) the object is
     *                        completely visible
     * @param framesGone length of time (in frames) the obstacle is supposed
     *                   to be completely invisible
     * @param totalCycle the total length of the cycle between visibility and
     *                   invisiblity NOTE: this must be greater than the
     *                   total of frames dangerous and frames gone
     * @param offset cycle offset for when the animation starts
     * @param simulation the model that owns the object
     */
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

    /**
     * Step method that updates the current opacity of the object based on
     * its position in the cycle and then moves the hitbox to an unreachable
     * location if it is below a certian visiblity.
     *
     * Cycle:
     * -visible
     * -becoming invisible
     * -invisible
     * -becoming visible
     */
    @Override
    public void step() {
        int currentFrame = (simulation.getCurrentFrame()+offset) % cycleLength;

        // If the ghost is fully visible
        if (currentFrame < dangerFrameEnd) {
            currentOpacity = 1;

        // If the ghost is between being visible and invisible
        } else if (currentFrame <= goneFrameStart) {
            double difference = goneFrameStart - dangerFrameEnd;
            double opacityGradient = ((double) currentFrame - dangerFrameEnd)
                    / difference;
            currentOpacity = 1.0 - opacityGradient;

            // If the ghost is supposed to be invisible
        } else if (currentFrame < goneFrameEnd) {
            currentOpacity = 0.0;

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
    protected void makeSprite() {
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


    /**
     * Gets an instance of a DisappearingGhost that is within a small area of
     * the given hitbox and randomly sets the cycle parameters to within a
     * playable range using the random generator provided.
     *
     * @param source the ObstaclePlacer that has needed generation variables
     * @param target the target hitbox that the ghost is placing itself against
     * @param rand Random object that is used for generation
     * @return a ghost that is close to the dorf's hitbox at a particular frame
     */
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
