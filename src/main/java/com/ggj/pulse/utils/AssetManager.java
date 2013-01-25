package com.ggj.pulse.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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
    private static final String TEXTURES = "textures.pack";

    public void initialize() {
        spriteHashMap = new HashMap<String, Sprite>();
        //load("TEDTURENAMVE", Texture.class);
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
}
