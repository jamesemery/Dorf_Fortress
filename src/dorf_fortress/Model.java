package dorf_fortress;


import java.util.List;
import java.util.ArrayList;
/**
 * Created by jamie on 5/27/15.
 */
public class Model {
    private List<Entity> entities;
    public Dorf player;
    public Ghost levelSolver;
    private boolean ghostMode;
    public List<Entity> testingEntities; //used for character generation
    private GameController controller;
    public static double SCENE_HEIGHT;



    public Model(GameController controller, double sceneHeight) {
        this.SCENE_HEIGHT = sceneHeight;
        this.controller = controller;
        entities = new ArrayList<Entity>();
        //Make a Dorf!
        Dorf ferdinand = new Dorf("sprites/BrownDorf.png", 32, 32, 34,
                100, this);
        this.player = ferdinand;
        controller.addSpriteToRoot(ferdinand.getSprite());

        //Make a Ghost!
        Ghost casper = new Ghost("sprites/BrownDorf.png", 32, 32, 34,
                100, this);
        this.levelSolver = casper;
//        this.controller.addSpriteToRoot(casper.getSprite());


        /*
         * RANDOM JUMP TESTING BEGINS
         */
        LevelBuilder levelBuilder = new LevelBuilder(this,entities,controller);
        levelBuilder.makeTestLevel();
//       //Make a Platform?
//       SolidPlatform ground1 = new SolidPlatform("sprites/128x32platform.png",128,32,34,
//                400,this);
//       entities.add(ground1);
//       controller.addSpriteToRoot(ground1.getSprite());
//
//       //Make a Platform?
//       SolidPlatform ground2 = new SolidPlatform("sprites/128x32platform.png",128,32,162,
//                400,this);
//       entities.add(ground2);
//       controller.addSpriteToRoot(ground2.getSprite());
//
//       //Make a Platform?
//       SolidPlatform ground3 = new SolidPlatform("sprites/128x32platform.png",128,32,290,
//                400,this);
//       entities.add(ground3);
//       controller.addSpriteToRoot(ground3.getSprite());
//
//       //Make a Platform?
//       SolidPlatform ground4 = new SolidPlatform("sprites/128x32platform.png",128,32,418,
//                400,this);
//       entities.add(ground4);
//       controller.addSpriteToRoot(ground4.getSprite());
//
//       BouncyPlatform level1 = new BouncyPlatform("sprites/basicPlatform.png",138,32,325,
//                300,this);
//       entities.add(level1);
//       controller.addSpriteToRoot(level1.getSprite());
//
//       SolidPlatform level2 = new SolidPlatform("sprites/128x32platform.png",128,32,197,
//                200,this);
//       entities.add(level2);
//       controller.addSpriteToRoot(level2.getSprite());
//
//       SolidPlatform level3 = new SolidPlatform("sprites/128x32platform.png",128,32,69,
//                100,this);
//       entities.add(level3);
//       controller.addSpriteToRoot(level3.getSprite());
//
//       KillBlock krell = new KillBlock("sprites/staticObstacleTransparent.png",32,32,200,
//                288, this);
//       entities.add(krell);
//       controller.addSpriteToRoot(krell.getSprite());

//        SimpleUpwardsKillBall jumpy = new SimpleUpwardsKillBall
//                ("sprites/BasicDorf.png",32,32,this,100,50);
//        entities.add(jumpy);
//        controller.addSpriteToRoot(jumpy.getSprite());

        /*
         * RANDOM JUMP TESTING ENDS
         */
//        ObstaclePlacer dangerMaker = new ObstaclePlacer(this,this.levelSolver);
//        dangerMaker.generateObstacles(40);
    }

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
            controller.addSpriteToRoot(levelSolver.getSprite());
        } else {
            this.reset();
            ghostMode = false;
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
            System.out.println(e);
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
        for (Entity i : entities){
            i.updateSprite();
        }
        if(testingEntities!=null) {
            for (Entity i : testingEntities) {
                i.updateSprite();
            }
        }
        if (ghostMode) {
            levelSolver.updateSprite();
        }
        else {
            player.updateSprite();
        }

    }

    // restets the level to the initial conditions for every entity contained
    // in the set
    public void reset() {
        for (Entity i : entities) {
            i.reset();
        }
        if (testingEntities!=null){
            for (Entity i : testingEntities) {
                i.reset();
            }
        }
        player.reset();
        levelSolver.reset();
    }


}

