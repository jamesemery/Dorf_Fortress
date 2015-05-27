package dorf_fortress;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

public class GameController implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = .5;
    private Timer timer;
    public Dorf dorf_obj;
    public InputBuffer inputStore;

    public GameController(Dorf dorfObj) {
        this.dorf_obj = dorfObj;
        this.setUpAnimationTimer();
    }

    public void initialize() {
        this.setUpAnimationTimer();
        inputStore = new InputBuffer();
    }

    private void setUpAnimationTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                        inputStore.reset();
                    }
                });
            }
        };

        final long startTimeInMilliseconds = 0;
        final long repetitionPeriodInMilliseconds = 100;
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer = new java.util.Timer();
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void updateAnimation() {
        //step.

        dorf_obj.updateSprite();
        //We do know that this bit actually works, though; I tried it and
        //just called dorf_obj.right() every step, which worked pretty well.
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            inputStore.addInput("left");
            keyEvent.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            inputStore.addInput("right");
            keyEvent.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            inputStore.addInput("up");
            keyEvent.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            inputStore.addInput("down");
            keyEvent.consume();
        }
    }
}
