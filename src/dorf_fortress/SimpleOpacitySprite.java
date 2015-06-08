package dorf_fortress;

/**
 *
 * Created by jamie on 6/6/15.
 */
public class SimpleOpacitySprite extends SimpleSprite {
    public SimpleOpacitySprite(String s, Entity owner) {
        super(s, owner);
    }

    @Override
    public void update(double dorf_x) {
        super.update(dorf_x);
        image.setOpacity(((OpacityChanger)entity).getOpacity());
    }
}
