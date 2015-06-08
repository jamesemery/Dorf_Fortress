package dorf_fortress;

/**
 * FadingPlatform is a variety of Platform that fades in and out of existence
 * (by means of OpacityChanger), as does its hitbox. Dorfs beware!
 */
public class FadingPlatform extends Platform implements OpacityChanger{
    private double currentOpacity;
    private int endVisibleFrame = 120;
    private int startInvisibleFrame = 180;
    private int endInvisibleFrame = 240;
    private int cycleLength = 300;
    private int offset;

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
     * @param targetFrame   The frame during which the ghost reaches the
     *                      platform. It must be visible then.
     * @param simulation   The singleton Model running in Main.
     */
    public FadingPlatform(int hitbox_width,
                          int hitbox_height,
                          double x,
                          double y,
                          Model simulation,
                          int targetFrame) {
        super(hitbox_width, hitbox_height, x, y, simulation);

        // Sets the offset so the cycle will start 15 frames before the
        // platform disappears
        int partOfCycle = targetFrame%300;
        this.offset = 300 - (partOfCycle-15)%300;
    }

    /**
     * Sets the platform's sprite to the default platform sprite.
     */
    @Override
    public void makeSprite() {
        this.sprite = new SimpleOpacitySprite(
                "sprites/128x32platform.png", this);
    }


    /**
     * Cycles the opacity of the platform based on the current part of the
     * cycle, with the first block being completely visible, the second chunk
     * linearly transitioning to zero opacity, and the fourth chunk increasing
     * the opacity. If the opacity is below a certain level, the hitbox is
     * moved to beneath the stage.
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

        // If it's below a certain visibility, its hitbox is unreachable.
        if (currentOpacity < .30) {
            hitbox.setY(simulation.SCENE_HEIGHT + 60);
        } else {
            hitbox.setY(getY());
        }
    }

    /*
     * Setters and getters.
     */
    @Override
    public double getOpacity() {
        return currentOpacity;
    }
}