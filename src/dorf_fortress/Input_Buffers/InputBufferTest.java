package dorf_fortress;

import static org.junit.Assert.*;

/**
 * A battery of tests for the two InputBuffer subclasses and their logic
 */
public class InputBufferTest {

    @org.junit.Test
    public void testGetInputEmptyInput() throws Exception {
        InputBuffer playerInput = BasicInputBuffer.getInstance();
        assertEquals(playerInput.getInput("left"),false);
        assertEquals(playerInput.getInput("right"),false);
        assertEquals(playerInput.getInput("up"),false);
        assertEquals(playerInput.getInput("down"),false);

        InputBuffer ghostInput = new GhostInputBuffer();
        assertEquals(ghostInput.getInput("left"),false);
        assertEquals(ghostInput.getInput("right"),false);
        assertEquals(ghostInput.getInput("up"),false);
        assertEquals(ghostInput.getInput("down"),false);
    }

    // We should hope that these are both the same instance
    @org.junit.Test
    public void testSingletonIsSingleton() throws Exception {
        BasicInputBuffer playerInput = BasicInputBuffer.getInstance();
        BasicInputBuffer playerInput2 = BasicInputBuffer.getInstance();
        assertEquals(playerInput,playerInput2);

        playerInput.addInput("right", true);
        boolean instance1 = playerInput.getInput("right");
        boolean instance2 = playerInput2.getInput("right");
        assertEquals(instance1,instance2);
    }

    public void testGhostInputAddInputGetInputNextInputAndResetInput() throws
            Exception {
        GhostInputBuffer ghostInput = new GhostInputBuffer();
        assertEquals(ghostInput.getInput("left"),false);
        assertEquals(ghostInput.getInput("right"), false);
        assertEquals(ghostInput.getInput("up"), false);
        assertEquals(ghostInput.getInput("down"), false);

        // Testing that it properly sets the first to the right one
        ghostInput.addInput(true, false, true, false);
        assertEquals(ghostInput.getInput("left"),true);
        assertEquals(ghostInput.getInput("right"), false);
        assertEquals(ghostInput.getInput("up"), true);
        assertEquals(ghostInput.getInput("down"), false);

        // Testing that adding a second input doesnt affect the first
        ghostInput.addInput(false, true, false, true);
        // This should still be giving the same results as the first set of
        // input
        assertEquals(ghostInput.getInput("left"),true);
        assertEquals(ghostInput.getInput("right"), false);
        assertEquals(ghostInput.getInput("up"), true);
        assertEquals(ghostInput.getInput("down"), false);

        ghostInput.nextFrame();
        // This should still be giving the same results as the set of input
        assertEquals(ghostInput.getInput("left"), false);
        assertEquals(ghostInput.getInput("right"), true);
        assertEquals(ghostInput.getInput("up"), false);
        assertEquals(ghostInput.getInput("down"), true);

        ghostInput.nextFrame();
        // This should repeat the second set of input if it goes past the end
        assertEquals(ghostInput.getInput("left"), false);
        assertEquals(ghostInput.getInput("right"), true);
        assertEquals(ghostInput.getInput("up"), false);
        assertEquals(ghostInput.getInput("down"), true);

        ghostInput.clear();
        // This should return the input to the first set of inputs
        assertEquals(ghostInput.getInput("left"), true);
        assertEquals(ghostInput.getInput("right"), false);
        assertEquals(ghostInput.getInput("up"), true);
        assertEquals(ghostInput.getInput("down"), false);

        ghostInput.removeInputs(1);
        // This should remove the second input
        assertEquals(ghostInput.getInput("left"), true);
        assertEquals(ghostInput.getInput("right"), false);
        assertEquals(ghostInput.getInput("up"), true);
        assertEquals(ghostInput.getInput("down"), false);

        ghostInput.nextFrame();
        // This should be repeating the first frames input again because it
        // doesn't have a second frame of input to pull from
        assertEquals(ghostInput.getInput("left"), true);
        assertEquals(ghostInput.getInput("right"), false);
        assertEquals(ghostInput.getInput("up"), true);
        assertEquals(ghostInput.getInput("down"), false);

        ghostInput.removeInputs(1);
        // Testing that removing down to one frame doesn't crash anything
        assertEquals(ghostInput.getInput("left"), true);
        assertEquals(ghostInput.getInput("right"), false);
        assertEquals(ghostInput.getInput("up"), true);
        assertEquals(ghostInput.getInput("down"), false);
    }

    @org.junit.Test
    public void testBasicInputBufferKeyPressLogic() throws Exception {
        BasicInputBuffer playerInput = BasicInputBuffer.getInstance();
        playerInput.clear();

        boolean actual = playerInput.getInput("right");
        // Should be false because the inputbuffer is empty
        assertEquals(actual, false);

        // Testing that adding input can be pulled
        playerInput.addInput("right",true);
        actual = playerInput.getInput("right");
        assertEquals(actual, true);

        // testing that nothing changed for other inputs
        actual = playerInput.getInput("left");
        assertEquals(actual,false);

        // testing that totally wrong inputs return false
        actual = playerInput.getInput("apple");
        assertEquals(actual, false);

        // Testing that setting something to false works properly
        playerInput.addInput("right",false);
        actual = playerInput.getInput("right");
        assertEquals(actual, false);

        // Testing that clear removes input from the buffer properly
        playerInput.addInput("right",false);
        playerInput.clear();
        actual = playerInput.getInput("right");
        assertEquals(actual, false);


    }
}