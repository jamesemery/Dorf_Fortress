package dorf_fortress;

import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joe on 6/4/2015.
 */


public class DorfSprite extends Sprite {

    ImageView[] rightImages;
    int numImages;
    boolean movingRight = true;
    int currentImage = 0;

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
        System.out.println("imageArray: " + rightArray);
        this.rightImages = new ImageView[rightArray.length];
        for (int i = 0; i < rightArray.length; i++ ) {
            System.out.println(rightArray[i]);
            this.rightImages[i] = new ImageView((rightArray[i]));
        }
        this.numImages = this.rightImages.length;
        this.getChildren().add(this.rightImages[0]);
    }

    @Override
    public void setX(double x) {
            rightImages[this.currentImage].setX(x);
        }

    @Override
    public void setY(double y) {
            rightImages[this.currentImage].setY(y);
        }

    @Override
    public void update(double dorf_x) {
        //have an instance variable saving the last x we updated at
        //if we're more than [number] pixels right of that,
            //go to next rightward sprite if going right
            //go to rightward[0]
        //if we're more than [number] pixels left of that,
            //go to next leftward sprite if going left
            //go to leftward[0]


        //Move to the next image
        this.getChildren().remove(rightImages[this.currentImage]);
        getNextFrame();
        this.getChildren().add(rightImages[this.currentImage]);
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
}

