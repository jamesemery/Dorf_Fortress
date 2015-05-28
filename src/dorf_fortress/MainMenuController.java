package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * Created by azureillusions on 5/27/15.
 */
public class MainMenuController {

    public MainMenuController() {}

    @FXML
    private ImageView menu_dorf_sprite;

    @FXML
    void whenBeginClicked(ActionEvent actionEvent) {
        System.out.println("Run the damn thing.");
    }

}
