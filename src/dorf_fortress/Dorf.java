package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Controllable hero of the game; subclass of Actor.
 */
public class Dorf extends Actor {
    double STEP_SIZE = 1;
    String name;
    InputBuffer inputSource;

    /**
     * Calls Actor's constructor with no name.
     * @param image_location
     * @param hitbox_width
     * @param hitbox_height
     */
    public Dorf(String image_location, int hitbox_width, int hitbox_height) {
        super(image_location, hitbox_width, hitbox_height);
        inputSource = InputBuffer.getInstance();
    }

    /**
     * Calls Actor's constructor with a name.
     * @param image_location
     * @param hitbox_width
     * @param hitbox_height
     * @param name
     */
    public Dorf(String image_location, int hitbox_width, int hitbox_height,
                String name) {
        super(image_location, hitbox_width, hitbox_height, name);
        this.name = name;
        inputSource = InputBuffer.getInstance();
    }

    /**
     * Handles user specific modifications to the to change velocity
     * accordingly then calls the actor step method
     */
    @Override
    public void step(){
        if (inputSource.getInput("left")) {
            this.left();
        }
        if (inputSource.getInput("right")) {
            this.right();
        }
        if (inputSource.getInput("up")) {
            this.up();
        }
        if (inputSource.getInput("down")) {
            this.down();
        }
        super.step();
    }


    public void left() {
        this.x_velocity -= STEP_SIZE;
        this.setX(this.getX() - this.STEP_SIZE);
    }
    public void right() {
        this.x_velocity += STEP_SIZE;
        this.setX(this.getX() + this.STEP_SIZE);
    }
    public void up() {
        this.y_velocity += STEP_SIZE;
    }
    public void down() {
        this.y_velocity -= STEP_SIZE;
    }
}
