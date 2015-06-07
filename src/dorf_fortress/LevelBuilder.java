package dorf_fortress;

import java.util.List;
import java.util.Random;

/**
 * TODO make a description here
 * Created by jamie on 5/27/15.
 */
public class LevelBuilder {
    private Model model;
    private List<Entity> entities;
    private GameController controller;
    private GhostInputSource ghostInput;
    private Ghost levelSolver;
    private Random randomGenerator;
    private Platform startPlatform;
    private int lastSafeFrame;
    private boolean levelFinished;
    private boolean holdingRight;
    private boolean holdingLeft;
    private boolean holdingUp;
    private boolean holdingDown;
    private boolean jumpHandled;
    private boolean justPlacedPlatform;

    public LevelBuilder(Model model, List<Entity> entities,
                        GameController controller) {
        this.model = model;
        this.entities = entities;
        this.controller = controller;

        long rgenseed = System.currentTimeMillis();
//        rgenseed = (long)1433632172184;
        this.randomGenerator = new Random();
        this.randomGenerator.setSeed(rgenseed);
        System.out.println("Seed is: " + rgenseed);
//        this.randomGenerator = new Random(); TODO return random to default
    }

    public void makeTestLevel() {
        generateLevel();

        /* Platforms */

//        Platform spawn = new ConveyorPlatform(128,32,-100,
//                200,this.model);
//        this.entities.add(spawn);
//
//        Platform spawn2 = new ConveyorPlatform(128,32,-200,
//                200,this.model);
//        this.entities.add(spawn2);
//
//        Platform spawn3 = new ConveyorPlatform(128,32,-300,
//                200,this.model);
//        this.entities.add(spawn3);
//
//        Platform spawn4 = new ConveyorPlatform(128,32,-400,
//                200,this.model);
//        this.entities.add(spawn4);
//
//        Platform spawn5 = new ConveyorPlatform(128,32,-500,
//                200,this.model);
//        this.entities.add(spawn5);
//
//        Platform spawn6 = new ConveyorPlatform(128,32,-600,
//                200,this.model);
//        this.entities.add(spawn6);
//
//        Platform spawn7 = new ConveyorPlatform(128,32,-700,
//                200,this.model);
//        this.entities.add(spawn7);
//
//        Platform spawn8 = new ConveyorPlatform(128,32,-800,
//                200,this.model);
//        this.entities.add(spawn8);
//
//        Platform spawn9 = new ConveyorPlatform(128,32,-900,
//                200,this.model);
//        this.entities.add(spawn9);
//
//        Platform spawn10 = new ConveyorPlatform(128,32,-1000,
//                200,this.model);
//        this.entities.add(spawn10);
//
//        Platform platform1 = new SolidPlatform(128,32,150,
//                300,this.model);
//        this.entities.add(platform1);
//
//        Platform platform2 = new TrampolinePlatform(128,32,300,
//                400,this.model);
//        this.entities.add(platform2);
//
//        Platform platform3 = new SolidPlatform(128,32,450,
//                150,this.model);
//        this.entities.add(platform3);
//
//        Platform platform4 = new SolidPlatform(128,32,650,
//                300,this.model);
//        this.entities.add(platform4);
//
//        Platform platform5 = new SolidPlatform(128,32,800,
//                200,this.model);
//        this.entities.add(platform5);
//
//        Platform platform6 = new BouncyPlatform(128,32,850,
//                400,this.model);
//        this.entities.add(platform6);
//
//        Platform platform7 = new SolidPlatform(128,32,1000,
//                150,this.model);
//        this.entities.add(platform7);

//        DisappearingGhost creepy = new DisappearingGhost(32,32,-10,70,60,60,
//                180,10,model);
//        this.entities.add(creepy);
//
//
//        /* Obstacles */
//
//        Obstacle on_platform1 = new KillBlock(
//                32,32,192,256,this.model);
//        this.entities.add(on_platform1);
//
//        Obstacle under_platform3 = new KillBlock(
//                32,32,512,256,this.model);
//        this.entities.add(under_platform3);
//
//        Obstacle fireball_0 = new SimpleUpwardsKillBall(
//                33,33,425,80,this.model);
//        entities.add(fireball_0);
//
//        Obstacle fireball_1 = new SimpleUpwardsKillBall(
//                33,33,775,100,this.model);
//        entities.add(fireball_1);
//
//        Obstacle fireball_2 = new SimpleUpwardsKillBall(
//                33,33,950,150,this.model);
//        entities.add(fireball_2);
//
//
//        /* Goal */
//
//        WinBlock victory_arch = new WinBlock(
//                113,109,1000+7,150-109,this.model);
//        this.entities.add(victory_arch);


        /* Dorf Placement */
        this.addSpritesToRoot();
    }

    /**
     * Places the Dorf and ghost such that they will begin slightly above the
     * starting platform in the center.
     */
    public void placeDorf() {
        double xStart = startPlatform.getX() + 32;
        double yStart = startPlatform.getY() - 100;

        //Make a Dorf!
        Dorf ferdinand = new Dorf(22, 32, xStart, yStart, model);
        model.player = ferdinand;
        controller.addSpriteToRoot(ferdinand.getSprite());

        //Make a Ghost!
        ghostInput = new GhostInputSource();
//        ghostInput = new GhostInputSource("src/dorf_fortress/NewDemoInputs.txt");
        Ghost casper = new Ghost(32, 32, xStart, yStart, model, ghostInput);
        model.levelSolver = casper;
        this.levelSolver = casper;
    }





    /**
     * This is where the procedural level generation comes in. It works by
     * first placing the starting platform and the dorf
     */
    public void generateLevel() {
        // Makes the starting platform
        int startPlatformX = 0;
        int startPlatformY = 200;
        Platform spawn = new SolidPlatform(128,32,startPlatformX,
                startPlatformY,this.model);
        this.entities.add(spawn);
        this.startPlatform = spawn;
        this.placeDorf();

        // Initializes model so it will be properly simulating the ghost
        // every frame
        model.setGhostMode(true);


        // Adds blank inputs to the front of the ghost input to make level
        // solving nicer for humans to play later
        for (int i = 0; i < 30; i++) {
            ghostInput.addInput(false, false, false, false);
        }
        this.lastSafeFrame = 30;
        model.reset(29);


        int lastPlatformFrame = 30;
        boolean downStart = false;
        int downFrame =0;
        int fallTreeFrame =0; // If the dorf tries to fall
        holdingUp = false;
        holdingRight = true;
        holdingLeft = false;
        while (!levelFinished) {
            System.out.println();
            System.out.println("frame: " + model.getCurrentFrame());
            System.out.println("x: " + levelSolver.getX());
            System.out.println("GhostInputSource size: " + ghostInput
                    .storedInput.size());

            // if its currently on a platform
            if (levelSolver.curPlatform!=null) {
                if (justPlacedPlatform) {
                    justPlacedPlatform = false;
                    ghostInput.removeInputs(model.getCurrentFrame());
                }
                lastPlatformFrame = model.getCurrentFrame();
                downStart = true;
                jumpHandled = false;
                handlePlatformInput();
                fallTreeFrame = 0;

            // If the dorf is in the air (because it isnt on a platform) then
            // it asks if this is the first frame off of a platform
            // (jumpHandled)
            } else if (!jumpHandled) {
                // if velocity is positive then it was because the platform
                // gave it velocity (eg. bouncy platform or the ghost jumped
                // of its own volition) so don't rewind the clock
                if (levelSolver.getY_velocity()>0) {
                    jumpHandled = true;
                } else {
                    double chanceNoJump = 0.15;
                    if (chanceNoJump>randomGenerator.nextDouble()) {
                        fallTreeFrame = model.getCurrentFrame() - 10;
                        jumpHandled = true;
                    } else {
                        int fudge = 2 + randomGenerator.nextInt(10);
                        model.reset(lastPlatformFrame - fudge);
                        ghostInput.removeInputs(lastPlatformFrame - fudge);
                        lastPlatformFrame = lastPlatformFrame - fudge;
                        holdingUp = true;
                        jumpHandled = true;
                    }
                }

            } else if (levelSolver.y_velocity>0) {
                handleUpInput();
                System.out.println("going up");


            } else if (downStart) {
                System.out.println("first down");
//                System.out.println("FinalX: " + levelSolver.finalX + " " +
//                        "FinalFrame: " + levelSolver.frameFinished);
//                System.out.println("selected frame = " + platformPlace);

                downFrame = model.getCurrentFrame();
                downStart = false;


            } else if (levelSolver.finishedLevel) {
//                System.out.println
//                        ("------------------------------------------------");
                boolean placed = placePlatform(downFrame, levelSolver
                        .frameFinished);
                if (placed) {
                    justPlacedPlatform = true;
                    downFrame = 0;
                } else {
                    model.reset(fallTreeFrame);
                }

            } else {
                handleDownInput();
            }

            ghostInput.addInput(holdingLeft,holdingRight,holdingUp,holdingDown);
            model.simulateFrame();
        }
    }

    /**
     * Handles the logic for how the computer manages creating new inputs
     * while the dorf is jumping up
     */
    public void handleUpInput() {
        double upUnholdingChance = 0.05;
        double rightUnholdingChance = 0.05;
        double rightHoldingChance = 0.05;

        // Randomly chooses to unpress the up key
        if (holdingUp) {
            if (upUnholdingChance>randomGenerator.nextDouble()) {
                holdingUp = false;
            }
        }
        if (holdingRight) {
            if (rightUnholdingChance>randomGenerator.nextDouble()) {
                holdingRight = false;
            }
        }
        if (!holdingRight) {
            if (rightHoldingChance>randomGenerator.nextDouble()) {
                holdingRight = true;
            }
        }
    }
    /**
     * Handles the logic for how the computer adds new input when the dorf is
     * falling
     */
    private void handleDownInput() {
        double rightUnholdingChance = 0.20;
        double leftTapChance = 0.50;
        double leftUnholdingChance = 0.30;
        double rightTapChance = 0.20;
        if (holdingRight) {
            if (rightUnholdingChance>randomGenerator.nextDouble()) {
                holdingRight = false;
                if (leftTapChance>randomGenerator.nextDouble()) {
                    holdingLeft = true;
                }
            }
        }
        if (holdingLeft) {
            if (leftUnholdingChance>randomGenerator.nextDouble()) {
                holdingLeft = false;
            }
        }
        if (!holdingRight) {
            if (rightTapChance>randomGenerator.nextDouble()) {
                holdingRight = true;
            }
        }
    }
    /**
     * Handles logic for how the computer adds the dorf's input if the dorf
     * is currently on a platform
     */
    private void handlePlatformInput() {
        double rightUnholdingChance = 0.01;
        double rightTapChance = 0.10;
        double leftTapChance = 0.05;
        double leftUnholdingChance = 0.30;
        double upTapChance = 0.005;
        holdingUp = false;

        // If the ghost is on a jump boosting platfomr, increase the chances
        // of pressing up for any given platform
        if (levelSolver.curPlatform instanceof BouncyPlatform) {
            upTapChance = 0.10;
            rightTapChance = 0.70;
        }
        if (holdingLeft) {
            if (leftUnholdingChance>randomGenerator.nextDouble()) {
                holdingLeft = false;
            }
        }
        if (holdingRight) {
            if (rightUnholdingChance>randomGenerator.nextDouble()) {
                holdingRight = false;
                if (leftTapChance>randomGenerator.nextDouble()) {
                    holdingLeft = true;
                }
            }
        }
        if (!holdingRight) {
            if (rightTapChance > randomGenerator.nextDouble()) {
                holdingRight = true;
            }
        }
        if(!holdingUp) {
            if (upTapChance > randomGenerator.nextDouble()) {
                holdingUp = true;
            }
        }
    }


    /**
     * Places a platform to 'catch' the falling ghost between the specified
     * start and end frame in the simulation, will ensure that the platform
     * placed will be above the bottom of the level, it will also ensure that
     * the platform is not too high. Returns true if the platform could be
     * placed given the parameters, false if it could not. Also carries with
     * it a random chance to place the win block which increases the more
     * platforms that have been placed.
     * @param startFrame
     * @param endFrame
     * @return
     */
    private boolean placePlatform(int startFrame, int endFrame) {
        double top_limit = 100;
        double bottom_limit = model.SCENE_HEIGHT - 32 - levelSolver.height;

        // Randomly selects a frame then checks that the ghost is within
        // top_limit and bottom_limit ycoor. If it fails 10 times in a row it
        // returns false
        int selectFrame = 2 + randomGenerator.nextInt(endFrame-startFrame-2);
        model.reset(startFrame + selectFrame);
        int times_tried = 1;
        while ((top_limit>levelSolver.getY())
                ||(levelSolver.getY()>bottom_limit)) {
            selectFrame = 2 + randomGenerator.nextInt(endFrame-startFrame-2);
            model.reset(startFrame+selectFrame);
            times_tried++;
            if (times_tried>10) {
                return false;
            }
        }

        // places the platform to "catch" the ghost
        Platform placed_platform = platformFactory();

        // Tests to make sure the dorf does land on the platform (platform
        // might interfere with prior path
        model.reset(startFrame+selectFrame);
        if (!(levelSolver.curPlatform==placed_platform)) {
            entities.remove(placed_platform);
            return false;
        }

        // Randomly places the win block if more than 7 platforms exist
        if (entities.size()>9) {
            double chance = (entities.size()-9)/14.0;
            if (randomGenerator.nextDouble()<chance) {
                // Only spawns the door ona normal platform
                if (placed_platform instanceof SolidPlatform) {
                    WinBlock victory_arch = new WinBlock(113, 109,
                            placed_platform.getX() + 5, placed_platform.getY() - 109,
                            this.model);
                    this.entities.add(victory_arch);
                    levelFinished = true;
                }
            }
        }

        model.reset(startFrame);
        justPlacedPlatform = true;
        return true;
    }


    /**
     * Handles the factors that determine which type of platform to build at
     * a particular point and builds it to intersect with the ghost path. If
     * the dorf is below a certian height then it is more likely to select
     * either a trampolene or jump boost platform
     */
    public Platform platformFactory() {
        double bouncyPlatformChance = 0.70;
        double boostPlatformCuttof = 0.70;

        double wayDown = levelSolver.getY()/model.SCENE_HEIGHT;
        int fudgeFactor = 10 + randomGenerator.nextInt((int)(50*wayDown));
        double xCoor = levelSolver.getX() + levelSolver.width - fudgeFactor;
        double yCoor = levelSolver.getY() + levelSolver.height;
        Platform platform = null;

        if (wayDown>boostPlatformCuttof) {
            if (bouncyPlatformChance>randomGenerator.nextDouble()) {
                platform = new BouncyPlatform(128,32,xCoor, yCoor,this.model);
            } else {
                platform = new SolidPlatform(128,32,xCoor, yCoor,this.model);
            }
        } else {
            platform = new SolidPlatform(128,32,xCoor, yCoor,this.model);
        }
        this.entities.add(platform);
        return platform;
    }




    /**
     * This method loops through all of the entities in the entity list and
     * adds their sprites to the root
     */
    public void addSpritesToRoot() {
        for (Entity e: entities) {
            this.controller.addSpriteToRoot(e.getSprite());
        }
    }
}