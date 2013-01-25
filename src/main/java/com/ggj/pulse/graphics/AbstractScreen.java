package com.ggj.pulse.graphics;

import com.badlogic.gdx.Screen;

/**
 * @author Modris Vekmanis
 */
public abstract class AbstractScreen implements Screen {
    private GameScreen gameScreen;

    @Override
    public void render(float delta) {
        //graphicsController.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
