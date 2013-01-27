package com.ggj.pulse.physics;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.BloodVesselEntity;
import com.ggj.pulse.entities.PlayerEntity;
import com.ggj.pulse.utils.AssetManager;

/**
 * @author Modris Vekmanis
 */
public class GameContactListener implements ContactListener {
    private ApplicationContainer applicationContainer;
    private Sound collision;

    public GameContactListener(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
        collision = applicationContainer.getAssetManager().get(AssetManager.CORRUPTION, Sound.class);
    }

    @Override
    public void beginContact(Contact contact) {
        AbstractEntity a = (AbstractEntity) contact.getFixtureA().getBody().getUserData();
        AbstractEntity b = (AbstractEntity) contact.getFixtureB().getBody().getUserData();
        if (a != null && b != null) {
            if (a instanceof PlayerEntity && !(b instanceof BloodVesselEntity) && !(b instanceof PlayerEntity)) {
                ((PlayerEntity) a).setHealth(((PlayerEntity) a).getHealth() - b.getBodySize() * 150);
                applicationContainer.destroyEntity(b);
                if (((PlayerEntity) a).isHasPulse()) collision.play();
            }
            if (b instanceof PlayerEntity && !(a instanceof BloodVesselEntity) && !(a instanceof PlayerEntity)) {
                ((PlayerEntity) b).setHealth(((PlayerEntity) b).getHealth() - b.getBodySize() * 150);
                applicationContainer.destroyEntity(a);
                if (((PlayerEntity) b).isHasPulse()) collision.play();
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
