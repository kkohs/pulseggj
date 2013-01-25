package com.ggj.pulse.graphics;

import com.badlogic.gdx.Screen;

/**
 * @author Modris Vekmanis
 */
@Deprecated
public abstract class MainScreen implements Screen {
    private GraphicsController graphicsController;

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

    public GraphicsController getGraphicsController() {
        return graphicsController;
    }

    public void setGraphicsController(GraphicsController graphicsController) {
        this.graphicsController = graphicsController;
    }
}
