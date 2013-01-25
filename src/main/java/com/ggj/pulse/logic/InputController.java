package com.ggj.pulse.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;

/**
 *
 * Date: 13.26.1
 * Time: 01:08
 * @author Kristaps Kohs
 */
public class InputController extends InputAdapter {
    private final Vector3 pointer = new Vector3();

    private ApplicationContainer applicationContainer;

    public InputController(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Camera camera = (Camera) applicationContainer.get("gameCam");
       this.pointer.set(screenX, screenY, 0);
        camera.unproject(this.pointer);
        Gdx.app.log("Mouse Pointer" , this.pointer.toString());
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return super.touchUp(screenX, screenY, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
