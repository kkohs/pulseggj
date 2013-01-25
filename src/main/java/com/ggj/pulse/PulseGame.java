package com.ggj.pulse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Created with IntelliJ IDEA.
 * User: Lacis
 * Date: 13.25.1
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public class PulseGame extends Game {
    @Override
    public void create() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main(String[] args) {
        new LwjglApplication(new PulseGame(), "Title0", 800, 600, true);
    }
}
