package com.tanks.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by Brent on 7/5/2015.
 */
public class Button extends Entity {

    protected Polygon boundsPoly;

    private Texture texture;

    public Button(int x, int y) {
        super();
        position = new Vector2(x, y);

        texture = new Texture("button.png");

        glowSprite = new Sprite(texture);
        setPolygon();
    }


    public void update(float dt) {
        boundsPoly.setPosition(position.x, position.y);
        glowSprite.setPosition(getPosition().x, getPosition().y);

    }

    public boolean pressed(Polygon polygon) {
        return Intersector.overlapConvexPolygons(boundsPoly, polygon);
    }

    public void setPolygon() {
        boundsPoly = new Polygon(new float[]{
                0, 0, glowSprite.getWidth(), 0, glowSprite.getWidth(), glowSprite.getHeight(), 0,
                glowSprite.getHeight()
        });
        boundsPoly.setOrigin(glowSprite.getWidth() / 2, glowSprite.getHeight() / 2);
    }

    public Polygon getBoundsPolygon() {
        return boundsPoly;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;

    }

    public void dispose() {
        texture.dispose();
    }


}