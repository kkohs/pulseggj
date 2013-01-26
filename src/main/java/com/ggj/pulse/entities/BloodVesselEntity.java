package com.ggj.pulse.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.ggj.pulse.ApplicationContainer;

import java.util.LinkedList;

/**
 * Date: 13.26.1
 * Time: 12:37
 *
 * @author Kristaps Kohs
 */
public class BloodVesselEntity extends ActionEntity {

    private ApplicationContainer applicationContainer;
    private PlayerEntity parent;
    private float health;
    private float distance;

    private LinkedList<Body> chain = new LinkedList<>();


    private int grpIndex;
    private Vector2 anchorPoint;

    private RopeJoint joint;


    public BloodVesselEntity(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }

    public RopeJoint getJoint() {
        return joint;
    }

    public void setJoint(RopeJoint joint) {
        this.joint = joint;
    }

    public AbstractEntity getParent() {
        return parent;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Vector2 getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(Vector2 anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    public void setParent(PlayerEntity parent) {
        this.parent = parent;
    }

    public int getGrpIndex() {
        return grpIndex;
    }

    public void setGrpIndex(int grpIndex) {
        this.grpIndex = grpIndex;
    }

    public LinkedList<Body> getChain() {
        return chain;
    }

    public void setChain(LinkedList<Body> chain) {
        this.chain = chain;
    }

    @Override
    public void update() {
        float currentDistance = anchorPoint.dst(parent.getPos());
        if(currentDistance  > distance + 15) {
            health -= 0.001 * currentDistance;

        }
        if(health <0 && chain.isEmpty()) {
            applicationContainer.destroyEntity(this);
            parent.getAnchors().remove(this);
        } else if(health < 0) {
           World world = (World) applicationContainer.get("physicsWorld");
            world.destroyBody(chain.removeFirst());
        }


    }
}
