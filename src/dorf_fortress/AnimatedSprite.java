package dorf_fortress;

import javafx.scene.image.ImageView;

/**
 * Created by Joe on 6/6/2015.
 */
public class AnimatedSprite extends Sprite {
    private ImageView[] images;
    private int numImages;
    int currentImage = 0;
    int frameDuration; //how many game frames until the sprite changes state
    int curFrame = 0;
    boolean cycling = true;


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

    public void update(double dorf_x) {
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
        //super.update(dorf_x);
        double diff = (this.images[0].getX()) - entity.getX() - entity
                .simulation.player.getX();
    }


    /**
     * Sets whether the animation is cycling.
     * @param state
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

    @Override
    public void setX(double x) {
        for (int i = 0; i < numImages; i++) {
            images[i].setX(x);
        }
    }

    @Override
    public void setY(double y) {
        for (int i = 0; i < numImages; i++) {
            images[i].setY(y);
        }
    }
}
