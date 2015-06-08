package dorf_fortress;

import javafx.scene.image.*;
import javafx.scene.paint.*;

/**
 * A Sprite that has animations for movement in the left and right directions,
 * created specifically for Dorf objects.
 * - It assumes that the leftwards and rightwards animations have the same number
 * of frames.
 * - The DorfSprite's animation is based not on time or the game loop, but on the
 * movement of the Dorf; whenever the Dorf moves [frameTolerance] pixels in a
 * direction, it updates its sprite.
 * - There is no forward-facing animation; when the character stops moving, it
 * will just stay on the same frame it was on.
 */
public class DorfSprite extends Sprite {
    //Variables representing the animation's frames.
    ImageView[] rightImages;
    ImageView[] leftImages;
    int numImages;
    //Variables to do with animation
    int currentImage = 0;
    boolean movingRight = true;
    double pastFrameX = 0;  //the last x at which we updated a sprite. Begins
                            //at zero.
    double frameTolerance = 10.0; //how far the entity must move before updating


    /**
     *
     * @param leftArray; an array of Strings containing the locations of the
     *                   left-facing sprites.
     * @param rightArray; ditto for right-facing sprites
     * @param dorf; a reference to the dorf.
     */
    public DorfSprite(String[] leftArray, String[] rightArray, Entity dorf) {
        super(dorf);
        this.rightImages = new ImageView[rightArray.length];
        this.leftImages = new ImageView[leftArray.length];
        for (int i = 0; i < rightArray.length; i++ ) {
            this.leftImages[i] = new ImageView((leftArray[i]));
            this.rightImages[i] = new ImageView((rightArray[i]));
        }
        this.numImages = this.rightImages.length;
        this.getChildren().add(this.rightImages[0]);
        this.pastFrameX = dorf.getX();
    }

    @Override
    public void setX(double x) {
        for (int i = 0; i < numImages; i++) {
            rightImages[i].setX(x);
            leftImages[i].setX(x);
        }
    }

    @Override
    public void setY(double y) {
        for (int i = 0; i < numImages; i++) {
            rightImages[i].setY(y);
            leftImages[i].setY(y);
        }
    }

    @Override
    public void update(double dorf_x) {
        //we have an instance variable saving the last x we updated at;
        //if we're more than [frameTolerance] pixels right of that...
        if (dorf_x - pastFrameX > frameTolerance) {
            if (movingRight) { getNextFrame(); } //just go to next frame
            else {
                //currentImage = 0;
                getNextFrame();
                movingRight = true;
            }
            pastFrameX = dorf_x;
        //if we're more than [frameTolerance] pixels left of that...
        } else if (dorf_x - pastFrameX < frameTolerance*-1) {
            //if we're moving right, reset animation moving left
            if (movingRight) {
                //currentImage = 0;
                getNextFrame();
                movingRight = false;
            } else { getNextFrame(); }
            pastFrameX = dorf_x;
        }

        // Clears the sprite and draws in the correct image.
        this.getChildren().clear();
        if(movingRight) {
            this.getChildren().add(rightImages[this.currentImage]);
        } else {
            this.getChildren().add(leftImages[this.currentImage]);
        }
        super.update(dorf_x);
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
     * Colors the hair and beard of all the sprites to match the given color.
     */
    public void colorSprites(Color hairColor) {
        for(int i = 0; i < rightImages.length; i++) {
            rightImages[i].setImage(Main.colorImage(rightImages[i].getImage(), hairColor));
            leftImages[i].setImage(Main.colorImage(leftImages[i].getImage(), hairColor));
        }
    }


}

