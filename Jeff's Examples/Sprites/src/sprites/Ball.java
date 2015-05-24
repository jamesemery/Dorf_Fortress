/**
 * Ball.java
 * Jeff Ondich, 10/29/14.
 *
 * A sample subclass of Sprite for CS257.
 */
package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Sprite {
    private Circle circle;

    public Ball() {
    }

    /* This illustrates an override of the setSize method. Note that it
     * defers the resizing of the sprite itself to super.setSize, but that
     * this Ball.setSize takes responsibility for replacing the old Circle
     * with a new Circle sized appropriately for the new size.
     */
    @Override
    public void setSize(double width, double height) {
        super.setSize(width, height);
        Circle oldCircle = this.circle;
        this.circle = new Circle(width / 2.0, height / 2.0, width / 2.0);
        if (oldCircle != null) {
            this.circle.setFill(oldCircle.getFill());
        } else {
            this.circle.setFill(Color.GREEN);
        }
        this.getChildren().clear();
        this.getChildren().add(this.circle);
    }

    /* This override does nothing, but it's required since Sprite.makeSound is abstract. */
    @Override
    public void makeSound() {
    }
}
