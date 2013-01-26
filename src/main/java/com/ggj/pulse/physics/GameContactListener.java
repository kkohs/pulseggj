package com.ggj.pulse.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.BloodVesselEntity;
import com.ggj.pulse.entities.PlayerEntity;

/**
 * @author Modris Vekmanis
 */
public class GameContactListener implements ContactListener {
    private ApplicationContainer applicationContainer;

    public GameContactListener(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }

    @Override
    public void beginContact(Contact contact) {
        AbstractEntity a = (AbstractEntity) contact.getFixtureA().getBody().getUserData();
        AbstractEntity b = (AbstractEntity) contact.getFixtureB().getBody().getUserData();
        if (a != null && b != null) {
            if (a instanceof PlayerEntity && !(b instanceof BloodVesselEntity) && !(b instanceof PlayerEntity)) {
                ((PlayerEntity) a).setHealth(((PlayerEntity) a).getHealth() - .1f * b.getBody().getLinearVelocity().len());
                applicationContainer.destroyEntity(b);
            }
            if (b instanceof PlayerEntity && !(a instanceof BloodVesselEntity) && !(a instanceof PlayerEntity)) {
                ((PlayerEntity) b).setHealth(((PlayerEntity) b).getHealth() - .1f * a.getBody().getLinearVelocity().len());
                applicationContainer.destroyEntity(a);

            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
