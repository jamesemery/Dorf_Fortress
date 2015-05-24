/**
 * Controller.java
 * Jeff Ondich, 10 Nov 2014
 *
 * A Controller class for an MVC demo using JavaFX.
 */

package MVCDemo;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class Controller {
    public Group diceGroup;
    public Label diceSumLabel;
    private DiceModel diceModel;

    public Controller() {
    }

    public void initialize() {
        this.diceModel = new DiceModel(this.diceGroup.getChildren().size());

        int index = 0;
        double leftEdge = 0.0;
        final double PADDING = 15.0;
        for (Node node : this.diceGroup.getChildren()) {
            FourSidedDie die = (FourSidedDie)node;
            die.setDieIndex(index);
            die.setDiceModel(this.diceModel);
            die.setLayoutX(leftEdge);
            leftEdge += die.getWidth() + PADDING;
            die.update();
            index++;
        }

        this.updateDiceSumLabel();
    }

    private void updateDiceSumLabel() {
        int total = 0;
        for (int k = 0; k < this.diceGroup.getChildren().size(); k++) {
            total += this.diceModel.getDieValue(k);
        }
        this.diceSumLabel.setText(String.format("Total: %d", total));
    }

    public void onRollButton(ActionEvent actionEvent) {
        this.diceModel.rollAllDice();
        for (Node node : this.diceGroup.getChildren()) {
            FourSidedDie die = (FourSidedDie)node;
            die.update();
        }
        this.updateDiceSumLabel();
    }
}
