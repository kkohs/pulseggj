package com.ggj.pulse.entities;

import com.badlogic.gdx.math.Vector2;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.utils.EntityFactory;

import java.security.SecureRandom;

/**
 * @author Modris Vekmanis
 */
public class ObjEmitter extends ActionEntity {
    private static SecureRandom random = new SecureRandom();

    private ApplicationContainer applicationContainer;
    private EntityFactory entityFactory;
    private Vector2 direction;

    private long nextSpawn = 0;
    private long maxCdTime = 10000;

    @Override
    public void update() {
        long t = applicationContainer.getCurrTime();
        if (t >= nextSpawn) {
            AbstractEntity entity = entityFactory.createBox(getPos().x, getPos().y, 1, 1, 5);
            Vector2 vel = new Vector2(this.getDirection());
            vel.mul(random.nextFloat() * 10000);
            entity.getBody().applyForceToCenter(vel);
            nextSpawn = t + (long) (random.nextFloat() * (float) maxCdTime);
        }
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

    public long getMaxCdTime() {
        return maxCdTime;
    }

    public void setMaxCdTime(long maxCdTime) {
        this.maxCdTime = maxCdTime;
    }
}
