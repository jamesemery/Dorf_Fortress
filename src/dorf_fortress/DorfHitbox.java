package dorf_fortress;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/**
 * Created by jamie on 5/27/15.
 */
public class DorfHitbox extends Hitbox {
    Rectangle[] hitboxRectangles;
    double x;
    double y;

    public DorfHitbox( double width, double height){
        this.x = x;
        this.y = y;
        hitboxRectangles = new Rectangle[1];
        hitboxRectangles[0] = new Rectangle(x, y, width, height);
    }

    void setX(double newx){
        x = newx;
        for (Rectangle r : hitboxRectangles) {
            r.setX(newx);
        }
    }

    void setY(double newy){
        y = newy;
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
      * this method takes a javafx bounds object and returns true if it
      * intersects with any of the objects that make up this hitbox. For this
      * hitbox it consists of looping through the rectangles that makes it up
      * and determines if the bounds intersect.
      **/
    public boolean intersects(javafx.geometry.Bounds bounds){
        for (Rectangle r : hitboxRectangles) {
            if (r.intersects(bounds)){
                return true;
            }
        }
        return false;
    }

}
