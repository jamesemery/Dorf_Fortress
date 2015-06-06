package dorf_fortress;

/**
 * An interface for a hitbox object. It moves along with its parent object and
 * returns true if there is an intersection.
 */
public abstract class Hitbox {

    /**
     * Check if the given hitbox intersects another.
     * @return True if intersecting, False otherwise
     */
    public abstract boolean intersects(Hitbox h);
    public abstract boolean intersects(javafx.geometry.Bounds boundsInLocal);

    /**
     * Setters and getters.
     */
    abstract void setX(double x);
    abstract void setY(double y);
    public abstract double getX();
    public abstract double getY();
}
