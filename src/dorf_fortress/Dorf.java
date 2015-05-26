package dorf_fortress;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * Controllable hero of the game; subclass of Actor.
 */
public class Dorf extends Actor {
    /**
     * Calls Actor's constructor with no name.
     * @param image_location
     * @param hitbox_width
     * @param hitbox_height
     */
    public Dorf(String image_location, int hitbox_width, int hitbox_height) {
        super(image_location, hitbox_width, hitbox_height);
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
        super(image_location, hitbox_width, hitbox_height);
        this.name = name;
    }
}
