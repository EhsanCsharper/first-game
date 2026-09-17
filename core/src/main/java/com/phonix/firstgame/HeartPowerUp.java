package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;

public class HeartPowerUp extends PowerUP {

    public HeartPowerUp(float x, float y, float speed, Texture texture) {
        super(x, y, speed, texture);
    }

    @Override
    public void apply(FirstGame game) {
        game.addHp(1);
    }
}
