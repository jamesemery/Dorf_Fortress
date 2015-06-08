package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The controller class for the Main Menu.
 */
public class MainMenuController {
    final private double SCENE_WIDTH = 640;
    final private double SCENE_HEIGHT = 480;

    public MainMenuController() {}

    @FXML private CheckBox up_to_11;
    @FXML private ImageView menu_dorf_sprite;
    @FXML private Slider difficultySlider;
    @FXML private ColorPicker beardColorPicker;
    @FXML private javafx.scene.control.TextField nameTextField;

    @FXML
    void initialize() {
        changeMenuSprite();
    }

    /**
     * Begins the game, getting the appropriate values from the slider and
     * color picker.
     */
    @FXML
    void whenBeginClicked(ActionEvent actionEvent) {
        Stage thisStage = (Stage) menu_dorf_sprite.getScene().getWindow();
        if (up_to_11.isSelected()) {
            // In our difficulty settings, "turning it to 11" means 50.
            Main.startGame(thisStage, 50, beardColorPicker.getValue());
        } else {
            Main.startGame(thisStage,
                    difficultySlider.getValue(), beardColorPicker.getValue());
        }
    }

    /**
     * Changes the Dorf sprite on the Main Menu to match the color selected.
     */
    public void changeMenuSprite() {
        Image newImage = new Image("sprites/ColoredDorf.png");
        newImage = Main.colorImage(newImage, beardColorPicker.getValue());
        menu_dorf_sprite.setImage(newImage);
    }
}
