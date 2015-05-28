package dorf_fortress;


import java.util.List;
import java.util.ArrayList;
/**
 * Created by jamie on 5/27/15.
 */
public class Model {
    private List<Entity> entities;
    private Dorf player;
    private GameController controller;
    public static double SCENE_HEIGHT;



    public Model(GameController controller, double sceneHeight) {
        this.SCENE_HEIGHT = sceneHeight;
        this.controller = controller;
        entities = new ArrayList<Entity>();
        //Make a Dorf!
        Dorf ferdinand = new Dorf("sprites/BrownDorf.png", 32, 32, this, 34,
                100);
        this.player = ferdinand;
        controller.addSpriteToRoot(ferdinand.getSprite());


        /*
         * RANDOM JUMP TESTING BEGINS
         */

        //Make a Platform?
        Platform ground1 = new Platform("sprites/128x32platform.png",128,32,34,
                400,this);
        entities.add(ground1);
        controller.addSpriteToRoot(ground1.getSprite());

        //Make a Platform?
        Platform ground2 = new Platform("sprites/128x32platform.png",128,32,162,
                400,this);
        entities.add(ground2);
        controller.addSpriteToRoot(ground2.getSprite());

        //Make a Platform?
        Platform ground3 = new Platform("sprites/128x32platform.png",128,32,290,
                400,this);
        entities.add(ground3);
        controller.addSpriteToRoot(ground3.getSprite());

        //Make a Platform?
        Platform ground4 = new Platform("sprites/128x32platform.png",128,32,418,
                400,this);
        entities.add(ground4);
        controller.addSpriteToRoot(ground4.getSprite());

        Platform level1 = new Platform("sprites/128x32platform.png",128,32,325,
                300,this);
        entities.add(level1);
        controller.addSpriteToRoot(level1.getSprite());

        Platform level2 = new Platform("sprites/128x32platform.png",128,32,197,
                200,this);
        entities.add(level2);
        controller.addSpriteToRoot(level2.getSprite());

        Platform level3 = new Platform("sprites/128x32platform.png",128,32,69,
                100,this);
        entities.add(level3);
        controller.addSpriteToRoot(level3.getSprite());

        /*
         * RANDOM JUMP TESTING ENDS
         */
    }


    public List<Entity> getObjects() {
        return entities;
    }

    public void simulateFrame() {
        for (Entity i : entities){
            i.updateSprite();
        }
        player.updateSprite();
    }

    // restets the level to the initial conditions for every entity contained
    // in the set
    public void reset() {
        for (Entity i : entities){
            i.reset();
        }
        player.reset();
    }


}

