package com.ggj.pulse.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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


    public Body createObject(AbstractEntity entity, Shape shape) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = entity.getPos().x;
        bodyDef.position.y = entity.getPos().y;

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
        entity.setBody(createObject(entity, shape));

        return entity;
    }

    public AbstractEntity createCircle(float x, float y, float radius, float angle) {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        AbstractEntity entity = new AbstractEntity();
        entity.setPos(new Vector2(x, y));
        entity.setBody(createObject(entity, shape));

        return entity;
    }

    public PlayerEntity createPlayer(float x, float y, float halfWidth, float halfHeight, float angle) {
        PlayerEntity entity = new PlayerEntity();
        entity.setPos(new Vector2(x, y));
        entity.setStatic(false);
        CircleShape shape = new CircleShape();
        shape.setRadius(4);

        Body body = createObject(entity, shape);
        entity.setBody(body);

        BodyDef centerDef = new BodyDef();
        centerDef.position.set(x,y);
       Body center =   world.createBody(centerDef);
        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.maxLength = 0;
        ropeJointDef.bodyA = center;
        ropeJointDef.bodyB = body;

        entity.setCenterJoint((RopeJoint) world.createJoint(ropeJointDef));

        return entity;
    }

    public void attachRopes(PlayerEntity entity) {
        BloodVesselEntity vesselEntity = new BloodVesselEntity(applicationContainer);
        BodyDef vesselBodyDef1 = new BodyDef();
        vesselBodyDef1.position.set(-70,48);
        Body vesselBody1 = world.createBody(vesselBodyDef1);
        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyB = entity.getBody();
        ropeJointDef.bodyA = vesselBody1;
        ropeJointDef.maxLength = 150f;
        RopeJoint ropeJoint = (RopeJoint)  world.createJoint(ropeJointDef);
        vesselEntity.setBody(vesselBody1);
        vesselEntity.setParent(entity);
        vesselEntity.setDistance(entity.getPos().dst(vesselBody1.getPosition()));
        vesselEntity.setHealth(100f);
        vesselEntity.setJoint(ropeJoint);
        vesselEntity.setAnchorPoint(vesselBody1.getPosition());



        BloodVesselEntity vesselEntity2 = new BloodVesselEntity(applicationContainer);
        BodyDef vesselBodyDef2 = new BodyDef();
        vesselBodyDef1.position.set(154, 71);
        Body vesselBody2 = world.createBody(vesselBodyDef2);
        RopeJointDef ropeJointDef2 = new RopeJointDef();
        ropeJointDef2.bodyB = entity.getBody();
        ropeJointDef2.bodyA = vesselBody1;
        ropeJointDef2.maxLength = 150f;
        RopeJoint ropeJoint2 = (RopeJoint)  world.createJoint(ropeJointDef2);
        vesselEntity2.setBody(vesselBody2);
        vesselEntity2.setParent(entity);
        vesselEntity2.setDistance(entity.getPos().dst(vesselBody2.getPosition()));
        vesselEntity2.setHealth(100f);
        vesselEntity2.setJoint(ropeJoint2);
        vesselEntity2.setAnchorPoint(vesselBody2.getPosition());



        BloodVesselEntity vesselEntity3 = new BloodVesselEntity(applicationContainer);
        BodyDef vesselBodyDef3 = new BodyDef();
        vesselBodyDef3.position.set(126,-19);
        Body vesselBody3 = world.createBody(vesselBodyDef3);
        RopeJointDef ropeJointDef3 = new RopeJointDef();
        ropeJointDef3.bodyB = entity.getBody();
        ropeJointDef3.bodyA = vesselBody3;
        ropeJointDef3.maxLength = 150f;
        RopeJoint ropeJoint3 = (RopeJoint)  world.createJoint(ropeJointDef3);
        vesselEntity3.setBody(vesselBody3);
        vesselEntity3.setParent(entity);
        vesselEntity3.setDistance(entity.getPos().dst(vesselBody3.getPosition()));
        vesselEntity3.setHealth(100f);
        vesselEntity3.setJoint(ropeJoint3);
        vesselEntity3.setAnchorPoint(vesselBody3.getPosition());




        BloodVesselEntity vesselEntity4 = new BloodVesselEntity(applicationContainer);
        BodyDef vesselBodyDef4 = new BodyDef();
        vesselBodyDef4.position.set(54,112);
        Body vesselBody4 = world.createBody(vesselBodyDef4);
        RopeJointDef ropeJointDef4 = new RopeJointDef();
        ropeJointDef4.bodyB = entity.getBody();
        ropeJointDef4.bodyA = vesselBody4;
        ropeJointDef4.maxLength = 150f;
        RopeJoint ropeJoint4 = (RopeJoint)  world.createJoint(ropeJointDef4);
        vesselEntity4.setBody(vesselBody4);
        vesselEntity4.setParent(entity);
        vesselEntity4.setDistance(entity.getPos().dst(vesselBody4.getPosition()));
        vesselEntity4.setHealth(100f);
        vesselEntity4.setJoint(ropeJoint4);
        vesselEntity4.setAnchorPoint(vesselBody4.getPosition());





        BloodVesselEntity vesselEntity5 = new BloodVesselEntity(applicationContainer);
        BodyDef vesselBodyDef5 = new BodyDef();
        vesselBodyDef5.position.set(68,97);
        Body vesselBody5 = world.createBody(vesselBodyDef5);
        RopeJointDef ropeJointDef5 = new RopeJointDef();
        ropeJointDef5.bodyB = entity.getBody();
        ropeJointDef5.bodyA = vesselBody5;
        ropeJointDef5.maxLength = 150f;
        RopeJoint ropeJoint5 = (RopeJoint)  world.createJoint(ropeJointDef5);
        vesselEntity5.setBody(vesselBody5);
        vesselEntity5.setParent(entity);
        vesselEntity5.setDistance(entity.getPos().dst(vesselBody5.getPosition()));
        vesselEntity5.setHealth(100f);
        vesselEntity5.setJoint(ropeJoint5);
        vesselEntity5.setAnchorPoint(vesselBody5.getPosition());



        BloodVesselEntity vesselEntity6 = new BloodVesselEntity(applicationContainer);
        BodyDef vesselBodyDef6 = new BodyDef();
        vesselBodyDef6.position.set(155,70);
        Body vesselBody6 = world.createBody(vesselBodyDef6);
        RopeJointDef ropeJointDef6 = new RopeJointDef();
        ropeJointDef6.bodyB = entity.getBody();
        ropeJointDef6.bodyA = vesselBody6;
        ropeJointDef6.maxLength = 150f;
        RopeJoint ropeJoint6 = (RopeJoint)  world.createJoint(ropeJointDef6);
        vesselEntity6.setBody(vesselBody6);
        vesselEntity6.setParent(entity);
        vesselEntity6.setDistance(entity.getPos().dst(vesselBody6.getPosition()));
        vesselEntity6.setHealth(100f);
        vesselEntity6.setJoint(ropeJoint6);
        vesselEntity6.setAnchorPoint(vesselBody6.getPosition());



        entity.addAnchor(vesselEntity);
        entity.addAnchor(vesselEntity2);
        entity.addAnchor(vesselEntity3);
        entity.addAnchor(vesselEntity4);
        entity.addAnchor(vesselEntity5);
        entity.addAnchor(vesselEntity6);


    }


    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
