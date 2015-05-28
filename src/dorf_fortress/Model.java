package dorf_fortress;


import java.util.List;
import java.util.ArrayList;
/**
 * Created by jamie on 5/27/15.
 */
public class Model {
    List<Entity> entities;
    Dorf player;
    GameController controller;



    public Model(GameController controller) {
        this.controller = controller;
        entities = new ArrayList<Entity>();
        //Make a Dorf!
        Dorf ferdinand = new Dorf("sprites/BasicDorf.png", 32, 32,
                "Ferdinand",this);
        ferdinand.setX(34);
        ferdinand.setY(100);
        this.player = ferdinand;
        controller.addSpriteToRoot(ferdinand.getSprite());

        //Make a Platform?
        Platform platty = new Platform("sprites/128x32platform.png",128,32,400,
                400,this);
        platty.setX(400);
        platty.setY(400);
        entities.add(platty);
        controller.addSpriteToRoot(platty.getSprite());
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
}

