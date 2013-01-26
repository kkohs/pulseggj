package com.ggj.pulse.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ggj.pulse.utils.AssetManager;

/**
 * @author Modris Vekmanis
 */
public class RectangleEntity extends AbstractEntity {
    private static Vector3 coords = new Vector3();
    private static Sprite sprite = new Sprite();
    private String textName = "textures/New Bitmap Image.bmp";
    private float w, h;

    @Override
    public void render(SpriteBatch batch, Camera camera, AssetManager assetManager, float scaleX, float scaleY) {
        coords.set(getPos().x - w / 2, getPos().y - h / 2, 0);
        camera.project(coords);
        sprite.setTexture(assetManager.get(textName, Texture.class));
        sprite.setSize(w * scaleX, h * scaleY);

        sprite.setU(0);
        sprite.setV(0);
        sprite.setU2(1);
        sprite.setV2(1);

        sprite.setOrigin(w * scaleX / 2, h * scaleY / 2);
        sprite.setRotation((float) Math.toDegrees(getBody().getAngle()));
        sprite.setPosition(coords.x, coords.y);


        sprite.draw(batch);
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }
}
