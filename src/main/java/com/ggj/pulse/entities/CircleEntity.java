package com.ggj.pulse.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.utils.AssetManager;

/**
 * @author Modris Vekmanis
 */
public class CircleEntity extends ActionEntity {
    private static Vector3 coords = new Vector3();
    private static Sprite sprite = new Sprite();
    private float w, h;

    private ApplicationContainer applicationContainer;


    public CircleEntity(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera, AssetManager assetManager, float scaleX, float scaleY) {
        coords.set(getPos().x - w, getPos().y - h, 0);
        camera.project(coords);
        sprite.setTexture(assetManager.get(AssetManager.ENEMY_ONE, Texture.class));
        sprite.setSize(w * scaleX * 2, h * scaleY * 2);

        sprite.setU(0);
        sprite.setV(0);
        sprite.setU2(1);
        sprite.setV2(1);

        sprite.setOrigin(w * scaleX , h * scaleY );
        sprite.setRotation((float) Math.toDegrees(getBody().getAngle()));
        sprite.setPosition(coords.x, coords.y);


        sprite.draw(batch);
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    @Override
    public void update() {
        if(getPos().y < -90f) {
            applicationContainer.destroyEntity(this);
        }
    }
}
