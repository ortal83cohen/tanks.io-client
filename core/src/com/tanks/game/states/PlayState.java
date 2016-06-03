package com.tanks.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tanks.game.TanksDemo;
import com.tanks.game.sprites.Bullet;
import com.tanks.game.sprites.Button;
import com.tanks.game.sprites.GameSprite;
import com.tanks.game.sprites.Tank;

import java.util.ArrayList;

/**
 * Created by Brent on 7/5/2015.
 */
public class PlayState extends State {

    private static final int GROUND_Y_OFFSET = -50;

    private final TextureRegion bgTextureRegion;

    BitmapFont font = new BitmapFont();

    int ANDROID_WIDTH = Gdx.graphics.getWidth();

    int ANDROID_HEIGHT = Gdx.graphics.getHeight();

    private Tank mTank;

    private Button mButton;

    private Texture bg;

    private Vector2 groundPos1, groundPos2;

    ArrayList<Tank> enemies;

    ArrayList<Bullet> bullets;

    public PlayState(com.tanks.game.states.GameStateManager gsm) {
        super(gsm);

        mTank = new Tank(200, 200);
        mButton = new Button((int) cam.position.x - 100,
                (int) cam.position.y - 150);
        enemies = new ArrayList<Tank>();
        bullets = new ArrayList<Bullet>();
        for (int i = 0; i < 20; i++) {
            enemies.add(i, new Tank(100 * i, 100 * i));
        }
        cam.setToOrtho(false, TanksDemo.WIDTH / 2, TanksDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        bgTextureRegion = new TextureRegion(bg);
        bgTextureRegion.setRegion(0, 0, bg.getWidth() * 10, bg.getHeight() * 10);
    }

    @Override
    protected void handleInput() {
        Vector3 touchPos = new Vector3();
        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y,
                    0); //when the screen is touched, the coordinates are inserted into the vector
            cam.unproject(touchPos);

            if (mButton.collides(
                    new com.badlogic.gdx.math.Rectangle(touchPos.x, touchPos.y, 20, 20))) {

             //   shoot(x - ANDROID_WIDTH / 2, -(y - ANDROID_HEIGHT / 2));

            } else {
                mTank.move(x - ANDROID_WIDTH / 2, -(y - ANDROID_HEIGHT / 2));
            }
            if (x % 20 == 0) {
                shoot(x - ANDROID_WIDTH / 2, -(y - ANDROID_HEIGHT / 2));
            }

        }


    }

    @Override
    public void update(float dt) {
        handleInput();

        mTank.update(dt);

        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            enemy.move(enemy.directionX, enemy.directionY);

            enemy.update(dt);
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            if (isOurOfScreen(bullet)) {
                bullets.remove(i);
            } else {
                bullet.update(dt);
                for (int j = 0; j < enemies.size(); j++) {
                    Tank enemy = enemies.get(j);
                    if (bullet.collides(enemy.getBounds())) {
                        enemies.remove(j);
                    }
                }

            }

        }
        cam.position.x = mTank.getPosition().x + mTank.getBounds().height / 2;
        cam.position.y = mTank.getPosition().y + mTank.getBounds().width / 2;

        mButton.setPosition(cam.position.x - 100,
                cam.position.y - 170);
        mButton.update(dt);
        cam.update();

    }

    private boolean isOurOfScreen(GameSprite gameSprite) {
        return cam.position.x - (cam.viewportWidth / 2) > gameSprite.getPosition().x + gameSprite
                .getSprite().getWidth() ||
                cam.position.x + (cam.viewportWidth / 2) < gameSprite.getPosition().x ||
                cam.position.y - (cam.viewportHeight / 2) > gameSprite.getPosition().y + gameSprite
                        .getSprite().getWidth() ||
                cam.position.y + (cam.viewportHeight / 2) < gameSprite.getPosition().y;
    }

    private void shoot(int directionx, int directiony) {
        if (bullets.size() < 5) {
            Bullet bullet = new Bullet((int) mTank.getPosition().x, (int) mTank.getPosition().y,
                    mTank.getRotation(), directionx, directiony);
            bullets.add(bullet);
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bgTextureRegion, 0, 0);
        mTank.getSprite().draw(sb);
        mButton.getSprite().draw(sb);
        sb.setProjectionMatrix(cam.combined); //or your matrix to draw GAME WORLD, not UI

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).getSprite().draw(sb);
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).getSprite().draw(sb);
        }

//        font.draw(sb, String.valueOf(mTank.getSprite().getRotation()), mTank.getPosition().x - 10,
//                mTank.getPosition().y - 10);
//        font.draw(sb, String.valueOf(Gdx.input.getX() - ANDROID_WIDTH / 2), cam.position.x,
//                cam.position.y - 150);
//        font.draw(sb, String.valueOf(Gdx.input.getY() - ANDROID_HEIGHT / 2), cam.position.x,
//                cam.position.y - 165);
        font.draw(sb, "enemies " + enemies.size(), cam.position.x - 35,
                cam.position.y - 175);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        mTank.dispose();
        mButton.dispose();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).dispose();
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).dispose();
        }

    }


}
