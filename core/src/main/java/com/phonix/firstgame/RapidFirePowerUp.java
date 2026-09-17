package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;

public class RapidFirePowerUp extends PowerUP{

    public RapidFirePowerUp(float x, float y, float speed, Texture texture) {
        super(x, y, speed, texture);
    }

    @Override
    public void apply(FirstGame game) {
        game.activateRapidFire();
    }
}
