package dorf_fortress;

/**
 * SimpleOpacitySprite is a subclass of SimpleSprite designed to handle sprites
 * with changing opacities.
 */
public class SimpleOpacitySprite extends SimpleSprite {

    /**
     * The constructor passes everything up the line.
     * @param sprite_location   The location of the sprite in the repo.
     * @param entity   The entity to which the sprite corresponds.
     */
    public SimpleOpacitySprite(String sprite_location, Entity entity) {
        super(sprite_location, entity);
    }

    /**
     * Takes the Dorf's absolute x-coordinate and shifts the sprite's relative
     * position on the screen accordingly. Also alters the sprite's opacity
     * according to OpacityChanger.
     * @param dorf_x   The Dorf's x-coordinate.
     */
    @Override
    public void update(double dorf_x) {
        super.update(dorf_x);
        super.getImage().setOpacity(((OpacityChanger) entity).getOpacity());
    }
}
