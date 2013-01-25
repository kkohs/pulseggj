package com.ggj.pulse.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ggj.pulse.ApplicationContainer;

/**
 * @author Modris Vekmanis
 */
public class GameScreen extends AbstractScreen {

    private ApplicationContainer applicationContainer;
    private World world;
    private Camera camera;
    private Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen() {
        camera = new PerspectiveCamera(60, 1024, 768);
        box2DDebugRenderer = new Box2DDebugRenderer();
        camera.translate(50, 50, 200);
        camera.lookAt(50, 50, 0);
        camera.near = 200f;
        camera.far = 201f;
    }

    public void initialize() {
        world = (World) applicationContainer.get("world");
        applicationContainer.put("gameCam", camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        camera.update();
        box2DDebugRenderer.render(world, camera.combined);
    }

    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }

    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }
}
