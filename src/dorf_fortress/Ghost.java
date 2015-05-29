package dorf_fortress;

/**
 * Created by jamie on 5/28/15.
 */
public class Ghost extends Dorf {
    public boolean finishedLevel;
    public double finalX;
    public int frameFinished;
    public int currentFrameCount;
    public boolean liveSimulation;
    //TODO change the security of these

    /**
     * Calls Actor's constructor with no name.
     *
     * @param image_location
     * @param hitbox_width
     * @param hitbox_height
     * @param model
     * @param x
     * @param y
     */
    public Ghost(String image_location, int hitbox_width, int hitbox_height, double x, double y,
                 Model model) {
        super(image_location, hitbox_width, hitbox_height, x, y, model);
        inputSource = GhostInputSource.getInstance();
    }

    @Override
    public void step() {
        currentFrameCount++;
        ((GhostInputSource)inputSource).nextFrame();
        super.step();
    }

    @Override
    public void die() {
        finishedLevel = true;
        finalX = this.getX();
        frameFinished = currentFrameCount;
    }

    @Override
    public void win() {
        if (liveSimulation) {
            liveSimulation = false;
            super.win();
        } else {
            finishedLevel = true;
            finalX = this.getX();
            frameFinished = currentFrameCount;
        }
    }

    @Override
    public void reset() {
        finishedLevel = false;
        currentFrameCount = 0;
        super.reset();
        inputSource.clear();
    }
}
