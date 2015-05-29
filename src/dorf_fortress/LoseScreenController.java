package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by azureillusions on 5/29/15.
 */
public class LoseScreenController {
    @FXML private AnchorPane primaryPane;
    @FXML
    void whenMenuClicked(ActionEvent event) {
        Stage thisStage = (Stage) primaryPane.getScene().getWindow();
        Main.startMenu(thisStage);
    }

    @FXML
    void whenSolutionClicked(ActionEvent event) {
        //This will do something eventually...

    }

    @FXML
    void whenTryAgainClicked(ActionEvent event) {
        Stage thisStage = (Stage) primaryPane.getScene().getWindow();
        // The issue with this bit is that we don't have access to the
        // various paramers startGame wants (i.e. beard color, difficulty,
        // name). Moreover, if we just call startGame, it will (probably?)
        // generate a brand new level.
        // Main.startGame(thisStage);
    }

}
