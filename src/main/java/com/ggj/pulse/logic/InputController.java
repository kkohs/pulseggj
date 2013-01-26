package com.ggj.pulse.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.PlayerEntity;

/**
 *
 * Date: 13.26.1
 * Time: 01:08
 * @author Kristaps Kohs
 */
public class InputController extends InputAdapter {
    private final Vector3 pointer = new Vector3();
    private MouseJoint mouseJoint;

    private ApplicationContainer applicationContainer;

    public InputController(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Camera camera = (Camera) applicationContainer.get("gameCam");
        World world = (World) applicationContainer.get("physicsWorld");
       this.pointer.set(screenX, screenY, 0);
        camera.unproject(this.pointer);
        Gdx.app.log("Mouse Pointer" , this.pointer.toString());
        PlayerEntity playerEntity = (PlayerEntity) applicationContainer.get("player");

        for(RopeJoint ropeJoint : playerEntity.getAnchors()) {
            ropeJoint.setMaxLength(100);
        }

        this.pointer.set(screenX, screenY,0);
        camera.unproject(this.pointer);

        MouseJointDef def = new MouseJointDef();
        def.bodyA = (Body) applicationContainer.get("background");
        def.bodyB = playerEntity.getBody();
        def.collideConnected = true;
        def.target.set(playerEntity.getPos());
        def.maxForce = 1000.0f * playerEntity.getBody().getMass();

        mouseJoint = (MouseJoint)world.createJoint(def);
        playerEntity.getBody().setAwake(true);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Camera camera = (Camera) applicationContainer.get("gameCam");
        if (mouseJoint != null) {
            camera.unproject(this.pointer.set(screenX, screenY, 0));
            mouseJoint.setTarget(new Vector2().set(this.pointer.x,this.pointer.y));
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        World world = (World) applicationContainer.get("physicsWorld");
        PlayerEntity playerEntity = (PlayerEntity) applicationContainer.get("player");

        if (mouseJoint != null) {
            world.destroyJoint(mouseJoint);
            mouseJoint = null;
            for(RopeJoint ropeJoint : playerEntity.getAnchors()) {
                ropeJoint.setMaxLength(2);
            }
        }
        return super.touchUp(screenX, screenY, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
