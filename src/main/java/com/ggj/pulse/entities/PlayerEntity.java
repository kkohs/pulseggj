package com.ggj.pulse.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.ggj.pulse.entities.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 13.26.1
 * Time: 10:45
 *
 * @author Kristaps Kohs
 */
public class PlayerEntity extends ActionEntity {
    private Vector2 target = new Vector2();

    private boolean shouldMove;


    private List<RopeJoint> anchors = new ArrayList<>();

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public boolean isShouldMove() {
        return shouldMove;
    }

    public void setShouldMove(boolean shouldMove) {
        this.shouldMove = shouldMove;
    }

    public List<RopeJoint> getAnchors() {
        return anchors;
    }

    public void addAnchor(RopeJoint ropeJoint) {
        anchors.add(ropeJoint);
    }

    @Override
    public void update() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
