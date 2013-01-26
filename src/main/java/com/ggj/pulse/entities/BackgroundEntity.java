package com.ggj.pulse.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ggj.pulse.utils.AssetManager;

/**
 * Date: 13.26.1
 * Time: 16:56
 *
 * @author Kristaps Kohs
 */
public class BackgroundEntity extends ActionEntity {
    private static Vector3 coords = new Vector3();
    private String textName = "textures/New Bitmap Image.bmp";
    private float w, h;
    private Sprite sprite;
    private Vector2 origin = new Vector2();

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    private boolean draw;

    public Sprite getTexture() {
        return sprite;
    }

    public void setTexture(Sprite texture) {
        this.sprite = texture;
    }

    @Override
    public void update() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }


    @Override
    public void render(SpriteBatch batch, Camera camera, AssetManager assetManager, float scaleX, float scaleY) {
        if (!draw) return;
        coords.set(getPos().x - w / 2, getPos().y - h / 2, 0);
        camera.project(coords);
        sprite.setSize(1600, 900);

        sprite.setU(0);
        sprite.setV(0);
        sprite.setU2(1);
        sprite.setV2(1);

        //    sprite.setOrigin(coords.x - origin.x,coords.y - origin.y);
        sprite.setRotation((float) Math.toDegrees(getBody().getAngle()));
        sprite.setPosition(0, 0);

        sprite.draw(batch);
    }

    @Override
    public float getBodySize() {
        return 10;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
