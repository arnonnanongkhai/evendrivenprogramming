package sut.game01.core;


import static playn.core.PlayN.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.*;
public class Leaf {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private float x;
    private float y;
    private static Body body;
    private boolean contacted;
    private int contactCheck;
    private char direction;
    private Body other;
    private World world;
    private boolean checkContact = false;

    public enum State{
        IDLE
    };

    private State state = State.IDLE;
    private int offset = 0;
    private int e = 0;
    private int time = 0;
    public Leaf(final World world, final float x_px, final float y_px, final char direction) {
        this.x = x_px;
        this.y = y_px;
        this.world = world;
        this.direction = direction;
        sprite = SpriteLoader.getSprite("images/leaf.json");
        sprite.addCallback(new Callback<Sprite>(){
            @Override
            public void onSuccess(Sprite result){
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x, y );
                body = initPhysicsBody(world, TestScreen.M_PER_PIXEL * x_px,
                        TestScreen.M_PER_PIXEL * y_px);

                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause){
                //PlayN.log().error("Error loading image!", cause);
            }

        });

    }

    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(x, y);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 * TestScreen.M_PER_PIXEL/2,
                50*TestScreen.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.35f;
        //fixtureDef.filter.groupIndex = -4;
        body.createFixture(fixtureDef);

        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);
        //body.applyLinearImpulse(new Vec2(300f,0f), body.getPosition());
        //body.setBullet(false );
        if(direction == 'L')
            body.applyLinearImpulse(new Vec2(-300f,0f), body.getPosition());
        else if(direction == 'R')
            body.applyLinearImpulse(new Vec2(300f,0f), body.getPosition());
        return body;
    }

    public Layer layer(){
        return sprite.layer();
    }

    public void update(int delta) {
        if (hasLoaded == false)
            return;

        e += delta;
        time+=25;
      //  if(checkContact == true){
        //    body.setActive(false);
       //     checkContact = false;
//        }


        if (e > 150) {

            switch (state) {
                case IDLE: offset = 0;
                    if(spriteIndex == 3){
                        state = State.IDLE;
                    }
                    break;

            }
            spriteIndex = offset + ((spriteIndex + 1) % 4);
            sprite.setSprite(spriteIndex);
            if(direction == 'R'){
                sprite.layer().setTranslation(body.getPosition().x + 50/ TestScreen.M_PER_PIXEL,
                        body.getPosition().y / TestScreen.M_PER_PIXEL);
            }else {
                sprite.layer().setTranslation(body.getPosition().x - 50/ TestScreen.M_PER_PIXEL,
                        body.getPosition().y / TestScreen.M_PER_PIXEL);
            }

            e = 0;
        }
        if(checkContact == true) {
            body.setActive(false);
            sprite.layer().setVisible(false);
            world.destroyBody(body);
            checkContact = false;
        }/*else if(time >=1000){
            body.setActive(false);
            sprite.layer().setVisible(false);
            time=0;
        }*/


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
    public void contact(Contact contact){
        //body.setActive(false);
        checkContact = true;
        sprite.layer().setVisible(false);
        world.destroyBody(body);
    }


}
