package dorf_fortress;

/**
 * Ghost object used by the ObstacleBuilder and LevelBuilder in procedural
 * generation of the level; it executes inputs directly from the
 * GhostInputBuffer rather than user keystrokes. This allows us to mimic user
 * input in order to create a solvable level.
 */
public class Ghost extends Dorf {
    public boolean finishedLevel;
    public double finalX;
    public int frameFinished;
    public boolean liveSimulation;

    /**
     * Constructs a new Ghost, clearing the input in preparationg for the
     * simulation.
     * @param hitbox_width   The width of the Ghost's hitbox.
     * @param hitbox_height   The height of the Ghost's hitbox.
     * @param model   A reference to the model.
     * @param x   The initial X-coordinate of the Ghost.
     * @param y   The initial Y-coordinate of the Ghost.
     */
    public Ghost(int hitbox_width, int hitbox_height, double x, double y,
                 Model model, InputBuffer inputSource) {
        super(hitbox_width, hitbox_height, x, y, model);
        this.inputSource = inputSource;
        inputSource.clear();
    }

    /**
     * The step method for the Ghost. Checks for input from its InputBuffer,
     * then calls Dorf's step() method.
     */
    @Override
    public void step() {
        ((GhostInputBuffer)inputSource).nextFrame();
        super.step();
    }

    /**
     * The death method for the Ghost. This will happen during level generation.
     * The Ghost records the frame and X-coordinate at which it died.
     */
    @Override
    public void die() {
        finishedLevel = true;
        finalX = this.getX();
        frameFinished = simulation.getCurrentFrame();
    }

    /**
     * The win method for the Ghost. If the Ghost is running visibly, i.e. the
     * user clicked "show simulation", then it acts as if it were a
     * user-controlled Dorf beating the level. If not, then it finishes the
     * simulation.
     */
    @Override
    public void win() {
        if (liveSimulation) {
            super.win();
            reset();

        } else {
            finishedLevel = true;
            finalX = this.getX();
            frameFinished = simulation.getCurrentFrame();
        }
    }

    /**
     * Makes the sprite for the Ghost; it's a DorfSprite similar to that of
     * the live Dorf.
     */
    @Override
    protected void makeSprite() {
        String[] rightImages = {"sprites/MummyRight1.png",
                "sprites/MummyRight2.png","sprites/MummyRight3.png"};
        String[] leftImages = {"sprites/MummyLeft1.png",
                "sprites/MummyLeft2.png","sprites/MummyLeft3.png"};
        this.sprite = new DorfSprite(leftImages,rightImages, this);
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
