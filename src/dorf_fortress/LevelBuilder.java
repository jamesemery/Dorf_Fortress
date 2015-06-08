package dorf_fortress;

import java.util.List;
import java.util.Random;

/**
 * A LevelBuilder object manages the procedural generation of platforms on a
 * level as well as the construction of a series of inputs for the level
 * solving Ghost. The goal of this method of generation is to construct
 * levels that are necessarily solvable by a known set of computer inputs.
 *
 * The level building script works by having the model simulate through the
 * level with the current set of ghost inputs as normal, and then adding to the
 * input based on the current conditions experienced by the ghost (ex. being on
 * a platform makes it more likely to press the left key to stop motion, or
 * being in the air going up the ghost is more likely to go right then in the
 * second half of its jump).
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
    private Model simulation;
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
     * @param simulation   The simulation the level gets built to
     * @param entities   The list of entities that model simulates
     * @param controller   The view/controller JFX object that the generated
     *                   sprites get added to for display
     */
    public LevelBuilder(Model simulation, List<Entity> entities,
                        GameController controller) {
        this.simulation = simulation;
        this.entities = entities;
        this.controller = controller;
        this.randomGenerator = new Random();
        this.difficulty = simulation.getDifficulty();
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
        double xStart = this.startPlatform.getX() + 32;
        double yStart = this.startPlatform.getY() - 100;

        //Make a Dorf!
        Dorf ferdinand = new Dorf(22, 32, xStart, yStart, this.simulation);
        this.simulation.player = ferdinand;
        this.controller.addSpriteToRoot(ferdinand.getSprite());

        //Make a Ghost!
        this.ghostInput = new GhostInputBuffer();
        Ghost casper = new Ghost(22, 32, xStart, yStart, this.simulation,
                this.ghostInput);
        this.simulation.levelSolver = casper;
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
                startPlatformY,this.simulation);
        this.entities.add(spawn);
        this.startPlatform = spawn;

        // Makes a safezone hitbox above the start platform for obstacle
        // generation
        Hitbox spawnZone = new RectangleHitbox(spawn.width,200);
        spawnZone.setY(spawn.getY() - 200);
        spawnZone.setX(spawn.getX());
        this.simulation.spawnSafeZone = spawnZone;

        // Initializes model so it will be properly simulating the ghost
        // every frame
        this.placeDorf();
        this.simulation.setGhostMode(true);


        // Adds blank inputs to the front of the ghost input to make level
        // solving nicer for humans to play later
        for (int i = 0; i < 30; i++) {
            this.ghostInput.addInput(false, false, false, false);
        }
        this.simulation.reset(29);


        int lastPlatformFrame = 30; // The last frame the dorf was
        // successfully on a platform
        boolean downStart = false;
        int downFrame =0;
        this.holdingUp = false;
        this.holdingRight = true;
        this.holdingLeft = false;

        // The primary generation loop. This while loop runs through the code
        // until the level has been completed. The variable levelFinished is
        // set by the platform placer if it has chosen to place a win block.
        // The winblock will necessarily be placed as the odds of it being
        // selected become 100% with enough platforms
        while (!this.levelFinished) {

            // if its currently on a platform, reset variables associated
            // with platform placement and adjust the input accordingly
            if (this.levelSolver.curPlatform!=null) {
                if (this.justPlacedPlatform) {
                    this.justPlacedPlatform = false;
                    this.ghostInput.removeInputs(
                            this.simulation.getCurrentFrame());
                }
                lastPlatformFrame = this.simulation.getCurrentFrame();
                downStart = true;
                this.jumpHandled = false;
                handlePlatformInput();

            // If the dorf is in the air (because it isn't on a platform) then
            // it asks if this is the first frame off of a platform
            // (jumpHandled)
            } else if (!this.jumpHandled) {
                // if velocity is positive then it was because the platform
                // gave it velocity (eg. bouncy platform or the ghost jumped
                // of its own volition) so don't rewind the clock
                if (this.levelSolver.getY_velocity()>0) {
                    this.jumpHandled = true;
                } else {

                    // Sometimes it decides to turn back the clock by a few
                    // frames and make a jump, sometimes it will allow itself
                    // to just fall of the platform
                    double chanceNoJump = 0.15;
                    if (chanceNoJump>this.randomGenerator.nextDouble()) {
                        // Stores back a few frames so it can get back in the
                        // event that it was too low to fall
                        lastPlatformFrame = this.simulation.getCurrentFrame()
                                            - 10;
                        this.jumpHandled = true;
                    } else {
                        int fudge = 4 + this.randomGenerator.nextInt(10);
                        this.simulation.reset(lastPlatformFrame - fudge);
                        this.ghostInput.removeInputs(lastPlatformFrame - fudge);
                        lastPlatformFrame = lastPlatformFrame - fudge;
                        this.holdingUp = true;
                        this.jumpHandled = true;
                    }
                }

            // If the velocity is positive, then the dorf is going up and
            // since jumpHandled is already true, the dorf probably jumped
            } else if (this.levelSolver.y_velocity>0) {
                handleUpInput();

            // If it gets here, then the dorf is not on a platform, and it
            // does not have an upwards velocity meaning that the dorf must
            // be falling right now, so flag this frame as the first frame
            // for generation and continue running. (NOTE: this will
            // sometimes result in the dorf falling back onto an extant
            // platform, if this happens then downStart will be reset while
            // its on the platform.
            } else if (downStart) {
                downFrame = this.simulation.getCurrentFrame();
                downStart = false;

            // If the levelSolver has finished the level, then at this point
            // it cant have completed the level because the no exit has been
            // built so it means the dorf must have died, in that case we
            // need to build a platform to catch him now dont we?
            } else if (this.levelSolver.finishedLevel) {
                boolean placed = placePlatform(downFrame,
                        this.levelSolver.frameFinished);
                if (placed) {
                    this.justPlacedPlatform = true;
                    downFrame = 0;
                } else {
                    this.simulation.reset(lastPlatformFrame);
                }

            // If none of these other things are true, then the dorf must be
            // plin'ol fallin.
            } else {
                handleDownInput();
            }

            this.ghostInput.addInput(this.holdingLeft,this.holdingRight,
                                     this.holdingUp, this.holdingDown);
            this.simulation.simulateFrame();
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
        if (this.holdingUp) {
            if (upUnholdingChance > this.randomGenerator.nextDouble()) {
                this.holdingUp = false;
            }
        }
        if (this.holdingRight) {
            if (rightUnholdingChance > this.randomGenerator.nextDouble()) {
                this.holdingRight = false;
            }
        }
        if (!this.holdingRight) {
            if (rightHoldingChance > this.randomGenerator.nextDouble()) {
                this.holdingRight = true;
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
        if (this.holdingRight) {
            if (rightUnholdingChance > this.randomGenerator.nextDouble()) {
                this.holdingRight = false;
                if (leftTapChance > this.randomGenerator.nextDouble()) {
                    this.holdingLeft = true;
                }
            }
        }
        if (this.holdingLeft) {
            if (leftUnholdingChance > this.randomGenerator.nextDouble()) {
                this.holdingLeft = false;
            }
        }
        if (!this.holdingRight) {
            if (rightTapChance > this.randomGenerator.nextDouble()) {
                this.holdingRight = true;
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
        this.holdingUp = false;

        // If the ghost is on a jump boosting platfomr, increase the chances
        // of pressing up for any given platform
        if ((this.levelSolver.curPlatform instanceof BouncyPlatform)||
                (this.levelSolver.curPlatform instanceof TrampolinePlatform)){
            upTapChance = 0.10;
            rightTapChance = 0.70;
        }
        // Clears jump input for the dorf so it will be on the convayor
        // platform for at least a moment
        if (this.levelSolver.curPlatform instanceof ConveyorPlatform) {
            this.holdingUp = false;
        }
        if (this.holdingLeft) {
            if (leftUnholdingChance > this.randomGenerator.nextDouble()) {
                this.holdingLeft = false;
            }
        }
        if (this.holdingRight) {
            if (rightUnholdingChance > this.randomGenerator.nextDouble()) {
                this.holdingRight = false;
                if (leftTapChance > this.randomGenerator.nextDouble()) {
                    this.holdingLeft = true;
                }
            }
        }
        if (!this.holdingRight) {
            if (rightTapChance > this.randomGenerator.nextDouble()) {
                this.holdingRight = true;
            }
        }
        if(!this.holdingUp) {
            if (upTapChance > this.randomGenerator.nextDouble()) {
                this.holdingUp = true;
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
        double bottom_limit = Model.SCENE_HEIGHT - 32 - levelSolver.height;

        // Randomly selects a frame then checks that the ghost is within
        // top_limit and bottom_limit ycoor. If it fails 10 times in a row it
        // returns false
        int selectFrame = 4 + this.randomGenerator.nextInt(endFrame-startFrame-2);
        this.simulation.reset(startFrame + selectFrame);
        int times_tried = 1;
        while ((top_limit > this.levelSolver.getY())
                || (this.levelSolver.getY()>bottom_limit)) {
            selectFrame = 4 + this.randomGenerator.nextInt(endFrame-startFrame-2);
            this.simulation.reset(startFrame + selectFrame);
            times_tried++;
            if (times_tried>10) {
                return false;
            }
        }

        // places the platform to "catch" the ghost
        Platform placed_platform = platformFactory();

        // Tests to make sure the dorf does land on the platform (platform
        // might interfere with prior path
        this.simulation.reset(startFrame + selectFrame);
        if (!(this.levelSolver.curPlatform == placed_platform)) {
            this.entities.remove(placed_platform);
            return false;
        }

        // Randomly places the win block if more than 9 platforms exist
        if (this.entities.size()>9) {
            double chance = (this.entities.size()-9)/14.0;
            if (this.randomGenerator.nextDouble()<chance) {
                // Only spawns the door ona normal platform
                if (placed_platform instanceof SolidPlatform) {
                    WinBlock victory_arch = new WinBlock(113, 109,
                            placed_platform.getX() + 5, placed_platform.getY() - 109,
                            this.simulation);
                    this.entities.add(victory_arch);
                    this.levelFinished = true;
                }
            }
        }

        this.simulation.reset(startFrame);
        this.justPlacedPlatform = true;
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
        if (this.difficulty < 3) {
            disappearingPlatformChance = 0.05;
        }
        if (this.difficulty > 9) {
            disappearingPlatformChance = 0.30;
        }

        // Getting variables for where the platform will be constructed
        double wayDown = this.levelSolver.getY()/Model.SCENE_HEIGHT;
        int fudgeFactor = 11 + this.randomGenerator.nextInt(
                          (int) (40 * wayDown));
        double xCoor = this.levelSolver.getX() + this.levelSolver.width
                                               - fudgeFactor;
        double yCoor = this.levelSolver.getY() + this.levelSolver.height;
        Platform platform;


        // First determines if either of the jump boosting platforms should
        // be placed (i.e. the ghost is below a certian part of the level)
        if (wayDown > boostPlatformCuttof) {
            if (bouncyPlatformChance > this.randomGenerator.nextDouble()) {

                // Bases the chance of spawning a trampoline platform on the
                // dorf's velocity
                double trampChance = (this.levelSolver.getY_velocity()/-600)
                                    - .30;

                if (trampChance > this.randomGenerator.nextDouble()) {
                    platform = new TrampolinePlatform(192,32,xCoor-20,yCoor,
                            this.simulation);
                } else {
                    platform = new BouncyPlatform(128,32,xCoor, yCoor,
                            this.simulation);
                }
                this.entities.add(platform);
                return platform;
            }
        }


        // Handles non-height dependant platforms
        if (conveyorPlatformChance > this.randomGenerator.nextDouble()) {
            int numToMake = 1 + this.randomGenerator.nextInt(4);
            platform = new ConveyorPlatform(96, 32, numToMake, xCoor,
                    yCoor, this.simulation);
            this.entities.add(platform);
            return platform;
        }
        if (disappearingPlatformChance > this.randomGenerator.nextDouble()) {
            platform = new FadingPlatform(128,32,xCoor, yCoor, this.simulation,
                    this.simulation.getCurrentFrame());
            this.entities.add(platform);
            return platform;
        }
        platform = new SolidPlatform(128,32,xCoor, yCoor,this.simulation);
        this.entities.add(platform);
        return platform;
    }




    /**
     * This method loops through all of the entities in the entity list and
     * adds their sprites to the root
     */
    public void addSpritesToRoot() {
        for (Entity e: this.entities) {
            this.controller.addSpriteToRoot(e.getSprite());
        }
    }
}