package com.ggj.pulse.entities;

import com.badlogic.gdx.math.Vector2;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.utils.EntityFactory;

/**
 * @author Modris Vekmanis
 */
public class ObjEmitter extends ActionEntity {
    private ApplicationContainer applicationContainer;
    private EntityFactory entityFactory;
    private Vector2 direction;

    private long nextSpawn = 0;
    private long cdTime = 1000;

    @Override
    public void update() {
        //if(ne)
        entityFactory.createBox(getPos().x, getPos().y, 5, 5, 5);
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

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public long getCdTime() {
        return cdTime;
    }

    public void setCdTime(long cdTime) {
        this.cdTime = cdTime;
    }
}
