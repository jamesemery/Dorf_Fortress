package dorf_fortress;


import java.util.Set;
import java.util.HashSet;

/**
 * Class that stores input entered during the Main simulation loop.
 * It is able to be reset so the input is clean for each new frame. This
 * allows classes elsewhere in main to mimic user input by entering it into
 * this buffer itself so it will excecute as though the user inputted it.
 * Uses singleton pattern for ease of reference.
 */
public class BasicInputBuffer extends InputBuffer {
    private static BasicInputBuffer uniqueInstance;
    private Set<String> storedInput;

    /**
     * Private constructor; The input is stored as a set.
     */
    private BasicInputBuffer() {
        storedInput = new HashSet<String>();
    }

    /**
     * Returns an instance of InputBuffer, creating one if it doesn't already
     * exist. This is the only constructor that can be publicly accessed.
     * @return   the instance of InputBuffer.
     */
    public static BasicInputBuffer getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BasicInputBuffer();
        }
        return uniqueInstance;
    }

    /**
     * Edits the stored input based on the key input given. If the key was
     * pressed (i.e. pressed = true), adds that input to storage; if it was
     * released (pressed = false), removes it from storage.
     * @param input   A String value representing the input.
     * @param pressed   True corresponds to a pressed key, false to a release.
     */
    public void addInput(String input, boolean pressed){
        if (pressed == true) {
            storedInput.add(input);
        } else {
            storedInput.remove(input);
        }
    }

    /**
     * Returns a boolean value signifying whether or not the given input is
     * contained in storage.
     * @param input   A String value representing the input.
     */
    public boolean getInput(String input) {
        return storedInput.contains(input);
    }

    /**
     * Clears the input buffer of any remaining held inputs
     */
    public void clear() {
        storedInput.clear();
    }
}
