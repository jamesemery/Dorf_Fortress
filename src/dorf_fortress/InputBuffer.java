package dorf_fortress;

/**
 * An ADT we built to handle the two sorts of input sources that are used to
 * control actors in the level, computer generated and user generated. What
 * is important is that it can give a query which corresponds to an input and
 * returns a boolean that says that that input is there.
 */
public abstract class InputBuffer {

    /**
     * Returns a boolean signifying whether or not the given input is contained
     * in storage.
     * @param input TODO: DO
     * @return TODO: DO
     */
    public abstract boolean getInput(String input);

    public abstract void clear();

}
