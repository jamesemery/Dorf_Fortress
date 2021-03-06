package dorf_fortress;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.PriorityQueue;

/**
 * ObstaclePlacer handles the random generation of obstacles on the map in
 * such a way that they will never impact the Ghosts path to completing the
 * level. It does this by repeatedly simulating the level to completion with
 * the ghost and testing frame-by-frame whether or not the ghost intersects
 * with each obstacles hitbox. Once enough valid obstacles of one type are
 * made it moves on to another type before eventually putting all of the
 * obstacles into the simulation entities.
 */
public class ObstaclePlacer {
    Model simulation;
    Ghost levelSolver;
    Random randomGenerator;
    List<Entity> safeObstacles;
    double difficulty;
    Hitbox safeZone;

    // specific varialbes about the simulation parameters
    double finalX;
    int endFrame;

    /**
     * Creates a level builder object with references to objects it will need
     * for simulation and generation of obstacles.
     *
     * @param simulation
     * @param ghost
     * @param SpawnZone
     */
    public ObstaclePlacer(Model simulation, Ghost ghost, Hitbox SpawnZone) {
        this.simulation = simulation;
        this.levelSolver = ghost;
        this.randomGenerator = new Random();
        this.safeObstacles = new ArrayList<Entity>();
        this.difficulty = simulation.getDifficulty();
        this.safeZone = SpawnZone;
    }

    public void generateObstacles(int n) {
        // sets up the necessary ghost properties
        initializeGhost();

        // Generates the specific types of obstacles
        if (n > 1) {
            obstacleFactoryMethod(n);
        }
        simulation.addEntities(safeObstacles);
        simulation.setGhostMode(false);
    }

    /**
     * Calls determineDifficulty(n) to make a dictionary
     * corresponding to the obstacles that must be placed and
     * their frequency. exist of each given type and then it creates specific
     * instances of each type of obstacle and culls it with cullList() so
     * that none of the obstacles ever intersect with the ghost path.
     * @param n
     */

    private void obstacleFactoryMethod(int n) {
        Dictionary<String,Integer> obstacleOccurrence = determineDifficulty(n);
        Enumeration<String> keys = obstacleOccurrence.keys();

        // For each key in the dictionary it creates value elements and adds
        // them to the safeObstacles list.
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            List<Entity> testCases = new ArrayList<Entity>();
            int numToMake = obstacleOccurrence.get(key);
            int numMade = 0;

            while (numMade < numToMake) {
                // Randomly selects numToMake frames in the simulation and uses a
                // priority queue to ensure they come out in chronological order
                PriorityQueue<Integer> selectFrames = new PriorityQueue<Integer>();
                for (int k = 0; k < (numToMake - numMade); k++) {
                    selectFrames.add(randomGenerator.nextInt(endFrame)+1);
                }
                // Runs through the simulation of the ghost and grbs the ghosts
                // hitbox at the selected frames
                int lastframe = -1;
                while (!selectFrames.isEmpty()) {
                    Hitbox ghost = simulation.getNextGhostHitbox();
                    lastframe = getCorrespondingFrame();
                    while ((!selectFrames.isEmpty())&&(this
                            .getCorrespondingFrame() == selectFrames
                            .peek())) {
                        int current = selectFrames.poll();
                        Entity obstacle = Obstacle.getInstanceFactory(this,
                                levelSolver.getHitbox(), key, randomGenerator);
                        testCases.add(obstacle);

                    }
                }

                // resets the simulation and adds the valid obstacles to the
                // safe list
                simulation.reset();
                List<Entity> culledList = cullList(testCases);
                numMade += culledList.size();
                for (Entity e : culledList) {
                    safeObstacles.add(e);
                }
                testCases.clear();

            }
        }
    }


    /**
     * Takes a number of obstacles ot generate for the level and the known
     * difficulty (from 1-10) and determines a relative spawn prevelance for
     * each obstacle and uses that to determine how many of each obstacle
     * must be built and returns a dictionary pair of a string representing
     * the obstacle, and an int representing a number of obsacles to build.
     *
     * We decided to us a String for the dictionary pair output of this
     * method for readabilities sake. It is easier to tell what is being
     * constructed when reading the code if we use a String.
     *
     * @param n
     */
    private Dictionary<String, Integer> determineDifficulty(int n) {
        double boxChance = 0.15;
        double spinningChance = 0.15;
        double spiderChance = 0.20;
        double ghostChance = 0.20;

        // Adjusts the the occurance of certian obstacles based on the
        // difficulty
        if (difficulty < 3) {
            spinningChance = 0;
            spiderChance = 0.15;
            boxChance = 0.30;

        } else if (this.difficulty < 5) {
            ghostChance = 0;
            boxChance = 0.25;
        } else if (this.difficulty > 9) {
            boxChance = 0.05;
        }

        Dictionary<String,Integer> obstacleOccurrence = new Hashtable<>();
        int boxes = (int)(boxChance*n);
        int spinning = (int)(spinningChance*n);
        int spiders = (int)(spiderChance*n);
        int ghosts = (int)(ghostChance*n);
        int fireballs = n - boxes - spinning - spiders - ghosts;
        obstacleOccurrence.put("disappearingGhost", ghosts);
        obstacleOccurrence.put("oscillatingSpider", spiders);
        obstacleOccurrence.put("spinningHead", spinning);
        obstacleOccurrence.put("box", boxes);
        obstacleOccurrence.put("simpleBall", fireballs);
        return obstacleOccurrence;
    }

    /**
     * Runs through the given list of Obstacles and returns another list that
     * is equal or smaller in size to the first list that corresponds to only
     * the obstacles that did NOT intersect with the ghost. It does this by
     * resetting the simulation and placing the obstacles into the
     * testEntities and then simulating the level to completion, removing any
     * obstacles that intersect with the ghost along the path.
     */
    private List<Entity> cullList(List<Entity> testCases) {
        this.simulation.testingEntities = testCases;
        Hitbox ghost = this.simulation.getNextGhostHitbox();
        while (ghost != null) {
            int i = 0;
            while (i < testCases.size()) {
                Hitbox obstacleHitbox = testCases.get(i).getHitbox();

                // If the obstacle intesects either the safeZone or the
                // ghost, it is removed from the list
                if (ghost.intersects(obstacleHitbox)||
                        (obstacleHitbox.intersects(safeZone))) {
                    testCases.remove(i);
                } else {
                    i++;
                }
            }
            ghost = this.simulation.getNextGhostHitbox();
        }
        this.simulation.testingEntities = null;
        this.simulation.reset();
        return testCases;
    }


    /**
     * Runs through the simulation once without placing obstacles to ensure
     * that the ghost has generated winning variables, then stores the ones
     * necessary for placement
     */
    private void initializeGhost() {
        simulation.reset();
        // Makes sure that the ghost nessicarly has determined the frame and
        // x coordinate that it solves the level at
        while (simulation.getNextGhostHitbox() != null) {
        }
        simulation.reset();
        this.endFrame = levelSolver.frameFinished;
        this.finalX = levelSolver.finalX;
    }

    /*
     * Setters and getters.
     */
    public Model getSimulation() {
        return this.simulation;
    }

    public double getFinalX() {
        return this.finalX;
    }

    public int getCorrespondingFrame() {
        return this.simulation.getCurrentFrame();
    }
}
