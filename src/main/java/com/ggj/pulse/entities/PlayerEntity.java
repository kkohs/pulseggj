package com.ggj.pulse.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.utils.AssetManager;

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
    private static Vector3 coords = new Vector3();

    private Sprite sprite;
    private boolean shouldMove;

    private boolean dead;

    private RopeJoint centerJoint;

    private ApplicationContainer applicationContainer;


    public PlayerEntity(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }

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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void update() {
        if(anchors.isEmpty())  {
            dead = true;
            World world = (World) applicationContainer.get("physicsWorld");
            if(centerJoint != null){
            world.destroyJoint(centerJoint);
            centerJoint = null;
            }
            return;
        }

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

    @Override
    public void render(SpriteBatch batch, Camera camera, AssetManager assetManager, float scaleX, float scaleY) {
        coords.set(getPos().x - 10, getPos().y - 10, 0);
        camera.project(coords);
        sprite.setSize(10 * scaleX * 2, 10 * scaleY * 2);

        sprite.setU(0);
        sprite.setV(0);
        sprite.setU2(1);
        sprite.setV2(1);

        sprite.setOrigin(10 * scaleX , 10 * scaleY );
        sprite.setRotation((float) Math.toDegrees(getBody().getAngle()));
        sprite.setPosition(coords.x, coords.y);


        sprite.draw(batch);    }
}
