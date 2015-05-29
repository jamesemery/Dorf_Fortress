package dorf_fortress;

/**
 * Created by jamie on 5/28/15.
 */
public class Ghost extends Dorf {
    public boolean finishedLevel;
    public double finalX;
    public int frameFinished;
    private int currentFrameCount;

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
    public Ghost(String image_location, int hitbox_width, int hitbox_height,
                 Model model, double x, double y) {
        super(image_location, hitbox_width, hitbox_height, model, x, y);
    }

    @Override
    public void step(){
        inputSource.addInput("right", true);
        currentFrameCount++;
        super.step();
    }

    @Override
    public void die(){
        finishedLevel = true;
        finalX = this.getX();
        frameFinished = currentFrameCount;
    }

    @Override
    public void reset(){
        finishedLevel = false;
        currentFrameCount = 0;
        super.reset();
    }
}
