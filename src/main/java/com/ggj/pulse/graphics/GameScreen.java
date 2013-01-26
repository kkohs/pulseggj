package com.ggj.pulse.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.utils.AssetManager;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Modris Vekmanis
 */
public class GameScreen extends AbstractScreen {

    private ApplicationContainer applicationContainer;
    private AssetManager assetManager;
    private World world;
    private Camera camera;
    private SpriteBatch batch;
    private Box2DDebugRenderer box2DDebugRenderer;
    private List<AbstractEntity> visibleEntities = new LinkedList<>();

    public GameScreen() {
        camera = new PerspectiveCamera(60, 1600, 900);
        box2DDebugRenderer = new Box2DDebugRenderer();
        camera.translate(50, 50, 200);
        camera.lookAt(50, 50, 0);
        camera.near = 200f;
        camera.far = 201f;
    }

    public void initialize() {
        world = (World) applicationContainer.get("physicsWorld");
        applicationContainer.put("gameCam", camera);
        batch = new SpriteBatch();
    }


    private static Vector3 coords = new Vector3();

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        camera.update();
        box2DDebugRenderer.render(world, camera.combined);

        coords.set(1, 1, 0);
        camera.project(coords);
        float x1 = coords.x;
        float y1 = coords.y;

        coords.set(2, 2, 0);
        camera.project(coords);
        float x2 = coords.x;
        float y2 = coords.y;

        float scaleX = x2 - x1;
        float scaleY = y2 - y1;

        batch.begin();
        for (AbstractEntity entity : visibleEntities) {
            entity.render(batch, camera, assetManager, scaleX, scaleY);

        }

        batch.end();

    }

    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }

    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }

    public List<AbstractEntity> getVisibleEntities() {
        return visibleEntities;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
}
