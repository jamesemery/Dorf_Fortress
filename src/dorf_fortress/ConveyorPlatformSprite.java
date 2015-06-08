package dorf_fortress;

/**
 * Sprite for ConvayorPlatform that handles having multiple animated sprites
 * in a row for the display. Functions by being a node that contains several
 * children, all of which are animated sprites.
 * Created by jamie on 6/7/15.
 */
public class ConveyorPlatformSprite extends Sprite {
    private AnimatedSprite[] platforms;
    private double platformWidth;

    /**
     *
     *
     * @param entity
     */
    public ConveyorPlatformSprite(String[] images, int width, int height, int
            numPlatforms, Entity entity, int i) {
        super(entity);
        platforms = new AnimatedSprite[numPlatforms];
        platformWidth = width;
        for (int n = 0; n < numPlatforms; n++) {
            AnimatedSprite platform = new AnimatedSprite(images, width,
                    height, entity, i);
            platforms[n] = platform;
            this.getChildren().add(platform);
        }
    }

    @Override
    public void setX(double x) {
        int offset = 0;
        for (AnimatedSprite s: platforms) {
            s.setX(x+offset);
            offset+=platformWidth;
        }
    }

    @Override
    public void setY(double y) {
        for (AnimatedSprite s: platforms) {
            s.setY(y);
        }
    }
}
