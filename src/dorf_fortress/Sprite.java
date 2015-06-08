package dorf_fortress;


import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Parent;

/**
 * An abstract sprite class; it's the superclass for all types of visible
 * objects in the game, excepting the tiled background.
 */
public abstract class Sprite extends Parent {
    protected Entity entity;

    /**
     * Calls ImageView's constructor, which takes in a string URL pointing to
     * the location of the image; then creates its own Hitbox object.
     * @param entity
     */
    public Sprite (Entity entity) {
        super();
        this.entity = entity;
    }

    public void update(double dorf_x) {
        double screenX = dorf_x - Main.SCENE_WIDTH/2;
        this.setY(entity.getY());
        this.setX(entity.getX() - screenX);
    }

    public void setX(double x){};
    public void setY(double y){};

}
