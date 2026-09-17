package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;

public class SpaceShipBullet extends Bullet{

    public SpaceShipBullet(float x, float y, Texture texture, float speed) {
        super(x, y, texture, speed);
    }

    @Override
    public void update(float delta) {
        y += speed * delta;
    }
}
