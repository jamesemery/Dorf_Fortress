package dorf_fortress;

import java.util.List;

/**
 * Created by jamie on 5/27/15.
 */
public class LevelBuilder {
    private Model model;
    private List<Entity> entities;
    private GameController controller;

    public LevelBuilder(Model model, List<Entity> entities,
                        GameController controller) {
        this.model = model;
        this.entities = entities;
        this.controller = controller;
    }

    public void makeTestLevel() {
        //Make a Platform?
        SolidPlatform ground1 = new SolidPlatform("sprites/128x32platform.png",128,32,34,
                400,this.model);
        this.entities.add(ground1);
        this.controller.addSpriteToRoot(ground1.getSprite());

        //Make a Platform?
        SolidPlatform ground2 = new SolidPlatform("sprites/128x32platform.png",128,32,162,
                400,this.model);
        this.entities.add(ground2);
        controller.addSpriteToRoot(ground2.getSprite());

        //Make a Platform?
        SolidPlatform ground3 = new SolidPlatform("sprites/128x32platform.png",128,32,290,
                400,this.model);
        this.entities.add(ground3);
        this.controller.addSpriteToRoot(ground3.getSprite());

        //Make a Platform?
        SolidPlatform ground4 = new SolidPlatform("sprites/128x32platform.png",128,32,418,
                400,this.model);
        this.entities.add(ground4);
        this.controller.addSpriteToRoot(ground4.getSprite());

        BouncyPlatform level1 = new BouncyPlatform("sprites/basicPlatform.png",138,32,325,
                300,this.model);
        this.entities.add(level1);
        this.controller.addSpriteToRoot(level1.getSprite());

        SolidPlatform level2 = new SolidPlatform("sprites/128x32platform.png",128,32,197,
                200,this.model);
        this.entities.add(level2);
        this.controller.addSpriteToRoot(level2.getSprite());

        SolidPlatform level3 = new SolidPlatform("sprites/128x32platform.png",128,32,69,
                100,this.model);
        this.entities.add(level3);
        this.controller.addSpriteToRoot(level3.getSprite());

        KillBlock krell = new KillBlock("sprites/staticObstacleTransparent.png",32,32,200,
                288, this.model);
        this.entities.add(krell);
        this.controller.addSpriteToRoot(krell.getSprite());
    }
}