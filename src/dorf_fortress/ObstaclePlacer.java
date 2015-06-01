package dorf_fortress;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.PriorityQueue;

/**
 * Created by jamie on 5/28/15.
 */
public class ObstaclePlacer {
    Model simulation;
    Ghost levelSolver;
    Random randomGenerator;
    List<Entity> safeObstacles;

    // specific varialbes about the simulation parameters
    double finalX;
    int endFrame;

    public ObstaclePlacer(Model simulation, Ghost ghost) {
        this.simulation = simulation;
        this.levelSolver = ghost;
        this.randomGenerator = new Random();
        this.safeObstacles = new ArrayList<Entity>();
    }

    public void generateObstacles(int n, List<Integer> params) {
        // sets up the necessary ghost properties
        initializeGhost();

        // Generates the specific types of obstacles
        if (n > 1) {
            obstacleFactoryMehtod(n, params);
        }
        simulation.addEntities(safeObstacles);
        simulation.setGhostMode(false);
    }

    /**
     * Runs creates a dictornary corresponding to to how many objects must
     * exist of each given type and then it creates specific instances of
     * each type of obstacle and culls it with cull list before
     * @param n
     * @param params
     */
    private void obstacleFactoryMehtod(int n, List<Integer> params) {
        Dictionary<String,Integer> obsticleOccurance = parseParams(n, params);
        Enumeration<String> keys = obsticleOccurance.keys();

        // For each key in the dictonary it creates vlaue elements and adds
        // them to the safeObstacles list.
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            List<Entity> testCases = new ArrayList<Entity>();
            int numToMake = obsticleOccurance.get(key);
            int numMade = 0;

            while (numMade < numToMake) {
                System.out.println(key + " numMade: " + numMade);
                // Randomly selects numToMake frames in the simulation and uses a
                // priority queue to ensure they come out in chronological order
                PriorityQueue<Integer> selectFrames = new PriorityQueue<Integer>();
                for (int k = 0; k < (numToMake - numMade); k++) {
                    selectFrames.add(randomGenerator.nextInt(endFrame)+1);
                }
                // Runs through the simulation of the ghost and grabbs the ghosts
                // hitbox at the selected frames
                int lastframe = -1;
                while (!selectFrames.isEmpty()) {
                    Hitbox ghost = simulation.getNextGhostHitbox();
                    lastframe = getCorrespondingFrame();
                    System.out.println(getCorrespondingFrame() + " " +
                            selectFrames.peek());
                    while ((!selectFrames.isEmpty())&&(levelSolver
                            .currentFrameCount == selectFrames.peek ())) {
                        System.out.println(selectFrames.peek());
                        int current = selectFrames.poll();
                        Entity obstacle = Obstacle.getInstanceFactory(this,
                                levelSolver.getHitbox(), key, randomGenerator);
                        testCases.add(obstacle);

                    }
                }
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
     * Takes the params passed to the ObstaclePlacer and turns them into a
     * dictionary corresponding to the obstacle in quesiton and the number of
     * each obstacle it should make based on those parameters
     * @param n
     * @param params
     */
    private Dictionary<String, Integer> parseParams(int n, List<Integer> params) {
        Dictionary<String,Integer> obsticleOccurance = new Hashtable<String,
                Integer>();
        int boxes = (int)(0.8*n);
        System.out.println("Boxes to make: " + boxes);
        obsticleOccurance.put("box", boxes);
        obsticleOccurance.put("simpleBall", n - boxes);
        return obsticleOccurance;
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
        System.out.print("Endframe " + endFrame);
    }

    public Model getSimulation() {
        return simulation;
    }

    public double getFinalX() {
        return finalX;
    }

    public int getCorrespondingFrame() {
        return levelSolver.currentFrameCount;
    }
}
