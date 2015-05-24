package basics;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {
    public Label theLabel;

    public Controller() {
    }

    public void onButtonClick(ActionEvent actionEvent) {
        String text = this.theLabel.getText();
        String reversedText = new StringBuilder(text).reverse().toString();
        this.theLabel.setText(reversedText);
    }

    public void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        String currentText = this.theLabel.getText();
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            this.theLabel.setText(currentText.substring(1) + currentText.charAt(0));
            event.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            int length = currentText.length();
            this.theLabel.setText(currentText.charAt(length - 1) + currentText.substring(0, length - 1));
            event.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            this.theLabel.setText(currentText.toUpperCase());
            event.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            this.theLabel.setText(currentText.toLowerCase());
            event.consume();
        }
    }
}
