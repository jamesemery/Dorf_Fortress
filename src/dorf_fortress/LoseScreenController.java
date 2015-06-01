package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by azureillusions on 5/29/15.
 */
public class LoseScreenController {
    private Model model;

    @FXML private AnchorPane primaryPane;

    void setModel(Model simulation) {
        this.model = simulation;
    }
    @FXML
    void whenMenuClicked(ActionEvent event) {
        Stage thisStage = (Stage) primaryPane.getScene().getWindow();
        Main.startMenu(thisStage);
    }

    @FXML
    void whenSolutionClicked(ActionEvent event) {
        model.setGhostMode(true);

        Stage thisStage = (Stage) primaryPane.getScene().getWindow();
        Main.startGame(thisStage, "TODO: PASS NAME",
                this.model.getDifficulty(), Color.MAGENTA); //TODO: CHANGE COLOR
    }

    @FXML
    void whenTryAgainClicked(ActionEvent event) {
        Stage thisStage = (Stage) primaryPane.getScene().getWindow();
        Main.startGame(thisStage, "TODO: PASS NAME",
                this.model.getDifficulty(), Color.MAGENTA); //TODO: CHANGE COLOR
        // The issue with this bit is that we don't have access to the
        // various parameters startGame wants (i.e. beard color, difficulty,
        // name). Moreover, if we just call startGame, it will (probably?)
        // generate a brand new level.
        // Main.startGame(thisStage);
    }

}
