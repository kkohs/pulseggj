package com.ggj.pulse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private boolean started;

    @Override
    public void create() {
        System.out.println((boolean)(gameScreen == null));
        if (!started) {
            logger = new FPSLogger();
            applicationContainer = new ApplicationContainer();
            logicController = new LogicController();
            physicsController = new PhysicsController();
            gameScreen = new GameScreen();
            if (assetManager == null) {
                assetManager = new AssetManager();
                assetManager.initialize();
            }
            entityFactory = new EntityFactory(assetManager, applicationContainer, physicsController);
            startBatch = new SpriteBatch();
        }
        if (started) {
            logicController.setApplicationContainer(applicationContainer);
            physicsController.setApplicationContainer(applicationContainer);
            gameScreen.setApplicationContainer(applicationContainer);
            physicsController.setEntityFactory(entityFactory);
            logicController.setPhysicsController(physicsController);
            logicController.setGameScreen(gameScreen);

            applicationContainer.setAssetManager(assetManager);

            inputController = new InputController(applicationContainer);

            entityFactory.setWorld(physicsController.getWorld());
            entityFactory.setGameScreen(gameScreen);
            gameScreen.setAssetManager(assetManager);

            physicsController.initialize();
            gameScreen.initialize();

            physicsController.getWorld().setContactListener(new GameContactListener(applicationContainer));
            physicsController.getWorld().setContactFilter(new GameContactFilter());

            MapLoader mapLoader = new MapLoader();
            mapLoader.setEntityFactory(entityFactory);
            mapLoader.setPhysicsController(physicsController);
            mapLoader.setApplicationContainer(applicationContainer);
            mapLoader.setScreen(gameScreen);
            mapLoader.loadLevel();

            this.setScreen(gameScreen);
        }
    }

    SpriteBatch startBatch;

    @Override
    public void render() {

        if (!started) {
            Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            BitmapFont font = new BitmapFont();
            font.scale(6);
            startBatch.begin();

            font.draw(startBatch, "Press ENTER to start!", Gdx.graphics.getWidth() / 2 - font.getBounds("Press ENTER to start!").width / 2, Gdx.graphics.getHeight() / 2 + font.getBounds("Press ENTER to start!").height);
            startBatch.end();

            Gdx.input.setInputProcessor(new InputAdapter() {
                @Override
                public boolean keyDown(int keycode) {
                    if (keycode == Input.Keys.ENTER) {
                        started = true;
                        create();
                    }
                    return true;
                }
            });

        } else {
            if (applicationContainer.isNewGame()) {
                started = false;
                create();
                return;
            } else {
                logger.log();
                if (applicationContainer.getCurrTime() >= logTime) {    //todo remove
                    Gdx.app.log("Body count: ", String.valueOf(physicsController.getWorld().getBodyCount()));
                    logTime = applicationContainer.getCurrTime() + 1000;
                }
                logicController.update();
            }
            super.render();

        }
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "Pulse";
        configuration.resizable = false;
        configuration.useGL20 = true;
        configuration.width = 1366;
        configuration.height = 768;
        configuration.fullscreen = true;

        new LwjglApplication(new PulseGame(), configuration);
    }
}
