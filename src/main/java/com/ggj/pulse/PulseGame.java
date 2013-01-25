package com.ggj.pulse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.ggj.pulse.graphics.GraphicsController;
import com.ggj.pulse.logic.LogicController;
import com.ggj.pulse.graphics.MainScreen;
import com.ggj.pulse.logic.PhysicsController;
import com.ggj.pulse.utils.AssetManager;
import com.ggj.pulse.utils.EntityFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Lacis
 * Date: 13.25.1
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public class PulseGame extends Game {

    private ApplicationContainer applicationContainer;
    private LogicController logicController;
    private PhysicsController physicsController;
    private GraphicsController graphicsController;
    private EntityFactory entityFactory;

    private AssetManager assetManager;

    @Override
    public void create() {

        applicationContainer = new ApplicationContainer();
        logicController = new LogicController();
        physicsController = new PhysicsController();
        graphicsController = new GraphicsController();
        assetManager = new AssetManager();
        assetManager.initialize();
        entityFactory = new EntityFactory(assetManager);

        logicController.setApplicationContainer(applicationContainer);
        physicsController.setApplicationContainer(applicationContainer);
        graphicsController.setApplicationContainer(applicationContainer);
        physicsController.setEntityFactory(entityFactory);
        logicController.setPhysicsController(physicsController);

        entityFactory.setWorld(physicsController.getWorld());

        physicsController.initialize();
        graphicsController.initialize();

        //MainScreen screen = new MainScreen();
        //screen.setGraphicsController(graphicsController);
        this.setScreen(graphicsController);
    }

    @Override
    public void render() {
        logicController.update();
        super.render();
    }

    public static void main(String[] args) {
        new LwjglApplication(new PulseGame(), "Spēle", 1024, 768, true);
    }
}
