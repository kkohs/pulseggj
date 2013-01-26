package com.ggj.pulse.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.ggj.pulse.entities.AbstractEntity;
import com.ggj.pulse.entities.PlayerEntity;

/**
 * @author Modris Vekmanis
 */
public class EntityFactory {
    private World world;
    private AssetManager assetManager;


    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
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
        if(shape != null) {
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
        body.setLinearDamping(0.05f);
        body.setUserData(entity);
        return entity;
    }

    public void createBox(float x, float y, float halfWidth, float halfHeight, float angle) {
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(halfWidth, halfHeight);
        AbstractEntity entity = new AbstractEntity();
        entity.setPos(new Vector2(x, y));
        Body body = createObject(entity, shape);

    }

    public PlayerEntity createPlayer(float x, float y, float halfWidth, float halfHeight, float angle) {
        PlayerEntity entity = new PlayerEntity();
        entity.setPos(new Vector2(x,y));
        entity.setStatic(false);
        CircleShape shape = new CircleShape();
        shape.setRadius(4);

        Body body = createObject(entity,shape);
        entity.setBody(body);
        return entity;
    }

    public void attachRopes(PlayerEntity entity) {
       BodyDef anchorDef = new BodyDef();
        anchorDef.position.set(entity.getPos().x, entity.getPos().y -8);
    //    anchorDef.type = BodyDef.BodyType.StaticBody;

        Body anchor = world.createBody(anchorDef);


        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyB = entity.getBody();
        ropeJointDef.bodyA = anchor;
        ropeJointDef.maxLength = 100f;
        RopeJoint ropeJoint = (RopeJoint)  world.createJoint(ropeJointDef);

        anchorDef = new BodyDef();
        anchorDef.position.set(entity.getPos().x -8, entity.getPos().y);
   //     anchorDef.type = BodyDef.BodyType.StaticBody;

         anchor = world.createBody(anchorDef);
         entity.addAnchor(ropeJoint);

        ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyB = entity.getBody();
        ropeJointDef.bodyA = anchor;
        ropeJointDef.maxLength = 100f;

        ropeJoint = (RopeJoint) world.createJoint(ropeJointDef);
        entity.addAnchor(ropeJoint);

        anchorDef = new BodyDef();
        anchorDef.position.set(entity.getPos().x + 8, entity.getPos().y);
    //    anchorDef.type = BodyDef.BodyType.StaticBody;

         anchor = world.createBody(anchorDef);


        ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyB = entity.getBody();
        ropeJointDef.bodyA = anchor;
        ropeJointDef.maxLength = 100f;

        ropeJoint = (RopeJoint) world.createJoint(ropeJointDef);
        entity.addAnchor(ropeJoint);

        anchorDef = new BodyDef();
        anchorDef.position.set(entity.getPos().x, entity.getPos().y +8);
   //     anchorDef.type = BodyDef.BodyType.StaticBody;

         anchor = world.createBody(anchorDef);


        ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyB = entity.getBody();
        ropeJointDef.bodyA = anchor;
        ropeJointDef.maxLength = 100f;

        ropeJoint = (RopeJoint)  world.createJoint(ropeJointDef);
        entity.addAnchor(ropeJoint);

    }


    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
