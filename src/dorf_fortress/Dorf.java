package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Controllable hero of the game; subclass of Actor.
 */
public class Dorf extends Actor {

    double STEP_SIZE_X = 200/ GameController.FRAMES_PER_SECOND;
    double STEP_SIZE_Y = 20/ GameController.FRAMES_PER_SECOND;
    double PLATFORM_JUMP_BOOST = 15000/ GameController.FRAMES_PER_SECOND;
    public final double FRICTION_CONSTANT = 120/ GameController.FRAMES_PER_SECOND;
    public final double MAX_HORIZ_SPEED = 80;
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
     * Pulls the frame's input from the InputBuffer, then calls the appropriate
     * methods to modify its velocity. Then:
     * - applies friction
     * - calls Actor's step() method, which applies gravity and
     * - calls Entity's step() method, which changes position based on velocity
     *   and handles collision checking.
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

    /**
     * Applies friction by subtracting a friction constant from the velocity.
     * To do that, we check the sign of the velocity, then make sure the
     * friction doesn't overshoot.
     */
    private void applyFriction() {
        if(this.x_velocity > 0) {
            this.x_velocity -= FRICTION_CONSTANT;
            //We don't want to overshoot on velocity, just bring it to zero.
            if (this.x_velocity < 0) { this.x_velocity = 0;}
        } else if (this.x_velocity < 0) {
            this.x_velocity += FRICTION_CONSTANT;
            //We don't want to overshoot on velocity, just bring it to zero.
            if (this.x_velocity > 0) { this.x_velocity = 0;}
        }
    }

    /**
     * Adds to the leftwards velocity.
     */
    public void left() {
<<<<<<< HEAD
        this.x_velocity -= STEP_SIZE;
        if (this.x_velocity < MAX_HORIZ_SPEED * -1) {
            this.x_velocity = MAX_HORIZ_SPEED * -1;
        }
=======
        this.x_velocity -= STEP_SIZE_X;

>>>>>>> 3f45805ec73d00de74053c5717bdd5fd13df538c
    }

    /**
     * Adds to the rightwards velocity.
     */
    public void right() {
<<<<<<< HEAD
        this.x_velocity += STEP_SIZE;
        if (this.x_velocity > MAX_HORIZ_SPEED) {
            this.x_velocity = MAX_HORIZ_SPEED;
        }
=======
        this.x_velocity += STEP_SIZE_X;
>>>>>>> 3f45805ec73d00de74053c5717bdd5fd13df538c
    }

    /**
     * Adds to the upwards velocity.
     */
    public void up() {
        if (onPlatform) {
            this.y_velocity += PLATFORM_JUMP_BOOST;
            System.out.println("Jump");
        }
        this.y_velocity += STEP_SIZE_Y;
    }

    /**
     * Adds to the downwards velocity.
     */
    public void down() {
        this.y_velocity -= STEP_SIZE_Y;
    }
}
