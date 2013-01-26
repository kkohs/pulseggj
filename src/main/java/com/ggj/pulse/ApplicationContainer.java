package com.ggj.pulse;

import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.ActionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Modris Vekmanis
 */
public class ApplicationContainer {
    private HashMap<String, Object> objects = new HashMap<>();
    private long currTime = 0;

    private List<AbstractEntity> entitiesToDestroy = new ArrayList<>();
    private List<ActionEntity> actionEntitiesToSpawn = new ArrayList<>();

    public void put(String id, Object object) {
        objects.put(id, object);
    }

    public Object get(String id) {
        return objects.get(id);
    }

    public long getCurrTime() {
        return currTime;
    }

    public void setCurrTime(long currTime) {
        this.currTime = currTime;
    }

    public void destroyEntity(AbstractEntity entity) {
        entitiesToDestroy.add(entity);
    }

    public List<AbstractEntity> getEntitiesToDestroy() {
        return entitiesToDestroy;
    }

    public List<ActionEntity> getActionEntitiesToSpawn() {
        return actionEntitiesToSpawn;
    }

    public void addToSpawn(ActionEntity entity) {
        actionEntitiesToSpawn.add(entity);
    }
}
