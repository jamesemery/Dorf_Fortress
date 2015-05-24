/**
 * FourSidedDie.java
 * Jeff Ondich, 10 Nov 2014
 *
 * A View class for an MVC demo using JavaFX.
 */

package MVCDemo;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class FourSidedDie extends Group {
    private DiceModel diceModel;
    private int dieIndex;

    @FXML private double width;
    @FXML private Color dotColor;
    @FXML private Color borderColor;
    @FXML private Color backgroundColor;

    final double DEFAULT_WIDTH = 200.0;
    final Color DEFAULT_DOT_COLOR = Color.GREEN;
    final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    final Color DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;

    public FourSidedDie() {
        this.width = DEFAULT_WIDTH;
        this.dieIndex = -1;
        this.dotColor = DEFAULT_DOT_COLOR;
        this.borderColor = DEFAULT_BORDER_COLOR;
        this.backgroundColor = DEFAULT_BACKGROUND_COLOR;
    }

    public void setDiceModel(DiceModel diceModel) {
        this.diceModel = diceModel;
    }

    public int getDieIndex() {
        return this.dieIndex;
    }

    public void setDieIndex(int dieIndex) {
        this.dieIndex = dieIndex;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Color getDotColor() {
        return this.dotColor;
    }

    public void setDotColor(Color dotColor) {
        this.dotColor = dotColor;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void update() {
        Circle dot;
        double dotRadius = this.getWidth() / 14.0;

        this.getChildren().clear();
        int value = this.diceModel.getDieValue(this.dieIndex);

        // Outline
        Rectangle outline = new Rectangle(0.0, 0.0, this.getWidth(), this.getWidth());
        outline.setFill(this.getBackgroundColor());
        outline.setStroke(this.getBorderColor());
        outline.setStrokeWidth(5.0);
        this.getChildren().add(outline);

        // Upper left
        if (value >= 4) {
            dot = new Circle(3.0 * dotRadius, 3.0 * dotRadius, dotRadius);
            dot.setFill(this.getDotColor());
            this.getChildren().add(dot);
        }

        // Middle left
        if (value == 6) {
            dot = new Circle(3.0 * dotRadius, 7.0 * dotRadius, dotRadius);
            dot.setFill(this.getDotColor());
            this.getChildren().add(dot);
        }

        // Bottom left
        if (value != 1) {
            dot = new Circle(3.0 * dotRadius, 11.0 * dotRadius, dotRadius);
            dot.setFill(this.getDotColor());
            this.getChildren().add(dot);
        }

        // Top right
        if (value != 1) {
            dot = new Circle(11.0 * dotRadius, 3.0 * dotRadius, dotRadius);
            dot.setFill(this.getDotColor());
            this.getChildren().add(dot);
        }

        // Middle right
        if (value == 6) {
            dot = new Circle(11.0 * dotRadius, 7.0 * dotRadius, dotRadius);
            dot.setFill(this.getDotColor());
            this.getChildren().add(dot);
        }

        // Bottom right
        if (value >= 4) {
            dot = new Circle(11.0 * dotRadius, 11.0 * dotRadius, dotRadius);
            dot.setFill(this.getDotColor());
            this.getChildren().add(dot);
        }

        // Middle middle
        if (value % 2 == 1) {
            dot = new Circle(7.0 * dotRadius, 7.0 * dotRadius, dotRadius);
            dot.setFill(this.getDotColor());
            this.getChildren().add(dot);
        }
    }
}
