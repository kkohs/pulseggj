package com.ggj.pulse.physics;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.ggj.pulse.entities.BloodVesselEntity;

/**
 * @author Modris Vekmanis
 */
public class GameContactFilter implements ContactFilter {
    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getBody().getUserData() instanceof BloodVesselEntity && fixtureB.getBody().getUserData() instanceof BloodVesselEntity) {
            return ((BloodVesselEntity) fixtureA.getBody().getUserData()).getGrpIndex() == ((BloodVesselEntity) fixtureB.getBody().getUserData()).getGrpIndex();
        }

        if (fixtureA.getBody().getUserData() instanceof BloodVesselEntity && !(fixtureB.getBody().getUserData() instanceof BloodVesselEntity)) {
            return false;
        }
        if (fixtureB.getBody().getUserData() instanceof BloodVesselEntity && !(fixtureA.getBody().getUserData() instanceof BloodVesselEntity)) {
            return false;
        }
        return true;
    }


}
