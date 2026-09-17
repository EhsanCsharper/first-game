package com.phonix.firstgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy {
    protected float x, y;
    protected float speed;
    protected Texture texture;
    protected int hp;
    protected int scoreValue;

    public Enemy(float x, float y, Texture texture, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.texture = texture;
    }

    public void update(float delta) {
        y -= speed * delta;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth(), texture.getHeight());
    }

    public boolean isOutOfScreen() {
        return y < -texture.getHeight();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
    public void takeDamage(int damage) {
        hp -= damage;
    }
    public boolean isDead() {
        return hp <= 0;
    }
    public int getScoreValue() {
        return scoreValue;
    }

    public boolean shouldShoot() {
        return false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
