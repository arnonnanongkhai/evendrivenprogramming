package sut.game01.core;

import static playn.core.PlayN.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.*;
public class Barring {
    private float x;
    private float y;
    private static Body body;
    private boolean contacted;
    private int contactCheck;
    private Body other;
    private World world;
    private boolean checkContact = false;
    private ImageLayer barringLayer;


    public Barring(final World world, final float x_px, final float y_px) {
        this.x = x_px;
        this.y = y_px;
        this.world = world;

        Image barringImage = assets().getImage("images/barring.png");
        barringLayer  = graphics().createImageLayer(barringImage);
        body = initPhysicsBody(world, TestScreen.M_PER_PIXEL * x_px,TestScreen.M_PER_PIXEL * y_px);
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
        fixtureDef.filter.groupIndex = -1;
        body.createFixture(fixtureDef);

        body.createFixture(fixtureDef);

        body.setLinearDamping(1.0f);
        body.setTransform(new Vec2(x, y), 0f);
        body.applyLinearImpulse(new Vec2(-300f,0f), body.getPosition());
        //body.setBullet(false );
        return body;
    }

    public Layer layer(){
        return barringLayer;
    }

    public void update(int delta) {
        if(checkContact == true){
            body.setActive(false);
            checkContact = false;
        }




    }

    public void paint(Clock clock){
       barringLayer.setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL),
                body.getPosition().y / TestScreen.M_PER_PIXEL);

    }

    public Body getBody(){
        return this.body;
    }
    public void contact(Contact contact){
        //body.setActive(false);
        checkContact = true;
        barringLayer.setVisible(false);

    }
}
