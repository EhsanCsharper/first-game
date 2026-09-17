package com.phonix.firstgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Iterator;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class FirstGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture spaceshipIdleTexture;
    TextureRegion spaceshipCurrentFrame;
    TextureRegion spaceshipIdleFrame;
    Texture bulletTexture;

    Array<Bullet> bullets;
    float playerSpeed = 100f;
    int score;
    BitmapFont font;
    int hp = 3;
    Rectangle spaceShipRect;
    GameState gameState;


    Texture enemyTexture;
    Texture tankTexture;
    Texture tankBulletTexture;

    float playerX = 100, playerY = 100;
    float spaceShipSpeed = 500;

    Array<Explosion> explosions;
    Texture explosionSheet;
    Animation<TextureRegion> explosionAnimation;
    EnemySpawner enemySpawner;
    Texture heartTexture;
    PowerUpManager powerUpManager;
    float shootTimer;
    float shootInterval;
    boolean rapidFireActive;
    float rapidFireTimer;
    float rapidFireDuration;
    Texture backgroundTexture;
    Texture spaceShipLeftSpriteSheet;
    Texture spaceShipRightSpriteSheet;
    Animation<TextureRegion> spaceShipLeftAnimation;
    Animation<TextureRegion> spaceShipRightAnimation;
    float spaceShipAnimationTime;
    TextureRegion[] leftFrames;
    TextureRegion[] rightFrames;
    int currentLeftFrame = 0;
    int currentRightFrame = 0;
    ShipState shipState;
    Texture moveFlameSheet;
    Animation<TextureRegion> moveFlameAnimation;
    float moveFlameAnimationTime;
    float leftEngineX;
    float leftEngineY;
    float rightEngineX;
    float rightEngineY;


    @Override
    public void create() {
        batch = new SpriteBatch();
        spaceshipIdleTexture = new Texture("spaceship.png");
        spaceshipIdleFrame = new TextureRegion(spaceshipIdleTexture);

        bullets = new Array<>();
        bulletTexture = new Texture("bullet.png");
        enemyTexture = new Texture("enemy.png");
        tankTexture = new Texture("tank.png");
        tankBulletTexture = new Texture("tank-bullet.png");
        font = new BitmapFont();
        spaceShipRect = new Rectangle();
        gameState = GameState.PLAYING;
        explosionSheet = new Texture("explosion-spritesheet.png");
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet, 50, 50);
        TextureRegion[] frames = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            frames[i] = tmp[0][i];
        }
        explosionAnimation = new Animation<>(0.1f, frames);
        explosions = new Array<>();
        enemySpawner = new EnemySpawner(enemyTexture, tankTexture, tankBulletTexture);
        heartTexture = new Texture("heart.png");
        powerUpManager = new PowerUpManager();
        shootTimer = 0;
        shootInterval = 0.3f;
        rapidFireActive = false;
        rapidFireTimer = 0;
        rapidFireDuration = 5f;
        backgroundTexture = new Texture("background.png");
        spaceShipLeftSpriteSheet = new Texture("spaceship-left-spritesheet.png");
        spaceShipRightSpriteSheet = new Texture("spaceship-right-spritesheet.png");
        tmp = TextureRegion.split(spaceShipLeftSpriteSheet, 64, 64);
        leftFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            leftFrames[i] = tmp[0][i];
        }
        spaceShipLeftAnimation = new Animation<>(0.1f, leftFrames);
        tmp = TextureRegion.split(spaceShipRightSpriteSheet, 64, 64);
        rightFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            rightFrames[i] = tmp[0][i];
        }
        spaceShipRightAnimation = new Animation<>(0.1f, rightFrames);
        spaceShipAnimationTime = 0f;
        currentLeftFrame = 0;
        currentRightFrame = 0;
        shipState = ShipState.IDLE;

        moveFlameSheet = new Texture("spritesheet-move-flame.png");
        tmp = TextureRegion.split(moveFlameSheet, 12, 16);
        frames = new TextureRegion[6];

        for (int i = 0; i < 6; i++) {
            frames[i] = tmp[0][i];
        }
        moveFlameAnimation = new Animation<>(0.06f, frames);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            gameState = gameState == GameState.PLAYING ? GameState.PAUSED : GameState.PLAYING;
        }
        if (gameState == GameState.PLAYING) {
            updatePlayer();
            updateBullets();
            enemySpawner.updateEnemies(score, Gdx.graphics.getDeltaTime());
            updateExplosions();
            powerUpManager.update(Gdx.graphics.getDeltaTime());
            removeBullets();
            enemySpawner.removeEnemies();
            enemySpawner.removeTankBullets();
            removeFinishedExplosions();
            powerUpManager.removeOutOfScreen();
            checkCollisions();
            if (rapidFireActive) {
                rapidFireTimer -= Gdx.graphics.getDeltaTime();
                if (rapidFireTimer <= 0) {
                    rapidFireActive = false;
                    shootInterval = 0.3f;
                }
            }
        }
        drawEverything();
    }

    private void removeFinishedExplosions() {
        Iterator<Explosion> explosionIterator = explosions.iterator();
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            if (explosion.isFinished()) {
                explosionIterator.remove();
            }
        }
    }

    private void updateExplosions() {
        for (Explosion explosion : explosions) {
            explosion.update(Gdx.graphics.getDeltaTime());
        }
    }


    private void updatePlayer() {
        moveFlameAnimationTime += Gdx.graphics.getDeltaTime();
        boolean moving = false;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            shipState = ShipState.RIGHT;
            playerX += playerSpeed * Gdx.graphics.getDeltaTime();

            spaceShipAnimationTime += Gdx.graphics.getDeltaTime();
            currentRightFrame = Math.min((int) (spaceShipAnimationTime / 0.1f), 1);
            spaceshipCurrentFrame = rightFrames[currentRightFrame];
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            shipState = ShipState.LEFT;
            playerX -= playerSpeed * Gdx.graphics.getDeltaTime();

            spaceShipAnimationTime += Gdx.graphics.getDeltaTime();
            currentLeftFrame = Math.min((int) (spaceShipAnimationTime / 0.1f), 1);
            spaceshipCurrentFrame = leftFrames[currentLeftFrame];
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerY += playerSpeed * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerY -= playerSpeed * Gdx.graphics.getDeltaTime();
        }

        if (!moving) {
            spaceShipAnimationTime = 0;
            spaceshipCurrentFrame = spaceshipIdleFrame;
            shipState = ShipState.IDLE;
        }

        spaceShipRect.set(playerX, playerY, spaceshipIdleTexture.getWidth(), spaceshipIdleTexture.getHeight());
        updateEnginePositions();
    }

    private void updateBullets() {
        shootTimer += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer > shootInterval) {
            Bullet bullet = new SpaceShipBullet(
                playerX + 28,
                playerY + 52,
                bulletTexture,
                spaceShipSpeed
            );
            bullets.add(bullet);
            shootTimer = 0;
        }
        for (Bullet bullet : bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());
        }
    }


    private void removeBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (bullet.isOutOfScreen()) {
                iterator.remove();
            }
        }
    }

    private void checkCollisions() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Enemy> enemyIterator1 = enemySpawner.getEnemies().iterator();
            while (enemyIterator1.hasNext()) {
                Enemy enemy = enemyIterator1.next();

                if (CollisionUtils.isColliding(bullet.getBounds(), enemy.getBounds())) {
                    enemy.takeDamage(1);
                    bulletIterator.remove();
                    if (enemy.isDead()) {
                        score += enemy.getScoreValue();
                        enemyIterator1.remove();
                        explosions.add(new Explosion(enemy.getX() + 20, enemy.getY() + 20, explosionAnimation));
                        spawnRandomPowerUp(enemy.getX(), enemy.getY());
                    }
                    break;
                }
            }
        }

        Iterator<Enemy> enemyIterator1 = enemySpawner.getEnemies().iterator();
        while (enemyIterator1.hasNext()) {
            Enemy enemy = enemyIterator1.next();
            if (CollisionUtils.isColliding(enemy.getBounds(), spaceShipRect)) {
                enemyIterator1.remove();
                hp = Math.max(0, hp - 1);
            }
        }

        Iterator<Bullet> tankBulletIterator = enemySpawner.getTankBullets().iterator();
        while (tankBulletIterator.hasNext()) {
            Bullet tankBullet = tankBulletIterator.next();
            if (CollisionUtils.isColliding(tankBullet.getBounds(), spaceShipRect)) {
                tankBulletIterator.remove();
                hp = Math.max(0, hp - 1);
            }
        }

        Iterator<PowerUP> powerUPIterator = powerUpManager.getPowerUps().iterator();
        while (powerUPIterator.hasNext()) {
            PowerUP powerUP = powerUPIterator.next();
            if (CollisionUtils.isColliding(powerUP.getBounds(), spaceShipRect)) {
                powerUP.apply(this);
                powerUPIterator.remove();
            }
        }
    }

    private void drawEverything() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(spaceshipCurrentFrame, playerX, playerY);
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }

        for (Enemy enemy : enemySpawner.getEnemies()) {
            enemy.draw(batch);
        }
        font.draw(batch, "Score: " + score, 10, 580);
        font.draw(batch, "HP: " + hp, 750, 580);
        if (hp <= 0) {
            gameState = GameState.GAME_OVER;
        }
        if (gameState == GameState.GAME_OVER) {
            font.draw(batch, "Game Over", 400, 300, 200, 300, true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                reset();
            }
        }
        if (gameState == GameState.PAUSED) {
            font.draw(batch, "paused", 400, 300, 500, 300, true);
        }
        for (Bullet tankBullet : enemySpawner.getTankBullets()) {
            tankBullet.draw(batch);
        }

        for (Explosion explosion : explosions) {
            explosion.draw(batch);
        }
        powerUpManager.draw(batch);

        if (rapidFireActive) {
            font.draw(batch, "Rapid Fire", 700, 550, 100, 50, true);
        }
        drawEngineFlames();
        batch.end();

    }

    private void reset() {
        score = 0;
        hp = 3;
        bullets.clear();
        enemySpawner.reset();
        explosions.clear();
        playerX = 100;
        playerY = 100;
        gameState = GameState.PLAYING;
    }

    @Override
    public void dispose() {
        batch.dispose();
        spaceshipIdleTexture.dispose();
        bulletTexture.dispose();
        enemyTexture.dispose();
        tankTexture.dispose();
        tankBulletTexture.dispose();
        explosionSheet.dispose();
        font.dispose();
        heartTexture.dispose();
        backgroundTexture.dispose();
    }

    public void addHp(int amount) {
        hp += amount;
    }

    public void activateRapidFire() {
        System.out.println("rapid fire activated");
        rapidFireActive = true;
        rapidFireTimer = rapidFireDuration;
        shootInterval = 0.1f;
    }

    public void spawnRandomPowerUp(float x, float y) {
        if (!MathUtils.randomBoolean(0.2f)) return;

        int random = MathUtils.random(1, 100);

        PowerUP powerUP;

        if (random <= 50) {
            powerUP = new HeartPowerUp(x, y, 200, heartTexture);
        } else {
            powerUP = new RapidFirePowerUp(x, y, spaceShipSpeed, bulletTexture);
        }
        powerUpManager.getPowerUps().add(powerUP);
    }

    void drawEngineFlames() {
        TextureRegion flameFrame = moveFlameAnimation.getKeyFrame(moveFlameAnimationTime, true);

        batch.draw(flameFrame, leftEngineX, leftEngineY);
        batch.draw(flameFrame, rightEngineX, rightEngineY);
    }

    void updateEnginePositions() {
        if (shipState.equals(ShipState.IDLE)) {
            leftEngineX = playerX + 14;
            leftEngineY = playerY - 6;

            rightEngineX = playerX + 38;
            rightEngineY = playerY - 6;
        }
        if (shipState.equals(ShipState.LEFT)) {
            leftEngineX = playerX + 22;
            leftEngineY = playerY - 6;

            rightEngineX = playerX + 32;
            rightEngineY = playerY - 6;
        }
        if (shipState.equals(ShipState.RIGHT)) {
            leftEngineX = playerX + 22;
            leftEngineY = playerY - 6;

            rightEngineX = playerX + 32;
            rightEngineY = playerY - 6;
        }
    }
}
