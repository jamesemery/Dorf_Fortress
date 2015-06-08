package dorf_fortress;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests hitbox collisions or lack thereof of all combos of DorfHitbox and
 * RectangleHitbox.
 */
public class HitboxTest {
    private Hitbox grumpy = new DorfHitbox(32,32);
    private Hitbox sneezy = new DorfHitbox(16,16);
    private Hitbox quad = new RectangleHitbox(32,32);
    private Hitbox rhombus = new RectangleHitbox(16,16);
    
    @Test
    public void testDorfIntersectsRectangle() throws Exception {
        grumpy.setX(100);
        grumpy.setY(100);
        sneezy.setX(200);
        sneezy.setY(200);

        // Dorf and Rectangle have same bounds
        quad.setX(100);
        quad.setY(100);
        assertTrue(grumpy.intersects(quad));

        // Dorf envelops Rectangle
        rhombus.setX(108);
        rhombus.setY(108);
        assertTrue(grumpy.intersects(rhombus));

        // Dorf inside Rectangle
        quad.setX(192);
        quad.setY(192);
        assertTrue(sneezy.intersects(quad));

        // Don't return true while not overlapping.
        assertFalse(grumpy.intersects(quad));
        assertFalse(sneezy.intersects(rhombus));

        // Share an edge
        quad.setX(100);
        quad.setY(132);
        assertTrue(grumpy.intersects(quad));
        quad.setX(132);
        quad.setY(100);
        assertTrue(grumpy.intersects(quad));

        // Share a corner
        rhombus.setX(216);
        rhombus.setY(216);
        assertTrue(sneezy.intersects(rhombus));
        rhombus.setX(184);
        rhombus.setY(216);
        assertTrue(sneezy.intersects(rhombus));
        rhombus.setX(184);
        rhombus.setY(184);
        assertTrue(sneezy.intersects(rhombus));
        rhombus.setX(216);
        rhombus.setY(184);
        assertTrue(sneezy.intersects(rhombus));

        // Are within one of each other
        rhombus.setX(217);
        rhombus.setY(200);
        assertFalse(sneezy.intersects(rhombus));

        // Don't return true while not overlapping
        assertFalse(grumpy.intersects(rhombus));
        assertFalse(sneezy.intersects(quad));
    }

    @Test
    public void testRectangleIntersectsDorf() throws Exception {
        quad.setX(100);
        quad.setY(100);
        rhombus.setX(200);
        rhombus.setY(200);

        // Rectangle and Dorf have same bounds
        grumpy.setX(100);
        grumpy.setY(100);
        assertTrue(quad.intersects(grumpy));

        // Rectangle envelops Dorf
        sneezy.setX(108);
        sneezy.setY(108);
        assertTrue(quad.intersects(sneezy));

        // Rectangle inside Dorf
        grumpy.setX(192);
        grumpy.setY(192);
        assertTrue(rhombus.intersects(grumpy));

        // Don't return true while not overlapping.
        assertFalse(quad.intersects(grumpy));
        assertFalse(rhombus.intersects(sneezy));

        // Share an edge
        grumpy.setX(100);
        grumpy.setY(132);
        assertTrue(quad.intersects(grumpy));
        grumpy.setX(132);
        grumpy.setY(100);
        assertTrue(quad.intersects(grumpy));

        // Share a corner
        sneezy.setX(216);
        sneezy.setY(216);
        assertTrue(rhombus.intersects(sneezy));
        sneezy.setX(184);
        sneezy.setY(216);
        assertTrue(rhombus.intersects(sneezy));
        sneezy.setX(184);
        sneezy.setY(184);
        assertTrue(rhombus.intersects(sneezy));
        sneezy.setX(216);
        sneezy.setY(184);
        assertTrue(rhombus.intersects(sneezy));

        // Are within one of each other
        sneezy.setX(217);
        sneezy.setY(200);
        assertFalse(rhombus.intersects(sneezy));

        // Don't return true while not overlapping
        assertFalse(quad.intersects(sneezy));
        assertFalse(rhombus.intersects(grumpy));
    }

    @Test
    public void testDorfIntersectDorf() throws Exception {
        // Overlapping
        grumpy.setX(100);
        grumpy.setY(100);
        sneezy.setX(108);
        sneezy.setY(108);
        assertTrue(grumpy.intersects(sneezy));
        assertTrue(sneezy.intersects(grumpy));

        // Share an edge
        sneezy.setX(84);
        sneezy.setY(100);
        assertTrue(grumpy.intersects(sneezy));
        assertTrue(sneezy.intersects(grumpy));

        // Share a corner
        sneezy.setX(84);
        sneezy.setY(84);
        assertTrue(grumpy.intersects(sneezy));
        assertTrue(sneezy.intersects(grumpy));

        // Off by one
        sneezy.setX(133);
        sneezy.setY(100);
        assertFalse(grumpy.intersects(sneezy));
        assertFalse(sneezy.intersects(grumpy));

        // All over the place
        sneezy.setX(200);
        sneezy.setY(200);
        assertFalse(grumpy.intersects(sneezy));
        assertFalse(sneezy.intersects(grumpy));
    }

    @Test
    public void testRectangleIntersectRectangle() throws Exception {
        // Overlapping
        quad.setX(100);
        quad.setY(100);
        rhombus.setX(108);
        rhombus.setY(108);
        assertTrue(quad.intersects(rhombus));
        assertTrue(rhombus.intersects(quad));

        // Share an edge
        rhombus.setX(84);
        rhombus.setY(100);
        assertTrue(quad.intersects(rhombus));
        assertTrue(rhombus.intersects(quad));

        // Share a corner
        rhombus.setX(84);
        rhombus.setY(84);
        assertTrue(quad.intersects(rhombus));
        assertTrue(rhombus.intersects(quad));

        // Off by one
        rhombus.setX(133);
        rhombus.setY(100);
        assertFalse(quad.intersects(rhombus));
        assertFalse(rhombus.intersects(quad));

        // All over the place
        rhombus.setX(200);
        rhombus.setY(200);
        assertFalse(quad.intersects(rhombus));
        assertFalse(rhombus.intersects(quad));
    }
}