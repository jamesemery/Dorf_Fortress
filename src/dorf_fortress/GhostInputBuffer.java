package dorf_fortress;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * Class that mimics the behavior of the input source so that the Ghost
 * object will maintain consistent behavior to the Dorf object. In its
 * current state this class works by reading from a pre-generated file that
 * lists booleans corresponding to the key inputs for the Dorf that solve the
 * hard-coded level. The class is able to run through these inputs frame by
 * frame just like the input buffer that is tied to user inputs.
 *
 * The class stores a list of length 4 boolean arrays corresponding to frame
 * by frame input in chronological order and when asked for input it merely
 * returns the corresponding value for the current frame.
 */
public class GhostInputBuffer extends InputBuffer {
//    private static GhostInputBuffer uniqueInstance;
    List<Boolean[]> storedInput;
    Boolean[] currentInput;
    int cursor;


    /**
     * Currently, all this constructor does is read from a file that consists
     * of a series of 4 ordered booleans on a line and converts that into the
     * stored array of inputs.
     */
    public GhostInputBuffer() {
        storedInput = new ArrayList<Boolean[]>();
        cursor = 0;
    }


    /**
     * Currently, all this constructor does is read from a file that consists
     * of a series of 4 ordered booleans on a line and converts that into the
     * this constructor takes a filepath for where the file is located
     */
    public GhostInputBuffer(String filepath) {
        storedInput = new ArrayList<Boolean[]>();
        cursor = 0;
        File f = new File(filepath);
        try {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextBoolean()){
                Boolean[] frameInput = new Boolean[4];
                frameInput[0] = scanner.nextBoolean();
                frameInput[1] = scanner.nextBoolean();
                frameInput[2] = scanner.nextBoolean();
                frameInput[3] = scanner.nextBoolean();
                storedInput.add(frameInput);
            }
            currentInput = storedInput.get(0);
        } catch (FileNotFoundException e){
        }
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
        if (currentInput==null) {
            return false;
        }
        if (input == "right") {
            return currentInput[1];
        }
        if (input == "left") {
            return currentInput[0];
        }
        if (input == "up") {
            return currentInput[2];
        }
        if (input == "down") {
            return currentInput[3];
        }
        return false;

    }

    /**
     * Resets the cursor so the first input will be the valid first input.
     */
    public void clear() {
        cursor = 0;
        if (!storedInput.isEmpty()){
            currentInput = storedInput.get(0);
        }
    }

    /**
     * Jumps to the next frame
     */
    public void nextFrame() {
        cursor++;
        if (cursor<storedInput.size()) {
            currentInput = storedInput.get(cursor);
        }
    }

    /**
     * Adds set of input to the end of the list
     */
    public void addInput(boolean left, boolean right, boolean up, boolean
            down) {
        Boolean[] newFrame = new Boolean[4];
        newFrame[0] = left;
        newFrame[1] = right;
        newFrame[2] = up;
        newFrame[3] = down;
        storedInput.add(newFrame);

        // If this is the first input being entered, then it sets the current
        // frame to the proper place
        if (storedInput.size()==1){
            currentInput = storedInput.get(0);
        }
    }

    /**
     * Removes all inputs from the list after the given frame, if the number
     * specified is a frame after the last frame of the list it will throw an
     * exception. frame argument must be positive value
     */
    public void removeInputs(int frame) throws IndexOutOfBoundsException {
        if (frame > storedInput.size()) {
            throw new IndexOutOfBoundsException(frame + " is not a valid " +
                    "frame in the GhostInputBuffer, the last valid frame is "
                    + (storedInput.size() - 1));
        }
        // Repeatedly removes the last item of the array until the array has
        // a number of elements equal to the specified frame
        while (storedInput.size() > (frame+1)) {
            storedInput.remove(storedInput.size() - 1);
        }
    }
}
