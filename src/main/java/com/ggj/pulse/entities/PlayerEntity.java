package com.ggj.pulse.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;

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


    private RopeJoint centerJoint;


    private List<BloodVesselEntity> anchors = new ArrayList<>();

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

    public List<BloodVesselEntity> getAnchors() {
        return anchors;
    }

    public void addAnchor(BloodVesselEntity ropeJoint) {
        anchors.add(ropeJoint);
    }

    public RopeJoint getCenterJoint() {
        return centerJoint;
    }

    public void setCenterJoint(RopeJoint centerJoint) {
        this.centerJoint = centerJoint;
    }

    @Override
    public void update() {
        if(!shouldMove) {
            if(centerJoint.getMaxLength() > 2)
           centerJoint.setMaxLength(centerJoint.getMaxLength() - .8f);
            for(BloodVesselEntity e: anchors) {
                for(BloodVesselEntity body : e.getChain()) {
                    body.getBody().setLinearVelocity(0, 0);
                }
            }
            getBody().setLinearVelocity(0, 0);
        }  else {

            centerJoint.setMaxLength(100);
        }
    }
}
