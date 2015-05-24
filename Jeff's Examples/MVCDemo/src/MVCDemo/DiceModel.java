/**
 * DiceModel.java
 * Jeff Ondich, 10 Nov 2014
 *
 * A Model class for an MVC demo using JavaFX.
 */

package MVCDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceModel {
    private List<Integer> dieValues;
    private Random randomNumberGenerator;

    public DiceModel(int numberOfDice) {
        this.dieValues = new ArrayList<Integer>();
        for (int k = 0; k < numberOfDice; k++) {
            this.dieValues.add(0);
        }

        this.randomNumberGenerator = new Random();
        this.rollAllDice();
    }

    private void validateDieIndex(int dieIndex) throws IndexOutOfBoundsException {
        if (dieIndex < 0 || dieIndex >= this.dieValues.size()) {
            String message = String.format("Index %d requested, but there are only %d dice in this model.",
                    dieIndex, this.dieValues.size());
            throw new IndexOutOfBoundsException(message);
        }
    }

    public int getDieValue(int dieIndex) throws IndexOutOfBoundsException {
        this.validateDieIndex(dieIndex);
        return this.dieValues.get(dieIndex);
    }

    public void rollDie(int dieIndex) throws IndexOutOfBoundsException {
        this.validateDieIndex(dieIndex);
        int dieValue = this.randomNumberGenerator.nextInt(6) + 1;
        this.dieValues.set(dieIndex, dieValue);
    }

    public void rollAllDice() {
        for (int k = 0; k < this.dieValues.size(); k++) {
            int dieValue = this.randomNumberGenerator.nextInt(6) + 1;
            this.dieValues.set(k, dieValue);
        }
    }
}
