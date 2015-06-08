package dorf_fortress;

/**
 * FadingPlatform is a variety of platform that fades in and out of existence,
 * as does its hitbox. Dorfs beware!
 */
public class FadingPlatform extends Platform implements OpacityChanger{
    double currentOpacity;
    int endVisibleFrame;
    int startInvisibleFrame;
    int endInvisibleFrame;
    int cycleLength;
    int offset;

    /**
     * The constructor for FadingPlatform. Passes the information up the
     * inheritance tree to Platform's constructor. Accepts constructor input
     * that allows it to build platforms and it also accepts a frame for the
     * platform to be visible for and makes sure the platform is there for
     * that time
     * @param hitbox_width   The width of the hitbox.
     * @param hitbox_height   The height of the hitbox.
     * @param x   The sprite's x-coordinate on the stage.
     * @param y   The sprite's y-coordinate on the stage.
     * @param simulation   The singleton Model running in Main.
     */
    public FadingPlatform(int hitbox_width,
                         int hitbox_height,
                         double x,
                         double y,
                          int framesToExist,
                          int framesToBeGone,
                          int cycleLength,
                          int targetFrame,
                          Model simulation) {
        super(hitbox_width, hitbox_height, x, y, simulation);
        endVisibleFrame = framesToExist;
        int fadeLength = (cycleLength - framesToBeGone -framesToExist) / 2;
        startInvisibleFrame = fadeLength + endVisibleFrame;
        endInvisibleFrame = startInvisibleFrame + framesToBeGone;
        this.cycleLength = cycleLength;

        // Sets the offset so the cycle will start 15 frames before the
        // platform dissapears
        int partOfCycle = targetFrame%cycleLength;
        offset = cycleLength - (partOfCycle-15)%cycleLength;
    }

    @Override
    public void makeSprite(double x, double y, Model simulation) {
        this.sprite = new SimpleOpacitySprite(
                "sprites/128x32platform.png", this);
    }


    /**
     * Cycles the opacity of the platform based on the current part of the
     * cycle, with the first block being completly visible, the second chunk
     * linearly transitioning to zero opacity, and the foruth chunk setting
     * the opacity back up. If the opacity is below a certian level it sets
     * the hitbox to be under the stage.
     */
    @Override
    public void step() {
        int currentFrame = (simulation.getCurrentFrame()+offset) % cycleLength;

        // If the ghost is fully visible
        if (currentFrame < endVisibleFrame) {
            currentOpacity = 1;

            // If the ghost is between being visible and invisible
        } else if (currentFrame <= startInvisibleFrame) {
            double difference = startInvisibleFrame - endVisibleFrame;
            double opacityGradient = ((double)currentFrame - endVisibleFrame)
                    /difference;
            currentOpacity = 1.0 - opacityGradient;

            // If it is appearing during the last phase of the cycle
        } else if (currentFrame > endVisibleFrame) {
            double difference = cycleLength - endInvisibleFrame;
            double opacityGradient = ((double)currentFrame - endInvisibleFrame)
                    /difference;
            currentOpacity = opacityGradient;
        }
        // If its below a certian visiblity its hitbox is unreachable
        if (currentOpacity < .30) {
            hitbox.setY(simulation.SCENE_HEIGHT + 60);
        } else {
            hitbox.setY(getY());
        }
    }


    @Override
    public double getOpacity() {
        return currentOpacity;
    }
}