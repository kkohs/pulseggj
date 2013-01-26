package com.ggj.pulse.entities;

import com.badlogic.gdx.math.Vector2;

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

    @Override
    public void update() {
        if(!shouldMove) {
          // getBody().setAwake(false);
        }
    }
}
