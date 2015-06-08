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
 * level. The class takes an Integer list of parameters
 * Created by jamie on 5/28/15.
 */
public class ObstaclePlacer {
    private Model simulation;
    private Ghost levelSolver;
    private Random randomGenerator;
    private List<Entity> safeObstacles;
    private double difficulty;

    // specific variables about the simulation parameters
    private double finalX;
    private int endFrame;

    public ObstaclePlacer(Model simulation, Ghost ghost) {
        this.simulation = simulation;
        this.levelSolver = ghost;
        this.randomGenerator = new Random();
        this.safeObstacles = new ArrayList<Entity>();
        this.difficulty = simulation.getDifficulty();
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
     * Creates a dictionary corresponding to how many objects of each given
     * type must exist, and then creates specific instances of each type of
     * obstacle, culling it with cull list.
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
                    selectFrames.add(this.randomGenerator.nextInt(this.endFrame)+1);
                }
                // Runs through the simulation of the ghost and grbs the ghosts
                // hitbox at the selected frames
                int lastframe = -1;
                while (!selectFrames.isEmpty()) {
                    Hitbox ghost = this.simulation.getNextGhostHitbox();
                    lastframe = getCorrespondingFrame();
                    while ((!selectFrames.isEmpty())&&(this
                            .getCorrespondingFrame() == selectFrames
                            .peek())) {
                        int current = selectFrames.poll();
                        Entity obstacle = Obstacle.getInstanceFactory(this,
                                this.levelSolver.getHitbox(), key, this.randomGenerator);
                        testCases.add(obstacle);

                    }
                }

                // resets the simulation and adds the valid obstacles to the
                // safe list
                this.simulation.reset();
                List<Entity> culledList = cullList(testCases);
                numMade += culledList.size();
                for (Entity e : culledList) {
                    this.safeObstacles.add(e);
                }
                testCases.clear();

            }
        }
    }

    /**
     * Takes a number of obstacles to generate for the level and the known
     * difficulty (from 1-10) and determines a relative spawn prevalence for
     * each obstacle. It uses that to determine how many of each obstacle
     * must be built, returning a dictionary pair of a string representing
     * the obstacle, and an int representing a number of obstacles to build.
     *
     * We decided to use Strings for the dictionary pair output of this
     * method for readability's sake – it's slightly less efficient, but it
     * leads to much more legible code in the end.
     *
     * @param n   The number of obstacles to generate.
     */
    private Dictionary<String, Integer> determineDifficulty(int n) {
        double boxChance = 0.15;
        double spinningChance = 0.15;
        double spiderChance = 0.20;
        double ghostChance = 0.20;

        // Adjusts the the occurrence of certain obstacles based on the
        // difficulty
        if (this.difficulty < 3) {
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
     * the obstacles that did NOT intersect with the ghost
     */
    private List<Entity> cullList(List<Entity> testCases) {
        this.simulation.testingEntities = testCases;
        Hitbox ghost = this.simulation.getNextGhostHitbox();
        while (ghost != null) {
            int i = 0;
            while (i < testCases.size()) {
                Hitbox obstacleHitbox = testCases.get(i).getHitbox();
                if (ghost.intersects(obstacleHitbox)) {
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
        this.simulation.reset();
        while (this.simulation.getNextGhostHitbox() != null) {}
        this.simulation.reset();
        this.endFrame = this.levelSolver.frameFinished;
        this.finalX = this.levelSolver.finalX;
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
