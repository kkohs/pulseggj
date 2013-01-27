package com.ggj.pulse.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.ActionEntity;
import com.ggj.pulse.utils.EntityFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Modris Vekmanis
 */
public class PhysicsController {
    private ApplicationContainer applicationContainer;
    private World world;
    private EntityFactory entityFactory;
    private List<ActionEntity> actionEntities = new ArrayList<>();

    private float step = (float) (1.0 / 60.f);

    public PhysicsController() {
        world = new World(new Vector2(0, -20), true);
    }

    public void initialize() {
        applicationContainer.put("physicsWorld", world);
        applicationContainer.put("background", this.world.createBody(new BodyDef()));
    }

    public void update() {
        for(AbstractEntity entity : applicationContainer.getEntitiesToDestroy()) {

            if(entity instanceof ActionEntity) {
                actionEntities.remove(entity);
            }
        }
        applicationContainer.getEntitiesToDestroy().clear();
        for (ActionEntity entity : actionEntities) {
            entity.update();
        }
        world.step(Gdx.graphics.getDeltaTime(), 10, 10);
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
