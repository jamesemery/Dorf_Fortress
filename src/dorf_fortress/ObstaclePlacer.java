package dorf_fortress;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Created by jamie on 5/28/15.
 */
public class ObstaclePlacer {
    Model simulation;
    Ghost levelSolver;
    Random randomGenerator;
    List<Entity> safeObstacles;

    public ObstaclePlacer(Model simulation, Ghost ghost) {
        this.simulation = simulation;
        this.levelSolver = ghost;
        this.randomGenerator = new Random();
        this.safeObstacles = new ArrayList<Entity>();
    }

    public void generateObstacles(int n) {
        //simulates the ghosts path once to get necessary variables
        while (simulation.getNextGhostHitbox() != null) {
        }
        simulation.reset();
        int endFrame = levelSolver.frameFinished;
        double endX = levelSolver.finalX;


        while (safeObstacles.size() < n) {
            List<Entity> testCases = new ArrayList<Entity>();
            for (int i = safeObstacles.size(); i < n; i++) {
                Entity obstacle = getSimpleObsticle(endX);
                testCases.add(obstacle);
            }
            simulation.testingEntities = testCases;
            while (simulation.getNextGhostHitbox() != null) {
                Hitbox ghost = simulation.getNextGhostHitbox();
                int i = 0;
                while (i < testCases.size()) {
                    Hitbox obstacleHitbox = testCases.get(i).getHitbox();
                    if (ghost.intersects(obstacleHitbox)) {
                        testCases.remove(i);
                    } else {
                        i++;
                    }
                }
            }
            for (Entity e : testCases) {
                safeObstacles.add(e);
            }
            simulation.testingEntities = null;
            simulation.reset();
        }
        simulation.addEntities(safeObstacles);
        simulation.setGhostMode(false);
        System.out.println(safeObstacles.size());
    }




    public Obstacle getSimpleObsticle(double finalX) {
        int x = randomGenerator.nextInt((int)finalX);
        int speed = randomGenerator.nextInt(80) + 20;
        SimpleUpwardsKillBall jumpy = new SimpleUpwardsKillBall
                ("sprites/BasicDorf.png",32,32,x,speed,simulation);
        return jumpy;
    }
}
