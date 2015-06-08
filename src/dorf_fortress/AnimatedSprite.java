package dorf_fortress;

import javafx.scene.image.ImageView;

/**
 * Used for sprites with animations; cycles through the sprite's frames at a
 * given speed, and otherwise acts the same as any other Sprite.
 */
public class AnimatedSprite extends Sprite {
    //Fields relating to the images within the animation
    private ImageView[] images;
    private int numImages;
    private int currentImage = 0;
    //Fields relating to the animation itself
    private int frameDuration; //how many game frames until it changes its image
    private int curFrame = 0;
    private boolean cycling = true;

    /**
     * Constructs
     * @param imageArray   The array of image that compose the animation
     * @param hitbox_width   The width of the hitbox
     * @param hitbox_height   The height of the hitbox
     * @param entity   The entity to which the sprite corresponds
     * @param cycleSpeed   The number of game frames per image; corresponds to
     *                  this.frameDuration
     */
    public AnimatedSprite(String[] imageArray, int hitbox_width,
                      int hitbox_height, Entity entity, int cycleSpeed) {
        super(entity);
        this.frameDuration = cycleSpeed;
        this.images = new ImageView[imageArray.length];
        for (int i = 0; i < imageArray.length; i++ ) {
            this.images[i] = new ImageView((imageArray[i]));
        }
        this.numImages = this.images.length;
        this.getChildren().add(this.images[0]);
    }

    /**
     * The update() method for this sprite. Performs animation duties within
     * the object itself, then calls super.update() for the actual work of
     * placing the sprite.
     * @param dorf_x   The x-coordinate of the Dorf object. This lets us place
     *              the sprite in its relative position on the screen.
     */
    public void update(double dorf_x) {
        //Updates the animation if necessary
        if(cycling == true) {
            this.curFrame++;
            if (this.curFrame > frameDuration) {
                curFrame = 0;
                getNextFrame();
            }
        }
        // Clears the sprite and draws in the correct image.
        this.getChildren().clear();
            this.getChildren().add(images[this.currentImage]);
        super.update(dorf_x);
    }


    /**
     * Sets whether the animation is cycling, forcing the state rather than
     * toggling it.
     * @param state   "true" turns the animation on, "false" turns it off.
     */
    public void setAnimationState(boolean state) {
        this.cycling = state;
    }

    //Helper method that moves currentImage to the next index.
    private void getNextFrame() {
        if(this.currentImage < this.numImages -1) {
            this.currentImage++;
        } else {
            this.currentImage = 0;
        }
    }

    /**
     * Sets the sprite to a given x coordinate. Overridden here because we need
     * to affect all the images in the animation.
     */
    @Override
    public void setX(double x) {
        for (int i = 0; i < numImages; i++) {
            images[i].setX(x);
        }
    }

    /**
     * Sets the sprite to a given y coordinate. Overridden for the same reason
     * setX() is.
     */
    @Override
    public void setY(double y) {
        for (int i = 0; i < numImages; i++) {
            images[i].setY(y);
        }
    }
}
