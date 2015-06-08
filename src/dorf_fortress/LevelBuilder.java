package dorf_fortress;

import java.util.List;
import java.util.Random;

/**
 * A LevelBuilder object manages the procedural generation of platforms on a
 * level as well as the construction of a series of inputs for the level
 * solving Ghost. The goal of this mehtod of generation is to construct
 * levels that are necessaraly solvable by a known set of computer inputs
 * every time.
 * The level building script works by having the model simulate through the
 * level with the current set of ghost inputs as normal
 * and then adding to the input based on the current conditions experienced
 * by the ghost (ex. being on a platform makes it more likely to press the
 * left key to stop motion, or being in the air going up the ghost is more
 * likely to go right then in the second half of its jump).
 *
 * To place platforms the LevelBuilder keeps track of the first frame where
 * the ghost started falling (downwards velocity) and then simulates until
 * the dorf dies (or lands on a platform resetting everything) then simulates
 * to a randomly selected frame between starting to fall and dying and places
 * a platform exactly there to catch it. Also, if the dorf runs off the edge
 * of a platform it will sometimes revert several frames back to before the
 * dorf fell and add a jump to the input instead.
 */
public class LevelBuilder {
    private Model model;
    private List<Entity> entities;
    private GameController controller;
    private GhostInputBuffer ghostInput;
    private Ghost levelSolver;
    private Random randomGenerator;
    private Platform startPlatform;
    private double difficulty;
    private boolean levelFinished;
    private boolean holdingRight;
    private boolean holdingLeft;
    private boolean holdingUp;
    private boolean holdingDown;
    private boolean jumpHandled;
    private boolean justPlacedPlatform;

    /**
     * Creates and stores variables needed for level generation
     *
     * @param model The simulation the level gets built to
     * @param entities The list of entities that model simulats
     * @param controller The view/controller JFX object that the generated
     *                   sprites get added to for display
     */
    public LevelBuilder(Model model, List<Entity> entities,
                        GameController controller) {
        this.model = model;
        this.entities = entities;
        this.controller = controller;
        this.randomGenerator = new Random();
        this.difficulty = model.getDifficulty();
    }


    public void makeLevel() {
        generateLevel();
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
        ghostInput = new GhostInputBuffer();
        Ghost casper = new Ghost(22, 32, xStart, yStart, model, ghostInput);
        model.levelSolver = casper;
        this.levelSolver = casper;
    }


    /**
     * This method is what does the majority of the procedural level
     * generation by having the model simulate the level and then determining
     * conditions that the ghost is currently in and adding input to the
     * ghosts input buffer just before executing the input on simulation.
     */
    public void generateLevel() {
        // Makes the starting platform
        int startPlatformX = 0;
        int startPlatformY = 200;
        Platform spawn = new SolidPlatform(128,32,startPlatformX,
                startPlatformY,this.model);
        this.entities.add(spawn);
        this.startPlatform = spawn;

        // Makes a safezone hitbox above the start platform for obstacle
        // generation
        Hitbox spawnZone = new RectangleHitbox(spawn.width,200);
        spawnZone.setY(spawn.getY()-200);
        spawnZone.setX(spawn.getX());
        model.spawnSafeZone = spawnZone;

        // Initializes model so it will be properly simulating the ghost
        // every frame
        this.placeDorf();
        model.setGhostMode(true);


        // Adds blank inputs to the front of the ghost input to make level
        // solving nicer for humans to play later
        for (int i = 0; i < 30; i++) {
            ghostInput.addInput(false, false, false, false);
        }
        model.reset(29);


        int lastPlatformFrame = 30; // The last frame the dorf was
        // successfully on a platform
        boolean downStart = false;
        int downFrame =0;
        holdingUp = false;
        holdingRight = true;
        holdingLeft = false;

        // The primary generation loop. This while loop runs through the code
        // until the level has been completed. The variable levelFinished is
        // set by the platform placer if it has chosen to place a win block.
        // The winblock will necessarily be placed as the odds of it being
        // selected become 100% with enough platforms
        while (!levelFinished) {

            // if its currently on a platform, reset variables associated
            // with platform placement and adjust the input accordingly
            if (levelSolver.curPlatform!=null) {
                if (justPlacedPlatform) {
                    justPlacedPlatform = false;
                    ghostInput.removeInputs(model.getCurrentFrame());
                }
                lastPlatformFrame = model.getCurrentFrame();
                downStart = true;
                jumpHandled = false;
                handlePlatformInput();

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

                    // Sometimes it decides to turn back the clock by a few
                    // frames and make a jump, sometimes it will allow itself
                    // to just fall of the platform
                    double chanceNoJump = 0.15;
                    if (chanceNoJump>randomGenerator.nextDouble()) {
                        // Stores back a few frames so it can get back in the
                        // event that it was too low to fall
                        lastPlatformFrame = model.getCurrentFrame() - 10;
                        jumpHandled = true;
                    } else {
                        int fudge = 4 + randomGenerator.nextInt(10);
                        model.reset(lastPlatformFrame - fudge);
                        ghostInput.removeInputs(lastPlatformFrame - fudge);
                        lastPlatformFrame = lastPlatformFrame - fudge;
                        holdingUp = true;
                        jumpHandled = true;
                    }
                }

            // If the velocity is positive, then the dorf is going up and
            // since jumpHandled is already true, the dorf probably jumped
            } else if (levelSolver.y_velocity>0) {
                handleUpInput();

            // If it gets here, then the dorf is not on a platform, and it
            // does not have an upwards velocity meaning that the dorf must
            // be falling right now, so flag this frame as the first frame
            // for generation and continue running. (NOTE: this will
            // sometimes result in the dorf falling back onto an extant
            // platform, if this happens then downStart will be reset while
            // its on the platform.
            } else if (downStart) {
                downFrame = model.getCurrentFrame();
                downStart = false;

            // If the levelSolver has finished the level, then at this point
            // it cant have completed the level because the no exit has been
            // built so it means the dorf must have died, in that case we
            // need to build a platform to catch him now dont we?
            } else if (levelSolver.finishedLevel) {
                boolean placed = placePlatform(downFrame, levelSolver
                        .frameFinished);
                if (placed) {
                    justPlacedPlatform = true;
                    downFrame = 0;
                } else {
                    model.reset(lastPlatformFrame);
                }

            // If none of these other things are true, then the dorf must be
            // plin'ol fallin.
            } else {
                handleDownInput();
            }

            ghostInput.addInput(holdingLeft,holdingRight,holdingUp,holdingDown);
            model.simulateFrame();
        }
    }

    /**
     * Handles the logic for how the computer manages creating new inputs
     * while the dorf is jumping up. This is weighted so it is somewhat
     * likely that the dorf will release the up key during the first half of
     * a jump but it might also hold or unhold the right key.
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
     * falling. Here the dorf is more likely to press the left key to kill
     * momentum or to release the key. This was intended to mimic "fine
     * tuning" that a human might do before the end of a jump to hit a target.
     */
    private void handleDownInput() {
        double rightUnholdingChance = 0.05;
        double leftTapChance = 0.25;
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
     * is currently on a platform. For the most part, the dorf is very likely
     * to hod the right key, but it also might sometimes excecute a jump or
     * tap the left key or release the right key in order to pause and avoid
     * an obstacle.
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
        if ((levelSolver.curPlatform instanceof BouncyPlatform)||
                (levelSolver.curPlatform instanceof TrampolinePlatform)){
            upTapChance = 0.10;
            rightTapChance = 0.70;
        }
        // Clears jump input for the dorf so it will be on the convayor
        // platform for at least a moment
        if (levelSolver.curPlatform instanceof ConveyorPlatform) {
            holdingUp = false;
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
        int selectFrame = 4 + randomGenerator.nextInt(endFrame-startFrame-2);
        model.reset(startFrame + selectFrame);
        int times_tried = 1;
        while ((top_limit>levelSolver.getY())
                ||(levelSolver.getY()>bottom_limit)) {
            selectFrame = 4 + randomGenerator.nextInt(endFrame-startFrame-2);
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

        // Randomly places the win block if more than 9 platforms exist
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
     * either a trampolene or jump boost platform. It can also choose to
     * construct a conveyor platform.
     *
     * At call it will use the difficulty statistic to adjust the chance of
     * particular the ghost platforms that dissapear and reappear.
     */
    public Platform platformFactory() {
        double bouncyPlatformChance = 0.75;
        double boostPlatformCuttof = 0.70;
        double conveyorPlatformChance = 0.10;
        double disappearingPlatformChance = 0.15;

        // Adjusts platform spawn chances based on difficulty
        if (difficulty < 3) {
            disappearingPlatformChance = 0.05;
        }
        if (difficulty > 9) {
            disappearingPlatformChance = 0.30;
        }

        // Getting variables for where the platform will be constructed
        double wayDown = levelSolver.getY()/model.SCENE_HEIGHT;
        int fudgeFactor = 11 + randomGenerator.nextInt((int)(40*wayDown));
        double xCoor = levelSolver.getX() + levelSolver.width - fudgeFactor;
        double yCoor = levelSolver.getY() + levelSolver.height;
        Platform platform = null;


        // First determines if either of the jump boosting platforms should
        // be placed (i.e. the ghost is below a certian part of the level)
        if (wayDown>boostPlatformCuttof) {
            if (bouncyPlatformChance>randomGenerator.nextDouble()) {

                // Bases the chance of spawing a trampoline platform on the
                // dorf's velocity
                double trampChance = (levelSolver.getY_velocity()/-600) - .30;

                if (trampChance>randomGenerator.nextDouble()) {
                    platform = new TrampolinePlatform(192,32,xCoor-20,yCoor,
                            model);
                } else {
                    platform = new BouncyPlatform(128,32,xCoor, yCoor,this.model);
                }
                this.entities.add(platform);
                return platform;
            }
        }


        // Handles non-height dependant platforms
        if (conveyorPlatformChance>randomGenerator.nextDouble()) {
            int numToMake = 1 + randomGenerator.nextInt(4);
            platform = new ConveyorPlatform(96, 32, numToMake, xCoor,
                    yCoor, this.model);
            this.entities.add(platform);
            return platform;
        }
        if (disappearingPlatformChance>randomGenerator.nextDouble()) {
            platform = new FadingPlatform(128,32,xCoor, yCoor, this.model,
                    model.getCurrentFrame());
            this.entities.add(platform);
            return platform;
        }
        platform = new SolidPlatform(128,32,xCoor, yCoor,this.model);
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