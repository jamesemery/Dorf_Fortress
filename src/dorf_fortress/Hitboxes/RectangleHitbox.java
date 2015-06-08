package dorf_fortress;

import javafx.scene.shape.Rectangle;

/**
 * A simple variety of hitbox, typically surrounding platforms and other
 * uninterestingly shaped obstacles.
 */
public class RectangleHitbox extends Hitbox {
    private Rectangle[] hitboxRectangles;
    private double x;
    private double y;

    public RectangleHitbox(double width, double height){
        this.hitboxRectangles = new Rectangle[1];
        this.hitboxRectangles[0] = new Rectangle(width, height);
    }

    /**
     * Returns true if the given hitbox intersects with the current hitbox.
     * @param h   The hitbox whose intersection is being tested.
     * @return   A boolean: whether or not it intersects.
     */
    public boolean intersects(Hitbox h){
        for (Rectangle r : this.hitboxRectangles) {
            if (h.intersects(r.getBoundsInLocal())){
                return true;
            }
        }
        return false;
    }

    /**
     * Only ever called by the hitbox intersection checker.
     */
    public boolean intersects(javafx.geometry.Bounds bounds) {
        for (Rectangle r : hitboxRectangles) {
            if (r.intersects(bounds)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Setters and getters.
     */
    void setX(double newx){
        this.x = newx;
        for (Rectangle r : this.hitboxRectangles) {
            r.setX(newx);
        }
    }

    void setY(double newy){
        this.y = newy;
        for (Rectangle r : this.hitboxRectangles){
            r.setY(newy);
        }
    }

    public double getX() {return this.x;}

    public double getY() {return this.y;}

}
