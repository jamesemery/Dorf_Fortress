package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * Created by azureillusions on 5/29/15.
 */
public class WinScreenController {
    @FXML private ImageView archwayImage;

    @FXML
    void whenMenuClicked(ActionEvent event) {
        Stage thisStage = (Stage) archwayImage.getScene().getWindow();
        Main.startMenu(thisStage);
    }

}