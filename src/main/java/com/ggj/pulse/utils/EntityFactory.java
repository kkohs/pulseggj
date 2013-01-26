package com.ggj.pulse.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.BloodVesselEntity;
import com.ggj.pulse.entities.PlayerEntity;

/**
 * @author Modris Vekmanis
 */
public class EntityFactory {
    private World world;
    private AssetManager assetManager;
    private ApplicationContainer applicationContainer;

    public EntityFactory(AssetManager assetManager, ApplicationContainer applicationContainer) {
        this.assetManager = assetManager;
        this.applicationContainer = applicationContainer;
    }

    public Body createObject(AbstractEntity entity, Shape shape, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = entity.getPos().x;
        bodyDef.angle = angle;
        bodyDef.position.y = entity.getPos().y;
        bodyDef.fixedRotation = true;
        if (entity.isStatic()) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2;


        Body body = world.createBody(bodyDef);
        //  assetManager.attachShape(body, fixtureDef, 100, "left");
        body.createFixture(fixtureDef);
        body.setAngularDamping(0.5f);
        body.setLinearDamping(0.05f);
        if (shape != null) {
            shape.dispose();
        }
        return body;
    }

    public AbstractEntity createStaticObject(float x, float y, float halfWidth, float halfHeight, float angle, String bodyName) {
        AbstractEntity entity = new AbstractEntity();
        entity.setPos(new Vector2(x, y));

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = entity.getPos().x;
        bodyDef.position.y = entity.getPos().y;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2;

        Body body = world.createBody(bodyDef);
        assetManager.attachShape(body, fixtureDef, 300, bodyName);
        body.setAngularDamping(0.5f);
        //body.setLinearDamping(0.0005f);
        body.setUserData(entity);
        return entity;
    }

    public AbstractEntity createBox(float x, float y, float halfWidth, float halfHeight, float angle) {
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(halfWidth, halfHeight);
        AbstractEntity entity = new AbstractEntity();
        entity.setPos(new Vector2(x, y));
        entity.setBody(createObject(entity, shape, 0));

        return entity;
    }

    public AbstractEntity createCircle(float x, float y, float radius, float angle) {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        AbstractEntity entity = new AbstractEntity();
        entity.setPos(new Vector2(x, y));
        entity.setBody(createObject(entity, shape, 0));

        return entity;
    }

    public PlayerEntity createPlayer(float x, float y, float halfWidth, float halfHeight, float angle) {
        PlayerEntity entity = new PlayerEntity();
        entity.setPos(new Vector2(x, y));
        entity.setStatic(false);
        CircleShape shape = new CircleShape();
        shape.setRadius(4);

        Body body = createObject(entity, shape, 0);
        entity.setBody(body);

        BodyDef centerDef = new BodyDef();
        centerDef.position.set(x, y);
        Body center = world.createBody(centerDef);
        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.maxLength = 0;
        ropeJointDef.bodyA = center;
        ropeJointDef.bodyB = body;

        entity.setCenterJoint((RopeJoint) world.createJoint(ropeJointDef));

        return entity;
    }

    public void attachRopes(PlayerEntity entity) {
        Vector2 pos = new Vector2();
        createRope(entity,new Vector2().set(-70, 48), 1);
        createRope(entity, new Vector2().set(126, -19), 2);
        createRope(entity, new Vector2().set(54, 112), 3);
        createRope(entity, new Vector2().set(68, 97), 4);
        createRope(entity, new Vector2().set(155, 70), 5);


    }

    public void createRope(PlayerEntity entity, Vector2 wallPos, int index) {
        BloodVesselEntity bloodVesselEntity = new BloodVesselEntity(applicationContainer);
        bloodVesselEntity.setAnchorPoint(wallPos);
        bloodVesselEntity.setPos(entity.getPos());
        bloodVesselEntity.setHealth(100f);
        bloodVesselEntity.setParent(entity);
        bloodVesselEntity.setDistance(wallPos.dst(entity.getPos()));
        BodyDef wallPoint = new BodyDef();
        wallPoint.position.set(wallPos);

        Body wallBody = world.createBody(wallPoint);
        float dist = entity.getPos().dst(wallPos);
        float width = 1;
        float height = 1f;

        Vector2 vec = entity.getPos();
        vec.sub(wallPos).nor();
        int count = (int) ((dist / width) / 2);

        Body previous = wallBody;


        for (int i = 1; i < count; i++) {
            BloodVesselEntity e = new BloodVesselEntity(applicationContainer);
            e.setPos(vec.tmp().mul(i * width).add(wallPos));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width, height);
            e.setBody(createObject(e, shape, vec.angle() * MathUtils.degreesToRadians));
            e.getBody().setUserData(e);
            e.setGrpIndex(index);

            RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
            revoluteJointDef.initialize(previous, e.getBody(), previous.getPosition());
            revoluteJointDef.collideConnected = true;
            world.createJoint(revoluteJointDef);
            previous = e.getBody();

            bloodVesselEntity.getChain().add(previous);

        }

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = previous;
        revoluteJointDef.bodyB = entity.getBody();
        world.createJoint(revoluteJointDef);
        entity.addAnchor(bloodVesselEntity);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
