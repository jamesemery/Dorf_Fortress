package dorf_fortress;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

/**
 * SpinningHeadSprite is a subclass of Sprite designed specifically to handle
 * the intricacies of the spinning heads.
 */
public class SpinningHeadSprite extends Sprite{
    private ImageView head;
    private Line chain;
    private double centerX;
    private double centerY;

    /**
     * Constructs an ImageView for the head, a Line for the chain, and saves
     * the X and Y coordinates for the point around which the head spins. Also
     * passes the SpinningHead entity up to Sprite.
     *
     * @param entity
     */
    public SpinningHeadSprite(Entity entity, double centerX, double
            centerY) {
        super(entity);
        head = new ImageView("sprites/Skull.png");
        this.centerX = centerX;
        this.centerY = centerY;
        chain = new Line(centerX,centerY,centerX,centerY);
        this.getChildren().add(chain);
        this.getChildren().add(head);
    }

    /**
     * Gets the new X and Y locations from the associated SpinningHead entity
     * and updates the sprites accordingly.
     * @param dorf_x   The Dorf's x-coordinate.
     */
    @Override
    public void update(double dorf_x) {
        double screenX = dorf_x - Main.SCENE_WIDTH/2;
        this.setY(entity.getY());
        this.setX(entity.getX() - screenX);
        chain.setStartX(this.centerX - screenX);
    }

    /*
     * Setters & Getters
     */
    public void setX(double x) {
        head.setX(x);
        chain.setEndX(x + 10); // The chain reaches the center, not the corner.
    }

    public void setY(double y) {
        head.setY(y);
        chain.setEndY(y + 10); // The chain reaches the center, not the corner.
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
        chain.setStartX(centerX);
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
        chain.setStartY(centerY);
    }
}
