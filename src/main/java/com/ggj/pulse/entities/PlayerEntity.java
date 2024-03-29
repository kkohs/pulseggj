package com.ggj.pulse.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private static Vector3 coords = new Vector3();
    private Vector2 target = new Vector2();
    private boolean render = true;
    private Sprite sprite;
    private boolean shouldMove;
    private int pulse = 25;
    private int pulseTime = 0;
    private int pulseSpeed = 3;
    private boolean hasPulse = true;
    private boolean dead;
    private float health = 100;
    private RopeJoint centerJoint;
    private ApplicationContainer applicationContainer;
    private List<BloodVesselEntity> anchors = new ArrayList<>();

    private Sound beat;

    public PlayerEntity(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
        beat = applicationContainer.getAssetManager().get(AssetManager.HEARTBEAT, Sound.class);
        setRenderOrder(1);
    }

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

    public float getHealth() {
        if (health <= 0) return 0;
        return health;
    }

    public void setHealth(float health) {
        if (this.health <= 0) {
            this.health = 0;
        } else {
            this.health = health;
        }
    }

    @Override
    public void update() {
        pulseTime++;
        setPulseSpeed(Math.min(anchors.size(), (int) health / 25));
        if (getPos().y < -90f) {
            applicationContainer.destroyEntity(this);
        }
        if (health <= 0) {
            dead = true;
            hasPulse = false;
            World world = (World) applicationContainer.get("physicsWorld");
            for (BloodVesselEntity entity : anchors) {
                entity.setHealth(-1);
            }
            if (centerJoint != null) {
                world.destroyJoint(centerJoint);
                getBody().setFixedRotation(false);
                centerJoint = null;
            }
            return;
        }
        if (anchors.isEmpty()) {
            dead = true;
            hasPulse = false;
            World world = (World) applicationContainer.get("physicsWorld");
            if (centerJoint != null) {
                world.destroyJoint(centerJoint);
                getBody().setFixedRotation(false);
                centerJoint = null;
            }
            return;
        }

        if (!shouldMove) {
            if (centerJoint.getMaxLength() > 2) {
                float len = centerJoint.getMaxLength() > 50 ? 50f : centerJoint.getMaxLength();
                centerJoint.setMaxLength(len - .8f);
            }
            for (BloodVesselEntity e : anchors) {
                for (BloodVesselEntity body : e.getChain()) {
                    body.getBody().setLinearVelocity(0, 0);
                }
            }
            getBody().setLinearVelocity(0, 0);
        } else {

            centerJoint.setMaxLength(100);
        }
    }

    @Override
    public void render(SpriteBatch batch, Camera camera, AssetManager assetManager, float scaleX, float scaleY) {
        if (!render) return;
        coords.set(getPos().x - 20, getPos().y - 20, 0);
        camera.project(coords);
        if (hasPulse) {
            sprite.setSize(20 * scaleX * 2 - (pulseTime % (pulse + pulseSpeed * 5)), 20 * scaleY * 2 - (pulseTime % (pulse + pulseSpeed * 5)));
            if ((pulseTime % (pulse + pulseSpeed * 5)) == 0) {
                beat.stop();
                beat.play(1f);
            }
        } else {
            beat.stop();
            sprite.setSize(20 * scaleX * 2, 20 * scaleY * 2);
        }

        sprite.setU(0);
        sprite.setV(0);
        sprite.setU2(1);
        sprite.setV2(1);

        sprite.setOrigin(20 * scaleX, 20 * scaleY);
        sprite.setRotation((float) Math.toDegrees(getBody().getAngle()));
        sprite.setPosition(coords.x, coords.y);

        BitmapFont font = new BitmapFont();
        int y = 100;
        font.draw(batch, "Heart health: " + getHealth() + "\n", 40, y);
        for (BloodVesselEntity entity : anchors) {
            y -= 20;
            font = new BitmapFont();
            if (entity.getHealth() != entity.getLastHealth()) {
                font.setColor(Color.RED);
            }
            font.draw(batch, "Blood vessel health health: " + entity.getHealth() + "\n", 40, y);
        }
        sprite.draw(batch);

    }


    public void setPulseSpeed(int pulseSpeed) {
        this.pulseSpeed = pulseSpeed;
    }

    public void setHasPulse(boolean hasPulse) {
        this.hasPulse = hasPulse;
    }

    public boolean isHasPulse() {
        return hasPulse;
    }
}
