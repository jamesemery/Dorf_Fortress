package dorf_fortress;

/**
 * Ghost object is used by the ObstacleBuilder and LevelBuilder in procedural
 * generation of the level by being able to execute inputs directly from the
 * GhostInputSource where inputs can be easily mimic those of the user.
 *
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
        inputSource = new GhostInputSource();
        inputSource.clear();
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
        currentFrameCount = 0;
    }

    @Override
    public void win() {
        if (liveSimulation) {
            liveSimulation = false;
            reset();
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
    }
}
