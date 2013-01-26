package com.ggj.pulse.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.ActionEntity;
import com.ggj.pulse.utils.EntityFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Modris Vekmanis
 */
public class PhysicsController {
    private ApplicationContainer applicationContainer;
    private World world;
    private EntityFactory entityFactory;
    private List<ActionEntity> actionEntities = new LinkedList<>();

    private float step = (float) (1.0 / 60.f);

    public PhysicsController() {
        world = new World(new Vector2(0, -20), true);
    }

    public void initialize() {
        applicationContainer.put("physicsWorld", world);
        AbstractEntity world = entityFactory.createStaticObject(40, 50, 10, 10, 3, "left");
        applicationContainer.put("gameWorld", world);
        AbstractEntity entity = entityFactory.createPlayer(40, -10, 10, 10, 0);
        applicationContainer.put("player", entity);
    }

    public void update() {
        for (ActionEntity entity : actionEntities) {
            entity.update();
        }
        world.step(step, 10, 10);
    }


    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }

    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public void setEntityFactory(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public List<ActionEntity> getActionEntities() {
        return actionEntities;
    }

    public void setActionEntities(List<ActionEntity> actionEntities) {
        this.actionEntities = actionEntities;
    }
}
