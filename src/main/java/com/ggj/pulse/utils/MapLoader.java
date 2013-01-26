package com.ggj.pulse.utils;

import com.badlogic.gdx.math.Vector2;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.BloodVesselEntity;
import com.ggj.pulse.entities.ObjEmitter;
import com.ggj.pulse.entities.PlayerEntity;
import com.ggj.pulse.graphics.GameScreen;
import com.ggj.pulse.logic.PhysicsController;

/**
 * @author Modris Vekmanis
 */
public class MapLoader {
    private ApplicationContainer applicationContainer;
    private PhysicsController physicsController;
    private EntityFactory entityFactory;
    private GameScreen screen;

    public void loadLevel() {
        AbstractEntity world = entityFactory.createStaticObject(60, 40, 10, 10, 3, "map");
        AbstractEntity pipes = entityFactory.createStaticObject(60, 40, 10, 10, 3, "pipes");
        applicationContainer.put("gameWorld", world);
        applicationContainer.put("pipes", pipes);
        screen.getVisibleEntities().add(world);
       createObjEmitters();
        createPlayer();
    }

    public void createPlayer() {
        PlayerEntity entity = entityFactory.createPlayer(40, 40, 10, 10, 0);
        entityFactory.attachRopes(entity);
        applicationContainer.put("player", entity);
        physicsController.getActionEntities().add(entity);
       // entityFactory.createRope(entity);
        for(BloodVesselEntity vesselEntity : entity.getAnchors()) {
            physicsController.getActionEntities().add(vesselEntity);
        }
    }


    private void createObjEmitters() {
        ObjEmitter emitter = new ObjEmitter();
        emitter.setPos(new Vector2(-75.734055f, 21.004189f));
        emitter.setDirection(new Vector2(10, 11));
        emitter.setEntityFactory(entityFactory);
        emitter.setApplicationContainer(applicationContainer);
        physicsController.getActionEntities().add(emitter);

        emitter = new ObjEmitter();
        emitter.setPos(new Vector2(-59.568245f, 94.135216f));
        emitter.setDirection(new Vector2(21, 9));
        emitter.setEntityFactory(entityFactory);
        emitter.setApplicationContainer(applicationContainer);
        physicsController.getActionEntities().add(emitter);

        emitter = new ObjEmitter();
        emitter.setPos(new Vector2(30.241795f, 123.90083f));
        emitter.setDirection(new Vector2(1f, -5));
        emitter.setEntityFactory(entityFactory);
        emitter.setApplicationContainer(applicationContainer);
        physicsController.getActionEntities().add(emitter);

        emitter = new ObjEmitter();
        emitter.setPos(new Vector2(150.33064f, 90.029625f));
        emitter.setDirection(new Vector2(-9f, -10));
        emitter.setEntityFactory(entityFactory);
        emitter.setApplicationContainer(applicationContainer);
        physicsController.getActionEntities().add(emitter);

        emitter = new ObjEmitter();
        emitter.setPos(new Vector2(181.63586f, 33.834194f));
        emitter.setDirection(new Vector2(-15f, 5));
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

    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }
}
