package com.ggj.pulse.utils;

import com.badlogic.gdx.math.Vector2;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.ObjEmitter;
import com.ggj.pulse.entities.PlayerEntity;
import com.ggj.pulse.logic.PhysicsController;

/**
 * @author Modris Vekmanis
 */
public class MapLoader {
    private ApplicationContainer applicationContainer;
    private PhysicsController physicsController;
    private EntityFactory entityFactory;

    public void loadLevel() {
        AbstractEntity world = entityFactory.createStaticObject(30, 40, 10, 10, 3, "map");
        AbstractEntity pipes = entityFactory.createStaticObject(30, 40, 10, 10, 3, "pipes");
        applicationContainer.put("gameWorld", world);
        applicationContainer.put("pipes", pipes);
        //createObjEmitters();
        createPlayer();
    }

    public void createPlayer() {
        PlayerEntity entity = entityFactory.createPlayer(40, 40, 10, 10, 0);
        entityFactory.attachRopes(entity);
        applicationContainer.put("player", entity);
        physicsController.getActionEntities().add(entity);
    }




    private void createObjEmitters() {
        ObjEmitter emitter = new ObjEmitter();
        emitter.setPos(new Vector2(0, 0));
        emitter.setDirection(new Vector2(1, 2));
        emitter.setEntityFactory(entityFactory);
        emitter.setApplicationContainer(applicationContainer);
        physicsController.getActionEntities().add(emitter);
    }

    public PhysicsController getPhysicsController() {
        return physicsController;
    }

    public void setPhysicsController(PhysicsController physicsController) {
        this.physicsController = physicsController;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public void setEntityFactory(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }

    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }
}
