package com.ggj.pulse.logic;

import com.badlogic.gdx.Screen;
import com.ggj.pulse.graphics.GraphicsController;

/**
 * @author Modris Vekmanis
 */
public class MainScreen implements Screen {
    private GraphicsController graphicsController;

    @Override
    public void render(float delta) {
        int a=0;
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
