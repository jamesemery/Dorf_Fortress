package dorf_fortress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Created by azureillusions on 5/29/15.
 */
public class WinScreenController {
    private Model model;

    @FXML private AnchorPane primaryPane;
    @FXML private ImageView archwayImage;

    void setModel(Model simulation) {
        this.model = simulation;
    }

    @FXML
    void whenMenuClicked(ActionEvent event) {
        Stage thisStage = (Stage) archwayImage.getScene().getWindow();
        Main.startMenu(thisStage);
    }
    @FXML
    void whenReplayClicked(ActionEvent event) {
        System.out.println("Running whenTryAgainClicked() in LoseScreenController.java");
        Group mainRoot = (Group) primaryPane.getScene().getRoot();
        this.model.unpause();
        System.out.println("unpaused");
        mainRoot.requestFocus();
        mainRoot.getChildren().remove(primaryPane);
    }

}