package dorf_fortress;

import java.util.List;
import java.util.ArrayList;

/**
 * The Model class is responsible both for building the level by simulating
 * user input as well as running the model of the game itself.
 */
public class Model {
    static Model singleInstance;
    private List<Entity> entities;
    public Dorf player;
    public Ghost levelSolver;
    private boolean ghostMode;
    public List<Entity> testingEntities; //used for character generation
    private GameController controller;
    public static double SCENE_HEIGHT;
    private int currentFrame;
    private double difficulty;
    private int timeLimit;
    public Hitbox spawnSafeZone;

    /**
     * Constructor. Builds a model based on the height of the scene and
     * the level's difficulty, along with a reference to the constructor; the
     * rest is randomly generated.
     * @param controller   A reference to the level's controller.
     * @param sceneHeight   The height of the Scene.
     * @param difficulty   The difficulty of the level; a double from 0 to 50.
     */
    public Model(GameController controller, double sceneHeight, double difficulty) {
        this.SCENE_HEIGHT = sceneHeight;
        this.controller = controller;
        this.difficulty = difficulty;
        this.spawnSafeZone = null;

        entities = new ArrayList<Entity>();
        //Build the Ghost's path and the platforms along it.
        LevelBuilder levelBuilder = new LevelBuilder(this,entities,controller);
        levelBuilder.makeLevel();

        ObstaclePlacer dangerMaker = new ObstaclePlacer(this, this
                .levelSolver, spawnSafeZone);
        // Chooses how many obstacles to place based on the number of platforms
        // (i.e. entities.size(), since entities only contains platforms as yet)
        int obstacles = (int)(entities.size()*((this.difficulty/2) + 1));
        dangerMaker.generateObstacles(obstacles);
        //Set up the level for the user to play.
        setGhostMode(false);
        timeLimit = levelSolver.getEndFrame()*2 + ((int) difficulty)*60;
        levelSolver.liveSimulation = true;
    }

    /**
     * Pauses the animation; simply calls the controller's method of the same
     * name.
     */
    public void pause() {
        this.controller.pause();
    }

    /**
     * Unpauses the animation; ditto.
     */
    public void unpause() {this.controller.unpause(); }

    /**
     * Resets the level, and lays the groundwork to have either the ghost or
     * the human-controlled Dorf solve the level, depending on the value of
     * the boolean.
     * @param val   Whether the human or the computer is solving the level;
     *              a value of true means the computer's Ghost.
     */
    public void setGhostMode(boolean val) {
        if (val) { //The ghost is solving the level
            this.reset();
            ghostMode = true;
            //Clears the scene of Dorf sprites, and adds in the levelSolver's.
            controller.removeSpriteFromRoot(levelSolver.getSprite());
            controller.removeSpriteFromRoot(player.getSprite());
            controller.addSpriteToRoot(levelSolver.getSprite());
        } else { //The human is solving the level
            this.reset();
            ghostMode = false;
            //Clears the scene, adds in the user's Dorf sprite.
            controller.removeSpriteFromRoot(player.getSprite());
            controller.addSpriteToRoot(player.getSprite());
            controller.removeSpriteFromRoot(levelSolver.getSprite());
        }
    }

    /**
     * Adds the given list of entities to the running simulation as well as
     * giving them to the GameController, which will then connect htem into
     * the Scene.
     */
    public void addEntities(List<Entity> newEntities) {
        for (Entity e : newEntities) {
            entities.add(e);
            controller.addSpriteToRoot(e.getSprite());
        }
    }

    /**
     * Simulates the next frame of the level's simulation. If we're not already
     * in a simulation (i.e. ghostMode is false), sets ghostMode to true and
     * starts one.
     * @return   The hitbox of the Ghost's location at the next frame.
     */
    public Hitbox getNextGhostHitbox() {
        if (!ghostMode) {
            this.reset();
            ghostMode = true;
        }
        this.simulateFrame();
        if (this.levelSolver.finishedLevel) {
            return null;
        } else {
            return this.levelSolver.getHitbox();
        }
    }

    public List<Entity> getObjects() {
        return entities;
    }

    /**
     * Simulates the next frame of the level. Checks for a time limit death,
     * then calls the step() method for every possible Entity it knows of.
     */
    public void simulateFrame() {
        currentFrame++;
        // Check to see if the time limit has run out; if it has, kill the
        // dorf
        if ((!ghostMode)&&(currentFrame>timeLimit)) {
            player.die();
        }

        for (Entity i : entities) {
            i.step();
        }
        if (testingEntities != null) {
            for (Entity i : testingEntities) {
                i.step();
            }
        }
        //Move the Ghost/Dorf.
        if (ghostMode) {
            levelSolver.step();
        } else {
            player.step();
        }

    }

    /**
     * Resets the conditions of every object in the level back to starting
     * conditions. This method is used during the actual game; note that it
     * is different from reset(int frame).
     */
    public void reset() {
        currentFrame = 0;
        for (Entity i : entities) {
            i.reset();
        }
        if (testingEntities != null) {
            for (Entity i : testingEntities) {
                i.reset();
            }
        }
        player.reset();
        levelSolver.reset();
    }

    /**
     * Resets the conditions of every object in the level back to starting
     * conditions and then simulates the level using the ghost up to the
     * specified frame(which must be positive).
     *
     * This method is intended for use during level generation, no assumptions
     * are made about what happens when the ghost wins or loses as a result.
     * Note the difference between this and reset().
     */
    public void reset(int frame) {
        this.reset();
        while (currentFrame < frame) {
            this.simulateFrame();
        }
    }


    public boolean getGhostMode() {
        return ghostMode;
    }

    public double getDifficulty() { return this.difficulty; }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getRemainingTime() {
        return timeLimit - currentFrame;
    }
}
