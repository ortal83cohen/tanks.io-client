package com.tanks.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Brent on 7/5/2015.
 */
public class Button extends GameSprite {

    private Texture texture;

    public Button(int x, int y) {
        position = new Vector2(x, y);

        texture = new Texture("button.png");

        glowSprite = new Sprite(texture);
        setPolygon();
    }


    public void update(float dt) {

        boundsPoly.setPosition(position.x, position.y);
        glowSprite.setPosition(getPosition().x, getPosition().y);

    }


    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;

    }

    public void dispose() {
        texture.dispose();
    }

}