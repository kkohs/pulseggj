package com.ggj.pulse.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ggj.pulse.ApplicationContainer;

/**
 * @author Modris Vekmanis
 */
public class GraphicsController {

    private ApplicationContainer applicationContainer;
    private World world;
    private Camera camera;
    private Box2DDebugRenderer box2DDebugRenderer;

    public GraphicsController() {
        camera = new PerspectiveCamera(60, 800, 600);
        box2DDebugRenderer = new Box2DDebugRenderer();
        camera.translate(50, 50, 100);
        camera.lookAt(50, 50, 0);
        camera.near = 1f;
        camera.far = 101f;
    }

    public void initialize() {
        world = (World) applicationContainer.get("world");
    }

    public void render() {
        camera.update();
        box2DDebugRenderer.render(world, camera.projection);
    }

    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }

    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }
}
