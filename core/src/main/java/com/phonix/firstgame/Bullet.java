package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Bullet {
    protected float x, y;
    protected float speed = 500;

    protected Texture texture;

    public Bullet(float x, float y, Texture texture, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.texture = texture;
    }


    public abstract void update(float delta);

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean isOutOfScreen() {
        return y > 600;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

}
