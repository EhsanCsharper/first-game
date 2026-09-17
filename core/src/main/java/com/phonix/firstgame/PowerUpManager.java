package com.phonix.firstgame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class PowerUpManager {
    private Array<PowerUP> powerUps;

    public PowerUpManager() {
        this.powerUps = new Array();
    }

    public Array<PowerUP> getPowerUps() {
        return powerUps;
    }

    public void update(float deltaTime) {
        for (PowerUP powerUp : powerUps) {
            powerUp.update(deltaTime);
        }
    }

    public void removeOutOfScreen() {
        Iterator<PowerUP> iterator = powerUps.iterator();
        while (iterator.hasNext()) {
            PowerUP powerUp = iterator.next();
            if (powerUp.isOutOfScreen()) {
                iterator.remove();
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (PowerUP powerUp : powerUps) {
            powerUp.draw(batch);
        }
    }
}
