package dorf_fortress;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/**
 * Hitbox class for the dorf hitbox. Consists of a series of JFX
 * rectangles who's bounds are used for hitdetection. In its current state,
 * the hitbox is just one rectangle and is a placeholder until the dorf
 * sprite and sprite animations have been finalized.
 * Created by jamie on 5/27/15.
 */
public class DorfHitbox extends Hitbox {
    Rectangle[] hitboxRectangles;
    double x;
    double y;

    public DorfHitbox( double width, double height){
        hitboxRectangles = new Rectangle[1];
        hitboxRectangles[0] = new Rectangle(x, y, width, height);
    }

    void setX(double newx){
        this.x = newx;
        for (Rectangle r : hitboxRectangles) {
            r.setX(newx);
        }
    }

    void setY(double newy){
        this.y = newy;
        for (Rectangle r : hitboxRectangles){
            r.setY(newy);
        }
    }

    /**
     * This returns true if the given hitbox intersects with this current
     * hitbox. It works by giving the other hitbox a series of the bounds
     * that makes it up, and if any value returns true then it returns true.
     */
    public boolean intersects(Hitbox h){
        for (Rectangle r : hitboxRectangles) {
            if (h.intersects(r.getBoundsInLocal())){
                return true;
            }
        }
        return false;
    }

     /**
      * Takes a javafx bounds object and returns true if it intersects with any
      * of the objects that make up this hitbox. For this hitbox it consists of
      * looping through the rectangles that makes it up and determines if the
      * bounds intersect.
      **/
    public boolean intersects(javafx.geometry.Bounds bounds) {
        for (Rectangle r : hitboxRectangles) {
            if (r.intersects(bounds)) {
                return true;
            }
        }
        return false;
    }

    public double getX() {return x;};
    public double getY() {return y;};

}
