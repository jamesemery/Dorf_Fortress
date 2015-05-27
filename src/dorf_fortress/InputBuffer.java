package dorf_fortress;


import java.util.Set;
import java.util.HashSet;

/**
 * Class that stores input that is entered during the Main simulation loop.
 * It is able to be reset so the input is clean for each new frame. This
 * allows classes elsewhere in main to mimic user input by entering it into
 * this buffer itself so it will excecute as though the user inputted it.
 * Uses singleton pattern for ease of refrence
 *
 * Created by jamie on 5/27/15.
 */
public class InputBuffer {
    private static InputBuffer uniqueInstance;
    Set<String> storedInput;

    private InputBuffer() {
        storedInput = new HashSet<String>();
    }

    public static InputBuffer getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new InputBuffer();
        }
        return uniqueInstance;
    }

    public void addInput(String input){
        storedInput.add(input);
    }

    public boolean getInput(String input){
        return storedInput.contains(input);
    }

    public void reset(){
        storedInput.clear();
    }
}
