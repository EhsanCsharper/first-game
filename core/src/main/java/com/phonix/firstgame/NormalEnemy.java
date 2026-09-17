package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;

public class NormalEnemy extends Enemy{

    public NormalEnemy(float x, float y, Texture texture, float speed) {
        super(x, y, texture, speed);
        hp = 1;
        scoreValue = 10;
    }
}
