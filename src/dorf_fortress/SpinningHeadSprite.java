package dorf_fortress;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

/**
 * Created by jamie on 6/1/15.
 */
public class SpinningHeadSprite extends Sprite{
    ImageView head;
    double baseX;
    double baseY;
    Line chain;

    /**
     * Calls ImageView's constructor, which takes in a string URL pointing to
     * the location of the image; then creates its own Hitbox object.
     *
     * @param entity
     */
    public SpinningHeadSprite(String sprite_Location, double baseX, double
            baseY, Entity entity) {
        super(entity);
        head = new ImageView(sprite_Location);
        this.baseX = baseX;
        this.baseY = baseY;
        chain = new Line(baseY,baseX,baseX,baseY);
        this.getChildren().add(chain);
        this.getChildren().add(head);
    }

    @Override
    public void update(double dorf_x) {
        double screenX = dorf_x - Main.SCENE_WIDTH/2;
        this.setY(entity.getY());
        this.setX(entity.getX() - screenX);
        chain.setStartX(this.baseX - screenX);
    }

    public void setX(double x) {
        head.setX(x);
        chain.setEndX(x +10);
    }

    public void setY(double y) {
        head.setY(y);
        chain.setEndY(y +10);

    }

    public void setCenterX(double centerX) {
        this.baseX = centerX;
        chain.setStartX(baseX);
    }

    public void setCenterY(double centerY) {
        this.baseY = centerY;
        chain.setStartY(baseY);
    }
}
