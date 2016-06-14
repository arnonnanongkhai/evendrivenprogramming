package sut.game01.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by NeoNeo on 31/5/2559.
 */
public class PhysicBody {
    private Body body;
    private float x;
    private float y;
    private float sx;
    private float sy;
    private World world;


    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private boolean contacted;
    private int contactCheck;
    private Body other;
    //private Body body;
    private BodyDef bodyDef;



    public enum State{
        IDLE
    };

    private State state = State.IDLE;
    private int offset = 0;
    private int e = 0;
    private ImageLayer cloudLayer;
    
    public  PhysicBody(final World world, final float x, final float y, float sx, float sy){
        this.world = world;
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;

        Image barringImage = assets().getImage("images/cloud.png");
        cloudLayer  = graphics().createImageLayer(barringImage);
        body = initPhysicsBody(world, TestScreen.M_PER_PIXEL * x,TestScreen.M_PER_PIXEL * y);

    }
    private Body initPhysicsBody(World world, float x, float y) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(x, y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sx * TestScreen.M_PER_PIXEL/2,
                sy*TestScreen.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        //fixtureDef.density = 0.4f;
        //fixtureDef.friction = 0.1f;
        //fixtureDef.restitution = 0.35f;

        body.createFixture(fixtureDef);

        //body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }

    public Layer layer(){
        return cloudLayer;
    }

    public void update(int delta) {
        if (hasLoaded == false)
            return;
        y += delta;
        //body.setTransform(new Vec2(x, y),0f);

        e += delta;

        if(e >150){
            switch(state){
                case IDLE: offset = 0;
                    break;
            }

            spriteIndex = offset + ((spriteIndex + 1) %4);
            sprite.setSprite(spriteIndex);
            sprite.layer().setTranslation(body.getPosition().x / TestScreen.M_PER_PIXEL,
                    body.getPosition().y / TestScreen.M_PER_PIXEL);
            e = 0;
        }
    }

    public void paint(Clock clock){
        if(!hasLoaded) return;
        //body.setTransform(new Vec2(x, y),0f);



        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL),
                body.getPosition().y / TestScreen.M_PER_PIXEL);

    }

    public Body getBody(){
        return this.body;
    }
}
