package dorf_fortress;

import javafx.scene.image.*;
import javafx.scene.paint.*;

import java.util.LinkedList;
import java.util.List;

/**
 * A Sprite that has animations for movement in the left and right directions.
 * Created specifically for Dorf objects, but it's theoretically capable of
 * handling any object with left and right movement. The only caveat is that
 * the leftwards and rightwards animations must have the same number of frames.
 * There is no forward-facing animation; when the character stops moving, it
 * will just stay on the same frame it was on.
 * Created by Joe on 6/4/2015.
 */


public class DorfSprite extends Sprite {

    ImageView[] rightImages;
    ImageView[] leftImages;
    int numImages;
    int currentImage = 0;
    boolean movingRight = true;
    double pastFrameX = 0; //the last x coordinate at which we updated a sprite
    double frameTolerance =10.0; //how far the entity must travel before updating


    /**
     * Calls ImageView's constructor, which takes in a string URL pointing to
     * the location of the image; then creates its own Hitbox object.
     *
     * @param leftArray; an array of Strings containing the locations of the
     *                   left-facing sprites.
     * @param rightArray; ditto for right-facing sprites
     * @param hitbox_width
     * @param hitbox_height
     * @param dorf; a reference to the dorf.
     */
    public DorfSprite(String[] leftArray, String[] rightArray, int hitbox_width,
                      int hitbox_height, Entity dorf) {
        super(dorf);
        this.rightImages = new ImageView[rightArray.length];
        this.leftImages = new ImageView[leftArray.length];
        for (int i = 0; i < rightArray.length; i++ ) {
            this.leftImages[i] = new ImageView((leftArray[i]));
            this.rightImages[i] = new ImageView((rightArray[i]));
        }
        this.numImages = this.rightImages.length;
        this.getChildren().add(this.rightImages[0]);
        System.out.println("added child " + this.rightImages[0]);
        this.pastFrameX = dorf.getX();
        System.out.println("DorfSprite constructor finished.");
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
    //TODO: refactor this code for a nicer loop, so we can have quicker
        //changes in direction. Works for now, though.

        //we have an instance variable saving the last x we updated at;
        //if we're more than [frameTolerance] pixels right of that...
        if (dorf_x - pastFrameX > frameTolerance) {
            if (movingRight) { getNextFrame(); } //just go to next frame
            else {
                currentImage = 0;
                movingRight = true;
            }
            pastFrameX = dorf_x;
        //if we're more than [frameTolerance] pixels left of that...
        } else if (dorf_x - pastFrameX < frameTolerance*-1) {
            //if we're moving right, reset animation moving left
            if (movingRight) {
                currentImage = 0;
                movingRight = false;
            } else { getNextFrame(); }
            pastFrameX = dorf_x;
        }
//        System.out.println("movingRight: " + movingRight);

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

