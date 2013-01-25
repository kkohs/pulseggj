package com.ggj.pulse;

import java.util.HashMap;

/**
 * @author Modris Vekmanis
 */
public class ApplicationContainer {
    private HashMap<String, Object> objects = new HashMap<>();


    public void put(String id, Object object) {
        objects.put(id, object);
    }

    public Object get(String id) {
        return objects.get(id);
    }
}
