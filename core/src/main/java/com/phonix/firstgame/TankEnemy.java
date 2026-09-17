package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;

public class TankEnemy extends Enemy {
    float shootTimer;
    float shootInterval;

    public TankEnemy(float x, float y, Texture texture, float speed, float shootInterval) {
        super(x, y, texture, speed);
        hp = 3;
        scoreValue = 50;
        this.shootInterval = shootInterval;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        shootTimer += delta;
    }

    @Override
    public boolean shouldShoot() {
        if (shootTimer >= shootInterval) {
            shootTimer = 0;
            return true;
        }
        return false;
    }
}
