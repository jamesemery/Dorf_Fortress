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
    public boolean liveSimulation;
    //TODO change the security of these

    /**
     * Calls Actor's constructor with no name.
     *
     * @param hitbox_width
     * @param hitbox_height
     * @param model
     * @param x
     * @param y
     */
    public Ghost(int hitbox_width, int hitbox_height, double x, double y,
                 Model model, InputBuffer inputSource) {
        super(hitbox_width, hitbox_height, x, y, model);
        this.inputSource = inputSource;
        inputSource.clear();
    }

    @Override
    public void step() {
        ((GhostInputSource)inputSource).nextFrame();
        super.step();
    }

    @Override
    public void die() {
        finishedLevel = true;
        finalX = this.getX();
        frameFinished = simulation.getCurrentFrame();
    }

    @Override
    public void win() {
        if (liveSimulation) {
            //reset()
            super.win();
            reset();

        } else {
            finishedLevel = true;
            finalX = this.getX();
            frameFinished = simulation.getCurrentFrame();
        }
    }

    @Override
    protected void makeSprite(double x, double y, Model simulation) {
        //TODO the ghost sprite isn't animating right now.
        String[] rightImages = {"sprites/BasicDorfRight1.png",
                "sprites/BasicDorfRight2.png","sprites/BasicDorfRight3.png"};
        String[] leftImages = {"sprites/BasicDorfLeft1.png",
                "sprites/BasicDorfLeft2.png","sprites/BasicDorfLeft3.png"};
        this.sprite = new DorfSprite(leftImages,rightImages,(int)this.width,
                (int)this.height, this);
    }

    @Override
    public void reset() {
        finishedLevel = false;
        super.reset();
    }

    public int getEndFrame() {
        return frameFinished;
    }
}
