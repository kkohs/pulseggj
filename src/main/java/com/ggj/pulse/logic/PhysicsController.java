package com.ggj.pulse.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.utils.EntityFactory;

/**
 * @author Modris Vekmanis
 */
public class PhysicsController {
    private ApplicationContainer applicationContainer;
    private World world;
    private EntityFactory entityFactory;

    private float step = 1000 / 60;

    public PhysicsController() {
        world = new World(new Vector2(0, 0), true);
    }

    public void update() {
        world.step(step, 100, 100);
    }


    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }

    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }
}
