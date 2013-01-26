package com.ggj.pulse.logic;

import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.ActionEntity;
import com.ggj.pulse.entities.BloodVesselEntity;
import com.ggj.pulse.graphics.GameScreen;

/**
 * @author Modris Vekmanis
 */
public class LogicController {
    private ApplicationContainer applicationContainer;
    private GameScreen gameScreen;
    private PhysicsController physicsController;

    public void update() {

        for (AbstractEntity entity : applicationContainer.getEntitiesToDestroy()) {
            if (entity instanceof BloodVesselEntity) {
                physicsController.getWorld().destroyJoint(((BloodVesselEntity) entity).getJoint());
            }
            if (entity instanceof ActionEntity) {
                physicsController.getActionEntities().remove(entity);
            }

            if (entity.isRenderable()) {
                gameScreen.getVisibleEntities().remove(entity);
            }
        }
        applicationContainer.getEntitiesToDestroy().clear();

        applicationContainer.setCurrTime(System.currentTimeMillis());
        physicsController.update();
    }

    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }

    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }

    public PhysicsController getPhysicsController() {
        return physicsController;
    }

    public void setPhysicsController(PhysicsController physicsController) {
        this.physicsController = physicsController;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
