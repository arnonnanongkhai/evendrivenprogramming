package sut.game01.core;

import static playn.core.PlayN.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.*;
//import tripleplay.game.ScreenStack;
import tripleplay.game.*;
import sut.game01.core.*;

public class Fire {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private float x;
    private float y;
    private Body body;
    private boolean contacted;
    private int contactCheck;
    private Body other;
    private World world1;
    private boolean checkContact = false;




    public enum State {
        IDLE
    };

    private State state  = State.IDLE;
    private int e =0;
    private int offset = 0;

    public Fire(final World world, final float x_px, final float y_px){
        this.x = x_px;
        this.y = y_px;
        this.world1 =world;

        sprite = SpriteLoader.getSprite("images/fire.json");

        sprite.addCallback(new Callback<Sprite>() {

            @Override
            public void onSuccess(Sprite result){
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,sprite.height() /2f);
                sprite.layer().setTranslation(x,y + 13f);
                body = initPhysicsBody(world, TestScreen.M_PER_PIXEL * x,
                        TestScreen.M_PER_PIXEL * y);
                hasLoaded = true;
            }
            @Override
            public void onFailure(Throwable cause){
                PlayN.log().error("Error loading image!",cause);
            }

        });


    }
    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(x, y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 * TestScreen.M_PER_PIXEL/2,
                50*TestScreen.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1.0f;
        fixtureDef.restitution = 0.35f;
       // fixtureDef.filter.groupIndex = -3;
        body.createFixture(fixtureDef);

        body.createFixture(fixtureDef);

        body.setLinearDamping(1.0f);
        body.setTransform(new Vec2(x, y), 0f);
        body.applyLinearImpulse(new Vec2(-400f,0f), body.getPosition());
        //body.setBullet(false );
        return body;
    }

    public Layer layer(){
        return sprite.layer();
    }

    public void update(int delta) {
        if (hasLoaded == false)
            return;

        if(checkContact == true){
            body.setActive(false);
            world1.destroyBody(body);
            checkContact = false;
        }

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
        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL),
                body.getPosition().y / TestScreen.M_PER_PIXEL);

    }

    public Body getBody(){
        return this.body;
    }

    public void contact(Contact contact) {
        checkContact = true;
        layer().setVisible(false);
        world1.destroyBody(body);
    }
}
