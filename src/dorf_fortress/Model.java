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


    static Model getInstance(GameController controller, double sceneHeight, double difficulty) {
        singleInstance = new Model(controller, sceneHeight, difficulty);
        return singleInstance;
    }


    private Model(GameController controller, double sceneHeight, double difficulty) {
        this.SCENE_HEIGHT = sceneHeight;
        this.controller = controller;
        this.difficulty = difficulty;

        entities = new ArrayList<Entity>();
        //Make a Dorf!
        Dorf ferdinand = new Dorf( 32, 32, 34, 100, this);
        this.player = ferdinand;
        controller.addSpriteToRoot(ferdinand.getSprite());

        //Make a Ghost!
        Ghost casper = new Ghost("sprites/GreyDorf.png", 32, 32, 34,
                100, this);
        this.levelSolver = casper;
//        this.controller.addSpriteToRoot(casper.getSprite());


        /*
         * RANDOM JUMP TESTING BEGINS
         */
        LevelBuilder levelBuilder = new LevelBuilder(this, entities, controller);
        levelBuilder.makeTestLevel();
        /*
         * RANDOM JUMP TESTING ENDS
         */
        ObstaclePlacer dangerMaker = new ObstaclePlacer(this, this.levelSolver);

        /*
         * TODO: Here's where the number of obstacles is set by the difficulty.
         * TODO: Fiddle with? We can make obstacle_count = n(difficulty) + c,
         * TODO: where c is the count at difficulty 0 and n scales it.
         */
        List tempList = new ArrayList<Integer>();
        dangerMaker.generateObstacles((int) Math.round(2.5 * this
                .difficulty), tempList);
        setGhostMode(false);
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
        for (Entity i : entities) {
            i.updateSprite();
        }
        if (testingEntities != null) {
            for (Entity i : testingEntities) {
                i.updateSprite();
            }
        }
        if (ghostMode) {
            levelSolver.updateSprite();
        } else {
            player.updateSprite();
        }

    }

    // Resets the level to the initial conditions for every entity contained
    // in the set
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


    public boolean getGhostMode() {
        return ghostMode;
    }

    public double getDifficulty() { return this.difficulty; }

    public int getCurrentFrame() {
        return currentFrame;
    }
}
