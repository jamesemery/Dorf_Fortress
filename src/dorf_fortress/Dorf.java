package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Controllable hero of the game; subclass of Actor.
 */
public class Dorf extends Actor {
    double STEP_SIZE = 30/ GameController.FRAMES_PER_SECOND;
    double PLATFORM_JUMP_BOOST = 3000;
    public final double FRICTION_COEFFICIENT = 0.3;
    String name;
    InputBuffer inputSource;


    /**
     * Calls Actor's constructor with no name.
     * @param image_location
     * @param hitbox_width
     * @param hitbox_height
     */
    public Dorf(String image_location, int hitbox_width, int hitbox_height,
                Model model) {
        super(image_location, hitbox_width, hitbox_height, model);
        inputSource = InputBuffer.getInstance();
        hitbox = new DorfHitbox(this.getX(), this.getY() , 32, 32);
        height = 32;
        width = 32;
        hitbox_checker = true;
    }

    /**
     * Calls Actor's constructor with a name.
     * @param image_location
     * @param hitbox_width
     * @param hitbox_height
     * @param name
     * @param model
     */
    public Dorf(String image_location, int hitbox_width, int hitbox_height,
                String name, Model model) {
        super(image_location, hitbox_width, hitbox_height, name, model);
        this.name = name;
        inputSource = InputBuffer.getInstance();
        hitbox = new DorfHitbox(this.getX(), this.getY() , 32, 32);
        height = 32;
        width = 32;
        hitbox_checker = true;
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
        applyFriction();
        super.step();
    }

    private void applyFriction() {
        if(this.x_velocity > 0) {
            this.x_velocity -= FRICTION_COEFFICIENT;
            //We don't want to overshoot on velocity, just bring it to zero.
            if (this.x_velocity < 0) { this.x_velocity = 0;}
        } else if (this.x_velocity < 0) {
            this.x_velocity += FRICTION_COEFFICIENT;
            //We don't want to overshoot on velocity, just bring it to zero.
            if (this.x_velocity > 0) { this.x_velocity = 0;}
        }
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
        if (onPlatform) {
            this.y_velocity += PLATFORM_JUMP_BOOST;
            System.out.println("Jump");
        }
        this.y_velocity += STEP_SIZE;
    }
    public void down() {
        this.y_velocity -= STEP_SIZE;
    }
}
