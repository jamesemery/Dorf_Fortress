package dorf_fortress;

/**
 * Created by jamie on 5/28/15.
 */
public class Ghost extends Dorf {


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
    }

    @Override
    public void step(){
        inputSource.addInput("right",true);
        super.step();
    }

}
