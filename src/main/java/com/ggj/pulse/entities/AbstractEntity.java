package com.ggj.pulse.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created with IntelliJ IDEA.
 * User: Lacis
 * Date: 13.25.1
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public class AbstractEntity {
    private Vector2 pos;
    private boolean isStatic = false;
    private boolean renderable = false;
    private Body body;

    public void render() {

    }

    public Vector2 getPos() {
        if (body != null && pos != null) {
            pos.set(body.getPosition());
        }
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public boolean isRenderable() {
        return renderable;
    }

    public void setRenderable(boolean renderable) {
        this.renderable = renderable;
    }
}
