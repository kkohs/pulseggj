package com.ggj.pulse.physics;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * @author Modris Vekmanis
 */
public class GameContactFilter implements ContactFilter {
    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
        return true;
    }
}
