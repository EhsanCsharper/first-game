package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class PowerUP {
    protected float x, y, speed;
    protected Texture texture;

    public PowerUP(float x, float y, float speed, Texture texture) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.texture = texture;
    }

    public void update(float delta) {
        y -= speed * delta;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public boolean isOutOfScreen() {
        return y < -texture.getHeight();
    }

    public abstract void apply(FirstGame game);
}
