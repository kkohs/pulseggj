package com.ggj.pulse.logic;

import com.ggj.pulse.ApplicationContainer;

/**
 * @author Modris Vekmanis
 */
public class LogicController {
    private ApplicationContainer applicationContainer;
    private PhysicsController physicsController;

    public void update() {
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
}
