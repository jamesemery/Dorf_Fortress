/**
 * Jeff Ondich, 17 Nov 2014
 *
 * The "basics" JavaFX sample for CS 257. This application illustrates a very simple
 * UI with a button and key-press handling.
 *
 * (1) Click the button to reverse the string in the label
 * (2) Type arrow keys or the gaming-traditional W=up, A=left, S=down, D=right to
 *     transform the string in interesting ways.
 *
 * Note that on my Mac keyboard, you have to do Option-Arrow to get the arrow keys
 * to register as key presses. Not sure if that holds for all Macs.
 *
 * Don't just copy this code. Try to understand it. Note that the key handler can
 * be attached to any Node object (Parent, Rectangle, Group, TextField, etc.), but that
 * the Node has to have focus to receive keyboard events. By attaching the key handler
 * to the root Node of our application, we make it relatively easy to route the
 * key events to where they need to go.
 */

package basics;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("basics.fxml"));
        Parent root = (Parent)loader.load();
        final Controller controller = loader.getController();

        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.handleKeyPress(keyEvent);
            }
        });

        primaryStage.setTitle("Basics");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
