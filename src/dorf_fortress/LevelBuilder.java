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

        /* Platforms */

        Platform spawn = new SolidPlatform(128,32,0,
                200,this.model);
        this.entities.add(spawn);
        this.controller.addSpriteToRoot(spawn.getSprite());

        Platform platform1 = new SolidPlatform(128,32,150,
                300,this.model);
        this.entities.add(platform1);
        controller.addSpriteToRoot(platform1.getSprite());

        Platform platform2 = new BouncyPlatform(128,32,300,
                400,this.model);
        this.entities.add(platform2);
        this.controller.addSpriteToRoot(platform2.getSprite());

        Platform platform3 = new SolidPlatform(128,32,450,
                150,this.model);
        this.entities.add(platform3);
        this.controller.addSpriteToRoot(platform3.getSprite());

        Platform platform4 = new SolidPlatform(128,32,650,
                300,this.model);
        this.entities.add(platform4);
        this.controller.addSpriteToRoot(platform4.getSprite());

        Platform platform5 = new SolidPlatform(128,32,800,
                200,this.model);
        this.entities.add(platform5);
        this.controller.addSpriteToRoot(platform5.getSprite());

        Platform platform6 = new BouncyPlatform(128,32,850,
                400,this.model);
        this.entities.add(platform6);
        this.controller.addSpriteToRoot(platform6.getSprite());

        Platform platform7 = new SolidPlatform(128,32,1000,
                150,this.model);
        this.entities.add(platform7);
        this.controller.addSpriteToRoot(platform7.getSprite());


        /* Obstacles */

        Obstacle on_platform1 = new KillBlock(
                32,32,192,256,this.model);
        this.entities.add(on_platform1);
        this.controller.addSpriteToRoot(on_platform1.getSprite());

        Obstacle under_platform3 = new KillBlock(
                32,32,512,256,this.model);
        this.entities.add(under_platform3);
        this.controller.addSpriteToRoot(under_platform3.getSprite());

        Obstacle fireball_0 = new SimpleUpwardsKillBall(
                33,33,425,80,this.model);
        entities.add(fireball_0);
        controller.addSpriteToRoot(fireball_0.getSprite());

        Obstacle fireball_1 = new SimpleUpwardsKillBall(
                33,33,775,100,this.model);
        entities.add(fireball_1);
        controller.addSpriteToRoot(fireball_1.getSprite());

        Obstacle fireball_2 = new SimpleUpwardsKillBall(
                33,33,950,150,this.model);
        entities.add(fireball_2);
        controller.addSpriteToRoot(fireball_2.getSprite());


        /* Goal */

        WinBlock victory_arch = new WinBlock(
                113,109,1000+7,150-109,this.model);
        this.entities.add(victory_arch);
        this.controller.addSpriteToRoot(victory_arch.getSprite());
    }
}