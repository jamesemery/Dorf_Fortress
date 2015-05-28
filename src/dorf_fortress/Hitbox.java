package dorf_fortress;

import javafx.geometry.Bounds;

/**
 * An interface for a hitbox object which is able to be moved along with its
 * parent object and return true if there is an intersection
 * Created by jamie on 5/27/15.
 */
public abstract class Hitbox {

    abstract void setX(double x);

    abstract void setY(double y);

    //this returns true if t he given hitbox intersects with this current hitbox
    public abstract boolean intersects(Hitbox h);


    public abstract boolean intersects(javafx.geometry.Bounds boundsInLocal);
}
