package dorf_fortress;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by jamie on 5/28/15.
 */
public class GhostInputSource extends InputBuffer {
    private static GhostInputSource uniqueInstance;
    List<Boolean[]> storedInput;
    Boolean[] currentInput;
    int cursor;


    private GhostInputSource() {
        storedInput = new ArrayList<Boolean[]>();
        cursor = 0;
    }

    /**
     * Returns an instance of InputBuffer, creating one if it doesn't already
     * exist. This is the only constructor that can be publicly accessed.
     *
     * @return the instance of InputBuffer.
     */
    public static InputBuffer getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GhostInputSource();
        }
        return uniqueInstance;
    }

    /**
     * Returns a boolean value signifying whether or not the given input is
     * contained in storage. Assumes that the boolean array is at least 4
     * items long and is ordered such that numerically it corresponds to
     * left, right, up, down
     *
     * @param input
     * @return
     */
    public boolean getInput(String input) {
//        if (currentInput==null) {
//            return false;
//        }
//        if (input == "right") {
//            return currentInput[0];
//        }
//        if (input == "left") {
//            return currentInput[1];
//        }
//        if (input == "up") {
//            return currentInput[2];
//        }
//        if (input == "down") {
//            return currentInput[3];
//        }
//        return false;
        if (input == "right"){
            return true;
        }
        return false;
    }

    /**
     * Resets the cursor so the first input will be the valid first input
     */
    public void clear() {
//        cursor = 0;
//        currentInput = storedInput.get(0);
    }

    /**
     * Jumps to the next frame
     */
    public void nextFrame() {
//        cursor++;
//        currentInput = storedInput.get(cursor);
    }
}
