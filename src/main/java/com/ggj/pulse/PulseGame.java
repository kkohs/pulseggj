package com.ggj.pulse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.ggj.pulse.graphics.GameScreen;
import com.ggj.pulse.logic.InputController;
import com.ggj.pulse.logic.LogicController;
import com.ggj.pulse.logic.PhysicsController;
import com.ggj.pulse.utils.AssetManager;
import com.ggj.pulse.utils.EntityFactory;
import com.ggj.pulse.utils.MapLoader;

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
    private GameScreen gameScreen;
    private EntityFactory entityFactory;
    private InputController inputController;

    private AssetManager assetManager;

    @Override
    public void create() {

        applicationContainer = new ApplicationContainer();
        logicController = new LogicController();
        physicsController = new PhysicsController();
        gameScreen = new GameScreen();
        assetManager = new AssetManager();
        assetManager.initialize();
        entityFactory = new EntityFactory(assetManager);

        logicController.setApplicationContainer(applicationContainer);
        physicsController.setApplicationContainer(applicationContainer);
        gameScreen.setApplicationContainer(applicationContainer);
        physicsController.setEntityFactory(entityFactory);
        logicController.setPhysicsController(physicsController);

        inputController = new InputController(applicationContainer);

        entityFactory.setWorld(physicsController.getWorld());

        physicsController.initialize();
        gameScreen.initialize();

        MapLoader mapLoader = new MapLoader();
        mapLoader.setEntityFactory(entityFactory);
        mapLoader.setPhysicsController(physicsController);
        mapLoader.setApplicationContainer(applicationContainer);
        mapLoader.loadLevel();

        this.setScreen(gameScreen);
    }

    @Override
    public void render() {
        logicController.update();
        super.render();
    }

    public static void main(String[] args) {
        new LwjglApplication(new PulseGame(), "Spēle", 1600, 900, true);
    }
}
