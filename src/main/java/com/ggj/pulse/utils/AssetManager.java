package com.ggj.pulse.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Lacis
 * Date: 13.25.1
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */
public class AssetManager extends com.badlogic.gdx.assets.AssetManager {
    private HashMap<String, Sprite> spriteHashMap;
    private static final String TEXTURES = "textures/textures.pack";
    public static final String CHAIN_TEXTURE = "textures/chain.png";
    public static final String ENEMY_ONE = "textures/enemy2.png";
    public static final String ENEMY_TWO = "textures/enemy1.png";
    public static final String BACK_GROUND = "textures/pulseLevelBackground.png";

    private BodyEditorLoader bodyEditorLoader;

    public void initialize() {
        spriteHashMap = new HashMap<String, Sprite>();
        bodyEditorLoader = new BodyEditorLoader(Gdx.files.internal("map"));

        //load("TEDTURENAMVE", Texture.class);
        this.load(ENEMY_TWO, Texture.class);
        this.load(BACK_GROUND, Texture.class);
        this.load(ENEMY_ONE, Texture.class);
        this.load("textures/pulseHeart.png", Texture.class);
        this.load(CHAIN_TEXTURE, Texture.class);
        this.finishLoading();
    }

    public Sprite getSprite(String name) {
        if (spriteHashMap.containsKey(name)) {
            return spriteHashMap.get(name);
        } else {
            TextureAtlas atlas = get(TEXTURES, TextureAtlas.class);
            spriteHashMap.put(name, atlas.createSprite(name));
            return spriteHashMap.get(name);
        }
    }
    public Vector2 getOrigin(String name) {
       return bodyEditorLoader.getOrigin(name, 200);
    }

    public void attachShape(Body body, FixtureDef fixtureDef, int scale, String name) {
        bodyEditorLoader.attachFixture(body, name, fixtureDef, scale);
    }
}
