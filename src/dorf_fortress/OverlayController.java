package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by azureillusions on 6/4/15.
 */
public class OverlayController {
    private Model model;

    @FXML private AnchorPane primaryPane;
    @FXML private ImageView archwayImage;

    void setModel(Model simulation) {
        this.model = simulation;
    }

    @FXML
    void whenMenuClicked(ActionEvent event) {
        System.out.println("Running whenMenuClicked() in LoseScreenController.java");
        Stage thisStage = (Stage) primaryPane.getScene().getWindow();
        Main.loadMainMenu(thisStage);
    }

    @FXML
    void whenSolutionClicked(ActionEvent event) {
        System.out.println("Running whenSolutionClicked() in LoseScreenController.java");
        model.setGhostMode(true);
        Group mainRoot = (Group) primaryPane.getScene().getRoot();
        this.model.unpause();
        mainRoot.requestFocus();
        mainRoot.getChildren().remove(primaryPane);
    }

    @FXML
    void whenTryAgainClicked(ActionEvent event) {
        System.out.println("Running whenTryAgainClicked() in LoseScreenController.java");
        Group mainRoot = (Group) primaryPane.getScene().getRoot();
        this.model.unpause();
        System.out.println("unpaused");
        mainRoot.requestFocus();
        mainRoot.getChildren().remove(primaryPane);
    }

    @FXML
    void whenReplayClicked(ActionEvent event) {
        System.out.println("Running whenTryAgainClicked() in LoseScreenController.java");
        Group mainRoot = (Group) primaryPane.getScene().getRoot();
        model.setGhostMode(false);
        this.model.unpause();
        System.out.println("unpaused");
        mainRoot.requestFocus();
        mainRoot.getChildren().remove(primaryPane);
    }

    /*
     * Allows the user to simply press the space bar to replay the level.
     */
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            whenReplayClicked(new ActionEvent()); // Needed to call the method.
            event.consume();
        }
    }
}
