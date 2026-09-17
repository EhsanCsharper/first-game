package com.phonix.firstgame;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    float x, y;
    Animation<TextureRegion> animation;
    float stateTime = 0f;

    public Explosion(float x, float y, Animation<TextureRegion> animation) {
        this.x = x;
        this.y = y;
        this.animation = animation;
    }

    public void draw(Batch batch) {
        TextureRegion frame = animation.getKeyFrame(stateTime);
        batch.draw(frame, x, y);
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public boolean isFinished() {
        return animation.isAnimationFinished(stateTime);
    }
}
