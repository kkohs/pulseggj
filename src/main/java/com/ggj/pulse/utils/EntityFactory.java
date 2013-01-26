package com.ggj.pulse.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.ggj.pulse.ApplicationContainer;
import com.ggj.pulse.entities.*;
import com.ggj.pulse.graphics.GameScreen;
import com.ggj.pulse.logic.PhysicsController;

/**
 * @author Modris Vekmanis
 */
public class EntityFactory {
    private World world;
    private AssetManager assetManager;
    private GameScreen gameScreen;
    private ApplicationContainer applicationContainer;
    private PhysicsController physicsController;

    public EntityFactory(AssetManager assetManager, ApplicationContainer applicationContainer, PhysicsController physicsController) {
        this.assetManager = assetManager;
        this.applicationContainer = applicationContainer;
        this.physicsController = physicsController;
    }

    public Body createObject(AbstractEntity entity, Shape shape, float angle) {
        return createObject(entity, shape, angle, false, 20);
    }

    public Body createObject(AbstractEntity entity, Shape shape, float angle, boolean fixedRotation, int density) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = entity.getPos().x;
        bodyDef.angle = angle;
        bodyDef.position.y = entity.getPos().y;
        bodyDef.fixedRotation = fixedRotation;
        if (entity.isStatic()) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = 1;


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
        BackgroundEntity entity = new BackgroundEntity();
        entity.setPos(new Vector2(x, y));
        entity.setDraw(true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = entity.getPos().x;
        bodyDef.position.y = entity.getPos().y;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2;
            entity.setTexture(new Sprite(assetManager.get(AssetManager.BACK_GROUND, Texture.class),1600,900));
        Body body = world.createBody(bodyDef);
        assetManager.attachShape(body, fixtureDef, 400, bodyName);
        body.setAngularDamping(0.5f);
        //body.setLinearDamping(0.0005f);
        body.setUserData(entity);
        entity.setOrigin(assetManager.getOrigin(bodyName));
        entity.setBody(body);
        return entity;
    }

    public AbstractEntity createBox(float x, float y, float halfWidth, float halfHeight, float angle) {
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(halfWidth, halfHeight);
        RectangleEntity entity = new RectangleEntity(applicationContainer);
        entity.setPos(new Vector2(x, y));
        entity.setBody(createObject(entity, shape, 0));
        entity.setW(2 * halfWidth);
        entity.setH(2 * halfHeight);
        entity.getBody().setTransform(entity.getPos().x, entity.getPos().y, angle);

        applicationContainer.addToSpawn(entity);
        gameScreen.getVisibleEntities().add(entity);

        return entity;
    }

    public AbstractEntity createCircle(float x, float y, float radius, float angle) {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        CircleEntity entity = new CircleEntity(applicationContainer);
        entity.setPos(new Vector2(x, y));
        entity.setBody(createObject(entity, shape, 0));
        entity.setW(radius);
        entity.setH(radius);
        entity.getBody().setTransform(entity.getPos().x, entity.getPos().y, angle);

        gameScreen.getVisibleEntities().add(entity);
        applicationContainer.addToSpawn(entity);
        return entity;
    }

    public PlayerEntity createPlayer(float x, float y, float halfWidth, float halfHeight, float angle) {
        PlayerEntity entity = new PlayerEntity(applicationContainer);
        entity.setPos(new Vector2(x, y));
        entity.setStatic(false);


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
        fixtureDef.density = 200;


        Body body = world.createBody(bodyDef);
         assetManager.attachShape(body, fixtureDef, 40, "heart");
       // body.createFixture(fixtureDef);
        body.setAngularDamping(0.5f);
        body.setLinearDamping(0.05f);
        Fixture f = body.getFixtureList().get(0);

        entity.setBody(body);
       entity.setSprite(new Sprite(assetManager.get("textures/pulseHeart.png", Texture.class)));
        BodyDef centerDef = new BodyDef();
        centerDef.position.set(x, y);
        Body center = world.createBody(centerDef);
        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.maxLength = 0;
        ropeJointDef.bodyA = center;
        ropeJointDef.bodyB = body;

        entity.setCenterJoint((RopeJoint) world.createJoint(ropeJointDef));
        gameScreen.getVisibleEntities().add(entity);
        return entity;
    }

    public void attachRopes(PlayerEntity entity) {
        Vector2 pos = new Vector2();
        createRope(entity, new Vector2().set(-84, 49), 1);
        createRope(entity, new Vector2().set(212, 72), 2);
        createRope(entity, new Vector2().set(72, 122), 3);
        // createRope(entity, new Vector2().set(68, 97), 4);
        createRope(entity, new Vector2().set(166, -13), 5);


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
        float width = 2.5f;
        float height = 1.5f;

        Vector2 vec = entity.getPos();
        vec.sub(wallPos).nor();
        int count = (int) ((dist / width) );

        Body previous = wallBody;
        bloodVesselEntity.setWidth(width);
        bloodVesselEntity.setHeight(height);
        bloodVesselEntity.setSprite(new Sprite(assetManager.get(AssetManager.CHAIN_TEXTURE, Texture.class)));
         bloodVesselEntity.setRender(true);
        for (int i = 1; i < count; i++) {
            BloodVesselEntity e = new BloodVesselEntity(applicationContainer);
            e.setPos(vec.tmp().mul(i * width).add(wallPos));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width, height);
            e.setBody(createObject(e, shape, vec.angle() * MathUtils.degreesToRadians, false,1));
            e.getBody().setUserData(e);
            e.setGrpIndex(index);

            RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
            revoluteJointDef.initialize(previous, e.getBody(), new Vector2(previous.getPosition()).add(vec.tmp().mul(width / 2.0f)));
            revoluteJointDef.collideConnected = true;
            world.createJoint(revoluteJointDef);
            previous = e.getBody();

            bloodVesselEntity.getChain().add(e);

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

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
