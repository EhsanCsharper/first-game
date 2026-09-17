package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;

public class FastEnemy extends Enemy{

    public FastEnemy(float x, float y, Texture texture, float speed) {
        super(x, y, texture, speed);
        hp = 1;
        scoreValue = 20;
    }
}
