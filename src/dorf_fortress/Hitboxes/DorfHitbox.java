package dorf_fortress;

import javafx.scene.shape.Rectangle;

/**
 * Hitbox class for the dorf hitbox. Consists of a series of JFX
 * rectangles who's bounds are used for hitdetection. In its current state,
 * the hitbox is just one rectangle and is a placeholder until the dorf
 * sprite and sprite animations have been finalized.
 * Created by jamie on 5/27/15.
 */
public class DorfHitbox extends Hitbox {
    private Rectangle hitboxRectangle;
    private double x;
    private double y;

    public DorfHitbox(double width, double height){
        //The x and y coordinates for the hitbox rectangle will be
        //set later; we're just making sure that rectangle exists
        //and is the right size.
        hitboxRectangle = new Rectangle(0, 0, width, height);
    }

    /**
     * This returns true if the given hitbox intersects with this current
     * hitbox. It works by giving the other hitbox a series of the bounds
     * that makes it up, and if any value returns true then it returns true.
     */
    public boolean intersects(Hitbox h){
        if (h.intersects(hitboxRectangle.getBoundsInLocal())){
            return true;
        }
        return false;
    }

     /**
      * Only ever called by the hitbox intersection checker.
      */
     public boolean intersects(javafx.geometry.Bounds bounds) {
         if (hitboxRectangle.intersects(bounds)) {
             return true;
         }
         return false;
     }

    /*
     * Setters and getters.
     */
    void setX(double newx){
        this.x = newx;
        hitboxRectangle.setX(newx);
    }

    void setY(double newy){
        this.y = newy;
        hitboxRectangle.setY(newy);
    }

    public double getX() {return x;};
    public double getY() {return y;};

}
