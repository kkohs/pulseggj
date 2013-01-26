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

import java.util.LinkedList;

/**
 * Date: 13.26.1
 * Time: 12:37
 *
 * @author Kristaps Kohs
 */
public class BloodVesselEntity extends ActionEntity {
    private static Vector3 coords = new Vector3();
    private ApplicationContainer applicationContainer;
    private PlayerEntity parent;
    private float health;
    private float distance;
    private Sprite sprite;
    private float width;
    private float height;
    private boolean render;
    private LinkedList<BloodVesselEntity> chain = new LinkedList<>();
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

    public void setParent(PlayerEntity parent) {
        this.parent = parent;
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

    public int getGrpIndex() {
        return grpIndex;
    }

    public void setGrpIndex(int grpIndex) {
        this.grpIndex = grpIndex;
    }

    public LinkedList<BloodVesselEntity> getChain() {
        return chain;
    }

    public void setChain(LinkedList<BloodVesselEntity> chain) {
        this.chain = chain;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void update() {
        float currentDistance = anchorPoint.dst(parent.getPos());
        if (currentDistance > distance + 15) {
            health -= 0.01 * currentDistance;

        }
        if (health < 0 && chain.isEmpty()) {
            applicationContainer.destroyEntity(this);
            parent.getAnchors().remove(this);
        } else if (health < 0) {
            World world = (World) applicationContainer.get("physicsWorld");
            world.destroyBody(chain.removeFirst().getBody());
        }


    }

    @Override
    public void render(SpriteBatch batch, Camera camera, AssetManager assetManager, float scaleX, float scaleY) {
        if (render) {
            for (BloodVesselEntity e : chain) {

                coords.set(e.getPos().x - width / 2, e.getPos().y - height / 2, 0);
                camera.project(coords);
                sprite.setSize(width * scaleX, height * scaleY);

                sprite.setU(0);
                sprite.setV(0);
                sprite.setU2(1);
                sprite.setV2(1);
                sprite.setRotation((float) Math.toDegrees(e.getBody().getAngle()));
                sprite.setPosition(coords.x, coords.y);
                sprite.setOrigin(width * scaleX , width * scaleY );
                sprite.setSize(width*scaleX*2,height*scaleY*2);
                sprite.draw(batch);
            }
        }
    }
}
