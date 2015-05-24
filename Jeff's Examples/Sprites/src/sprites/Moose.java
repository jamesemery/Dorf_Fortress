/**
 * Moose.java
 * Jeff Ondich, 10/29/14.
 *
 * A sample subclass of Sprite for CS257.
 */
package sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class Moose extends Sprite {
    private AudioClip audioClip;

    public Moose() {
        Image image = new Image(getClass().getResourceAsStream("/res/moose.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        this.getChildren().add(imageView);

        this.audioClip = new AudioClip(getClass().getResource("/res/moose.wav").toString());
    }

    @Override
    public void makeSound() {
        this.audioClip.play();
    }
}
