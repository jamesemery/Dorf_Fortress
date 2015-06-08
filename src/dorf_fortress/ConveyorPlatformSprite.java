package dorf_fortress;

/**
 * Sprite for ConveyorPlatform that handles having multiple animated sprites
 * in a row for the display. It represents a node that contains several
 * children, all of which are animated sprites.
 */
public class ConveyorPlatformSprite extends Sprite {
    private AnimatedSprite[] platforms;
    private double platformWidth;

    /**
     * Constructs a series of identical animated platforms with the images
     * they are given and adds numPlatforms of them to its root for display.
     *
     * @param images   List of consecutive image sprites to cycle through for
     *                 object.
     * @param width   Width of a platform.
     * @param numPlatforms   Number of platforms to repeat.
     * @param entity   The owner object for this sprite.
     * @param animationSpeed   The animation speed.
     */
    public ConveyorPlatformSprite(String[] images, int width,int
            numPlatforms, Entity entity, int animationSpeed) {
        super(entity);
        platforms = new AnimatedSprite[numPlatforms];
        platformWidth = width;
        for (int n = 0; n < numPlatforms; n++) {
            AnimatedSprite platform = new AnimatedSprite(images, entity,
                    animationSpeed);
            platforms[n] = platform;
            this.getChildren().add(platform);
        }
    }

    @Override
    public void update(double dorf_x) {
        for (AnimatedSprite s: platforms) {
            s.update(dorf_x);
        }
        super.update(dorf_x);
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
