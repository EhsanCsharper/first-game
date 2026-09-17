package com.phonix.firstgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class EnemySpawner {
    float enemySpawnTimer;
    float enemySpawnInterval;
    float tankEnemySpeed;
    float normalEnemySpeed;
    float fastEnemySpeed;
    float tankShootInterval;
    Texture enemyTexture;
    Texture tankTexture;
    Texture tankBulletTexture;
    private Array<Enemy> enemies;
    Array<Bullet> tankBullets;

    public EnemySpawner(Texture enemyTexture, Texture tankTexture, Texture tankBulletTexture) {
        this.enemyTexture = enemyTexture;
        this.tankTexture = tankTexture;
        this.tankBulletTexture = tankBulletTexture;

        enemySpawnTimer = 0;
        enemySpawnInterval = 1.0f;
        tankEnemySpeed = 50f;
        normalEnemySpeed = 200f;
        fastEnemySpeed = 300f;
        tankShootInterval = 2f;
        enemies = new Array<>();
        tankBullets = new Array<>();
    }

    private void spawnEnemy() {
        Enemy enemy;
        int random = MathUtils.random(1, 100);
        if (random <= 20) {
            enemy = new NormalEnemy(
                MathUtils.random(0, 768),
                600,
                enemyTexture,
                normalEnemySpeed
            );
        } else if (random <= 60){
            enemy = new FastEnemy(
                MathUtils.random(0, 768),
                600,
                enemyTexture,
                fastEnemySpeed
            );
        } else {
            enemy = new TankEnemy(
                MathUtils.random(0, 768),
                600,
                tankTexture,
                tankEnemySpeed,
                tankShootInterval
            );
        }
        enemies.add(enemy);
    }

    public void updateEnemies(float score, float delta) {
        enemySpawnTimer += delta;

        if (score < 100) {
            enemySpawnInterval = 1.0f;
            normalEnemySpeed = 200f;
            tankEnemySpeed = 50f;
        }
        else if (score < 300) {
            enemySpawnInterval = 0.8f;
            normalEnemySpeed = 250f;
            tankEnemySpeed = 100f;
        }
        else if (score < 600) {
            enemySpawnInterval = 0.6f;
            normalEnemySpeed = 300f;
            tankEnemySpeed = 140f;
        }
        else {
            enemySpawnInterval = 0.4f;
            normalEnemySpeed = 300f;
            tankEnemySpeed = 140f;
        }
        if (enemySpawnTimer >= enemySpawnInterval) {
            System.out.println(normalEnemySpeed);
            spawnEnemy();
            enemySpawnTimer = 0;
        }

        for (Enemy enemy : enemies) {
            enemy.update(Gdx.graphics.getDeltaTime());
            if (enemy.shouldShoot()) {
                TankBullet tankBullet = new TankBullet(enemy.getX() + 25, enemy.getY() -1, tankBulletTexture);
                tankBullets.add(tankBullet);
            }
        }

        for (Bullet tankBullet : tankBullets) {
            tankBullet.update(Gdx.graphics.getDeltaTime());
        }
    }

    public void removeTankBullets() {
        Iterator<Bullet> it = tankBullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (bullet.isOutOfScreen()) {
                it.remove();
            }
        }
    }

    public void removeEnemies() {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (enemy.isOutOfScreen()) {
                enemyIterator.remove();
            }
        }
    }

    public void reset() {
        enemies.clear();
        tankBullets.clear();
        enemySpawnTimer = 0.0f;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public Array<Bullet> getTankBullets() {
        return tankBullets;
    }
}
