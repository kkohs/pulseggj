package com.ggj.pulse.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.utils.AssetManager;
import com.ggj.pulse.utils.EntityFactory;

import java.security.SecureRandom;

/**
 * @author Modris Vekmanis
 */
public class ObjEmitter extends ActionEntity {
    private static SecureRandom random = new SecureRandom();
    private static float maxSize = 8f;
    private static float minSize = 6f;
    private static float speedModifier = 1000000;

    private ApplicationContainer applicationContainer;
    private EntityFactory entityFactory;
    private Vector2 direction;
    private Vector2 vT;
    private float maxOffset = 3;

    private long nextSpawn = 0;
    private long maxCdTime = 15000;

    private Sound spawn;

    @Override
    public void update() {
        long t = applicationContainer.getCurrTime();
        if (t >= nextSpawn) {
            if (maxCdTime > 2000) maxCdTime -= 2000;
            float size = 0;
            AbstractEntity entity;
            if (random.nextBoolean()) {
                float dx = random.nextFloat() * (maxSize - minSize) + minSize;
                float dy = random.nextFloat() * (maxSize - minSize) + minSize;
                size = (dx + dy) / 2;
                entity = entityFactory.createBox(getPos().x, getPos().y, dx, dy, random.nextFloat() * 10);
                ((RectangleEntity) entity).setText(random.nextInt(5));
            } else {
                size = random.nextFloat() * (maxSize * 1.5f - minSize) + minSize;
                entity = entityFactory.createCircle(getPos().x, getPos().y, size, random.nextFloat() * 10);
                ((CircleEntity) entity).setText(random.nextInt(3));
            }
            size *= .005;
            entity.setBodySize(size);

            Vector2 vel = new Vector2(vT);
            vel.mul((random.nextFloat() * maxOffset) - maxOffset / 2);
            vel.add(getDirection());
            vel.mul(random.nextFloat() * speedModifier / size);
            entity.getBody().applyForceToCenter(vel);
            nextSpawn = t + (long) (random.nextFloat() * (float) maxCdTime);
            spawn.play(0.25f);
        }
    }

    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }

    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
        spawn = applicationContainer.getAssetManager().get(AssetManager.SPAWN, Sound.class);
        nextSpawn = System.currentTimeMillis() + (long) (random.nextFloat() * (float) maxCdTime);
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
        vT = new Vector2(direction.y, -direction.x);
        this.direction = direction;
    }

    public long getMaxCdTime() {
        return maxCdTime;
    }

    public void setMaxCdTime(long maxCdTime) {
        this.maxCdTime = maxCdTime;
    }
}
