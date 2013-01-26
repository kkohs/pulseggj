package com.ggj.pulse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.FPSLogger;
import com.ggj.pulse.graphics.GameScreen;
import com.ggj.pulse.logic.InputController;
import com.ggj.pulse.logic.LogicController;
import com.ggj.pulse.logic.PhysicsController;
import com.ggj.pulse.physics.GameContactFilter;
import com.ggj.pulse.physics.GameContactListener;
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

    private FPSLogger logger;
    private AssetManager assetManager;
    private long logTime = 0;

    @Override
    public void create() {
        logger = new FPSLogger();
        applicationContainer = new ApplicationContainer();
        logicController = new LogicController();
        physicsController = new PhysicsController();
        gameScreen = new GameScreen();
        assetManager = new AssetManager();
        assetManager.initialize();
        entityFactory = new EntityFactory(assetManager, applicationContainer);

        logicController.setApplicationContainer(applicationContainer);
        physicsController.setApplicationContainer(applicationContainer);
        gameScreen.setApplicationContainer(applicationContainer);
        physicsController.setEntityFactory(entityFactory);
        logicController.setPhysicsController(physicsController);
        logicController.setGameScreen(gameScreen);

        inputController = new InputController(applicationContainer);

        entityFactory.setWorld(physicsController.getWorld());
        entityFactory.setGameScreen(gameScreen);
        gameScreen.setAssetManager(assetManager);

        physicsController.initialize();
        gameScreen.initialize();

        physicsController.getWorld().setContactListener(new GameContactListener());
        physicsController.getWorld().setContactFilter(new GameContactFilter());

        MapLoader mapLoader = new MapLoader();
        mapLoader.setEntityFactory(entityFactory);
        mapLoader.setPhysicsController(physicsController);
        mapLoader.setApplicationContainer(applicationContainer);
       mapLoader.setScreen(gameScreen);
        mapLoader.loadLevel();

        this.setScreen(gameScreen);
    }

    @Override
    public void render() {
        logger.log();
        if (applicationContainer.getCurrTime() >= logTime) {    //todo remove
            Gdx.app.log("Body count: ", String.valueOf(physicsController.getWorld().getBodyCount()));
            logTime = applicationContainer.getCurrTime() + 1000;
        }
        logicController.update();
        super.render();
    }

    public static void main(String[] args) {
        new LwjglApplication(new PulseGame(), "Spēle", 1600, 900, true);
    }
}
