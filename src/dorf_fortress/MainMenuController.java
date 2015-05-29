package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by azureillusions on 5/27/15.
 */
public class MainMenuController {
    final private double SCENE_WIDTH = 640;
    final private double SCENE_HEIGHT = 480;

    public MainMenuController() {}

    @FXML private ImageView menu_dorf_sprite;
    @FXML private Slider difficultySlider;
    @FXML private ColorPicker beardColorPicker;
    @FXML private javafx.scene.control.TextField nameTextField;

    @FXML
    void whenBeginClicked(ActionEvent actionEvent) {
        Stage thisStage = (Stage) menu_dorf_sprite.getScene().getWindow();
        Main.startGame(thisStage, nameTextField.getText(),
                difficultySlider.getValue(), beardColorPicker.getValue() );
    }

}
