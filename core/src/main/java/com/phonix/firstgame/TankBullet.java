package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;

public class TankBullet extends Bullet {

    public TankBullet(float x, float y, Texture texture) {
        super(x, y, texture, 0);
        speed = 400f;
    }

    @Override
    public void update(float delta) {
        y -= speed * delta;
    }
}
