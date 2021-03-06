package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Acts as the controller for LoseScreen.fxml and WinScreen.fxml. Has a setter
 * method for the model, which it needs in order to pause and unpause the game
 * and to toggle ghostmode.
 */
public class OverlayController {
    private Model model;
    @FXML private AnchorPane primaryPane;

    /**
     * A setter method for the model.
     * @param simulation   The singleton Model running in Main.
     */
    void setModel(Model simulation) {
        this.model = simulation;
    }

    /**
     * The code corresponding to the "Return to Menu" button. Executes
     * loadMainMenu() in Main.
     * @param event   The button press that triggers the method.
     */
    @FXML
    void whenMenuClicked(ActionEvent event) {
        Stage thisStage = (Stage) primaryPane.getScene().getWindow();
        Main.loadMainMenu(thisStage);
    }

    /**
     * The code corresponding to the "Replay Level" and "Try Again" buttons.
     * @param event   The button press that triggers the method.
     */
    @FXML
    void whenReplayClicked(ActionEvent event) {
        Group mainRoot = (Group) primaryPane.getScene().getRoot();
        model.setGhostMode(false); // Never hurts to be safe!
        mainRoot.requestFocus();
        mainRoot.getChildren().remove(primaryPane);
        this.model.unpause();
    }

    /**
     * The code corresponding to the "Next Level" button. Makes a new level with
     * the same Dorf hair color and a slightly higher difficulty.
     */
    @FXML
    void whenNextLevelClicked(ActionEvent event) {
        double difficulty = this.model.getDifficulty();
        if (difficulty < 50) {
            difficulty += 1;
        }
        Stage mainStage = (Stage) primaryPane.getScene().getWindow();
        Color hairColor = this.model.player.hairColor;
        Main.startGame(mainStage, difficulty, hairColor);
    }

    /**
     * The code corresponding to the "See Solution" button. Activates ghostmode,
     * then runs essentially the same code as whenReplayClicked().
     * @param event   The button press that triggers the method.
     */
    @FXML
    void whenSolutionClicked(ActionEvent event) {
        model.setGhostMode(true);
        Group mainRoot = (Group) primaryPane.getScene().getRoot();
        mainRoot.getChildren().remove(primaryPane);
        mainRoot.requestFocus();
        this.model.unpause();

    }

    /**
     * Allows the user to simply press the space bar to replay the level (on
     * a lose screen) or to progress forward (on a win screen).
     * @param event   The key press. If not space, this does nothing.
     */
    public void handleKeyPress(KeyEvent event, boolean winning) {
        if (event.getCode() == KeyCode.SPACE) {
            if (winning) {
                whenNextLevelClicked(new ActionEvent());
                event.consume();
            } else {
                whenReplayClicked(new ActionEvent());
                event.consume();
            }
        }
    }
}
