package com.tanks.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tanks.game.utils.Type;

/**
 * Created by ortal on 6/15/2016.
 */
public class Player extends Tank {

    public Player(World world, String id, int x, int y) {
        super(world, id, "tank2.png", x, y, Type.PLAYER);
        position = new Vector2(x, y);
        boundsPoly.scale(-0.5f);
        getSprite().scale(-0.5f);
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        directionX = 1;
        directionY = 1;

        speed = 0.1f;
        maxSpeed = 50;
    }

    @Override
    public boolean update(float dt) {
        if (speed > 0) {
            movement = true;
            speed = speed - dt * 20;
        }
        super.update(dt);
//        collisionManager.checkCollision(this);

//        Collisionable collision = collisionManager.checkCollision(this);
//        if (collision != null) {
//            dispose();
//            return false;
//        }
        return true;
    }


    public void move(int x, int y) {
//        body.setLinearVelocity(x, y);
        float SPEED_RATIO = 1;

        // calculte the normalized direction from the body to the touch position
        Vector2 direction = new Vector2(x, y);
        direction.sub(body.getPosition());
        direction.nor();

        float speed = 100;
        body.setLinearVelocity(direction.scl(speed));

//        body.applyLinearImpulse(new Vector2((x-body.getPosition().x)/SPEED_RATIO, (y-body.getPosition().y)/SPEED_RATIO), body.getWorldCenter(), true);

//        if (speed < maxSpeed) {
//            speed = speed + 3f;
//        }
        double length = Math.sqrt((x * x) + (y * y));
        this.directionX = (float) (x / length);
        this.directionY = (float) (y / length);

    }


}
