package dorf_fortress;


import java.util.List;
import java.util.ArrayList;
/**
 * Created by jamie on 5/27/15.
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


//    static Model getInstance(GameController controller, double sceneHeight, double difficulty) {
//        //if(singleInstance == null) {
//            singleInstance = new Model(controller, sceneHeight, difficulty);
//        //}
//        return singleInstance;
//    }


    public Model(GameController controller, double sceneHeight, double difficulty) {
        this.SCENE_HEIGHT = sceneHeight;
        this.controller = controller;
        this.difficulty = difficulty;

        entities = new ArrayList<Entity>();
        LevelBuilder levelBuilder = new LevelBuilder(this, entities, controller);
        levelBuilder.makeTestLevel();
        ObstaclePlacer dangerMaker = new ObstaclePlacer(this, this.levelSolver);

        /*
         * TODO: Here's where the number of obstacles is set by the difficulty.
         * TODO: Fiddle with? We can make obstacle_count = n(difficulty) + c,
         * TODO: where c is the count at difficulty 0 and n scales it.
         */
        List tempList = new ArrayList<Integer>(); //TODO get rid of temp list

        // Chooses how many obstacles to place based on the number of platforms
        int obstacles = (int)(entities.size()*((this.difficulty/2) + 1));
        dangerMaker.generateObstacles(obstacles, tempList);
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
     * sets or removes the ghost mode from the level, if given argument is
     * true it sets it, and removes it if its set to false, also handles
     * turning on and off display of the dorf
     */
    public void setGhostMode(boolean val) {
        if (val) {
            this.reset();
            ghostMode = true;
            controller.removeSpriteFromRoot(levelSolver.getSprite());
            controller.removeSpriteFromRoot(player.getSprite());
            controller.addSpriteToRoot(levelSolver.getSprite());
        } else {
            this.reset();
            ghostMode = false;
            controller.removeSpriteFromRoot(player.getSprite());
            controller.addSpriteToRoot(player.getSprite());
            controller.removeSpriteFromRoot(levelSolver.getSprite());
        }
    }

    /**
     * Adds the given list of entities to the running simulation as well as
     * hooking them into the view properly
     */
    public void addEntities(List<Entity> newEntities) {
        for (Entity e : newEntities) {
            entities.add(e);
            controller.addSpriteToRoot(e.getSprite());
        }
    }


    /**
     * if the GhostMode is not on, it turns it on and resets the level then
     * it simulates one frame of the level and returns the hitbox for the
     * ghost frame at that perticular point. If the ghost finishes its run
     * though it returns a null
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


    public void simulateFrame() {
        currentFrame++;
        // Checks to see if the time limit has run out, if it has, kill the
        // dorf
        if ((!ghostMode)&&(currentFrame>timeLimit)) {
            player.die();
        }

        for (Entity i : entities) {
            if (i instanceof Ghost) {
                System.out.println("Ghost Shouldnt Be here");
            }
            i.step();
        }
        if (testingEntities != null) {
            for (Entity i : testingEntities) {
                i.step();
            }
        }
        if (ghostMode) {
            levelSolver.step();
        } else {
            player.step();
        }

    }

    /**
     * Resets the conditions of every object in the level back to starting
     * conditions
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
     * This class is intended for use during level generation, no assumptions
     * are made about what happens when the ghost wins or looses as a result
     */
    public void reset(int frame) {
        this.reset();
        while (currentFrame < frame) {
            this.simulateFrame();
        }
        System.out.println("Reset to " + frame);
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
