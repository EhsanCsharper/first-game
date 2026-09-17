package com.phonix.firstgame;

import com.badlogic.gdx.math.Rectangle;

public class CollisionUtils {
    public static boolean isColliding(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }
}
