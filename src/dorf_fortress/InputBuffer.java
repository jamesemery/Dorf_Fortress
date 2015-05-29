package dorf_fortress;


import java.util.Set;
import java.util.HashSet;

/**
 * ADT we built to handle the two sorts of input sources that are used to
 * control actors in the level, computer generated and user generated. What
 * is inportant is that it can give a query which corresponds to an input and
 * returns a boolean that says that that input is there.
 *
 * Created by jamie on 5/27/15.
 */
public abstract class InputBuffer {

    /**
     * Returns a boolean value signifying whether or not the given input is
     * contained in storage.
     * @param input
     * @return
     */
    public abstract boolean getInput(String input);

    public abstract void clear();

}
