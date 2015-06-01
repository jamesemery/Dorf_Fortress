package dorf_fortress;


import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Parent;

/**
 * Created by Joe Adkisson on 5/24/2015.
 * A sprite class; it's the superclass for all visible objects in the gameworld.
 * (With the probable exception of our background image.
 */
public class Sprite extends Parent {
    protected Entity entity;

    /**
     * Calls ImageView's constructor, which takes in a string URL pointing to
     * the location of the image; then creates its own Hitbox object.
     * @param sprite_Location
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
