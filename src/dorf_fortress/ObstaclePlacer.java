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
    Model simulation;
    Ghost levelSolver;
    Random randomGenerator;
    List<Entity> safeObstacles;
    double difficulty;

    // specific varialbes about the simulation parameters
    double finalX;
    int endFrame;

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
     * Runs creates a dictornary corresponding to to how many objects must
     * exist of each given type and then it creates specific instances of
     * each type of obstacle and culls it with cull list before
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

        } else if (difficulty < 5) {
            ghostChance = 0;
            boxChance = 0.25;
        }
        if (difficulty < 9) {
            boxChance = 0.05;
        }

        Dictionary<String,Integer> obstacleOccurance = new Hashtable<String,
                Integer>();
        int boxes = (int)(boxChance*n); //0.10
        int spinning = (int)(spinningChance*n); //0.20
        int spiders = (int)(spiderChance*n); //0.20
        int ghosts = (int)(ghostChance*n); //0.20
        int fireballs = n - boxes - spinning - spiders - ghosts;
        obstacleOccurance.put("disappearingGhost", ghosts);
        obstacleOccurance.put("oscillatingSpider", spiders);
        obstacleOccurance.put("spinningHead", spinning);
        obstacleOccurance.put("box", boxes);
        obstacleOccurance.put("simpleBall", fireballs);
        return obstacleOccurance;
    }

    /**
     * Runs through the given list of Obstacles and returns another list that
     * is equal or smaller in size to the first list that corresponds to only
     * the obstacles that did NOT intersect with the ghost
     */
    private List<Entity> cullList(List<Entity> testCases) {
        simulation.testingEntities = testCases;
        Hitbox ghost = simulation.getNextGhostHitbox();
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
            ghost = simulation.getNextGhostHitbox();
        }
        simulation.testingEntities = null;
        simulation.reset();
        return testCases;
    }


    /*
    * Runs through the simulation once without placing obstacles to ensure
    * that the ghost has generated solving variables then stores the ones
    * necessary for placement
    */
    private void initializeGhost() {
        simulation.reset();
        while (simulation.getNextGhostHitbox() != null) {
        }
        simulation.reset();
        this.endFrame = levelSolver.frameFinished;
        this.finalX = levelSolver.finalX;
    }

    public Model getSimulation() {
        return simulation;
    }

    public double getFinalX() {
        return finalX;
    }

    public int getCorrespondingFrame() {
        return simulation.getCurrentFrame();
    }
}
