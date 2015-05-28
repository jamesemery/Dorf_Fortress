package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by azureillusions on 5/27/15.
 */
public class MainMenuController {
    final private double SCENE_WIDTH = 640;
    final private double SCENE_HEIGHT = 480;

    public MainMenuController() {}

    @FXML
    private ImageView menu_dorf_sprite;

    @FXML
    void whenBeginClicked(ActionEvent actionEvent) {
        System.out.println("Run the damn thing.");
        Stage eventSource = (Stage) menu_dorf_sprite.getScene().getWindow();

        Group root = new Group();
        //Make a tiled background.
        BackgroundImage myBI = new BackgroundImage(
                new Image("sprites/BasicTile.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Region platformerBasicsRegion = new Region();
        platformerBasicsRegion.setPrefSize(SCENE_WIDTH+20, SCENE_HEIGHT+20);
        platformerBasicsRegion.setBackground(new Background(myBI));
        root.getChildren().add(platformerBasicsRegion);

        //Make a Dorf!
//        Dorf ferdinand = new Dorf("sprites/BasicDorf.png", 32, 32, "Ferdinand", this);
//        ferdinand.setX(34);
//        ferdinand.setY(100);
//        root.getChildren().add(ferdinand.getSprite());

        //Make a Platform?
//        Platform platty = new Platform("sprites/BasicDorf.png",32,32,400,400, this);
//        platty.setX(400);
//        platty.setY(400);
//        root.getChildren().add(platty.getSprite());

        //Set up the controller. (Hopefully)
//        GameController controller = new GameController(this);
//        controller.initialize();
//
//        // Set up a KeyEvent handler so we can respond to keyboard activity.
//        root.setOnKeyPressed(controller);
//        root.requestFocus();


        eventSource.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
    }

}
