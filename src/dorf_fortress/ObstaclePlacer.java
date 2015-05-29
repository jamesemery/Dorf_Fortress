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
    List<Obstacle> safeObstacles;

    public ObstaclePlacer(Model simulation, Ghost ghost) {
        this.simulation = simulation;
        this.levelSolver = ghost;
        this.randomGenerator = new Random();
    }

    public void generateObstacles(int n) {
        //simulates the ghosts path once to get necessary variables
        while (simulation.getNextGhostHitbox()!= null) {
        }
        simulation.reset();
        int endFrame = levelSolver.frameFinished;
        double endX = levelSolver.finalX;
        List<Entity> testCases = new ArrayList<Entity>();
        for (int i = 0; i < n; i++) {
            Entity obstacle = getSimpleObsticle(endX);
            testCases.add(obstacle);
        }
        simulation.testingEntities = testCases;
        while (simulation.getNextGhostHitbox()!= null) {
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
        simulation.testingEntities = null;
        simulation.addEntities(testCases);
        simulation.setGhostMode(false);


    }

    public Obstacle getSimpleObsticle(double finalX) {
        int x = randomGenerator.nextInt((int)finalX);
        int speed = randomGenerator.nextInt(80) + 20;
        SimpleUpwardsKillBall jumpy = new SimpleUpwardsKillBall
                ("sprites/BasicDorf.png",32,32,x,speed,simulation);
        return jumpy;
    }
}
