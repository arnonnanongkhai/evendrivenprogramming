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
public class Boss {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private float x;
    private float y;
    private Body body;
    private boolean contacted;
    private int contactCheck;
    private Body other;
    private World world;
    private TestScreen testScreen = new TestScreen();
    private Fire fire ;
    public boolean checkContact = false;
    public enum State {
        IDLE,RUN,ATTK ,LIDLE,LRUN,LAATK
    };

    private State state  = State.LIDLE;
    private int e =0;
    private int offset = 0;

    public Boss (final World world, final float x1 , final float y1){
        this.x = x1;
        this.y = y1;
        this.world = world;
        sprite = SpriteLoader.getSprite("images/boss.json");

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
        bodyDef.position = new Vec2(0, 0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100 * TestScreen.M_PER_PIXEL/2,
                sprite.layer().height()*TestScreen.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
       // fixtureDef.filter.groupIndex = -3;

        // fixtureDef.restitution = 0.35f;
        body.createFixture(fixtureDef);

        body.setFixedRotation(true);

        //body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }

    public Layer layer(){
        return sprite.layer();
    }
    public Body getBody(){
        return this.body;
    }

    public void update(int delta) {
        if (hasLoaded == false)
            return;


        e += delta;

        if(e >150){
            switch(state){
                case IDLE: offset = 0;
                    if(spriteIndex ==3){
                        state = State.IDLE;
                    }
                    break;
                case RUN: offset = 4;
                    break;
                case  ATTK: offset = 8;
                    if(spriteIndex ==11){
                        state = State.IDLE;
                    }
                    break;
                case LIDLE: offset =12;
                    if(spriteIndex == 15)
                        state = State.LIDLE;
                    break;
                case LRUN: offset =16;
                    if(spriteIndex == 19)
                        state =State.LIDLE;
                    break;
                case LAATK: offset =20;
                    if(spriteIndex == 23)
                        state =State.LIDLE;
                    break;
            }


            spriteIndex = offset + ((spriteIndex + 1) %4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
        if (checkContact == true){
            body.setActive(false);
            world.destroyBody(body);

        }


    }
    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL ),
                body.getPosition().y / TestScreen.M_PER_PIXEL);
    }

    public void shooting(){
        if (checkContact == false){
            //testScreen.shootBoss(new Fire(world,body.getPosition().x /TestScreen.M_PER_PIXEL -100,body.getPosition().y / TestScreen.M_PER_PIXEL));
        }
    }
    public void contact(Contact contact){
        checkContact = true;
        sprite.layer().setVisible(false);
    }
}



/* private final ScreenStack ss;
  public static final Font TITLE_FONT = graphics().createFont("Helvetica",Font.Style.PLAIN,24);

  public HomeScreen(final ScreenStack ss) {
      this.ss = ss;

      }
      


    TestScreen(){
            public TestScreen(final ScreenStack ss) {
              this.ss = ss;
              Vec2 gravity = new Vec2(0.0f,40.0f);
              world = new World(gravity);
              world.setWarmStarting(true);
              world.setAutoClearForces(true);


        }


 
  public void wasShown(){
    super.wasShown();
       ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
     // groundShape.set(new Vec2(0,height), new Vec2(width, height));
      groundShape.set(new Vec2(0,height-1),new Vec2(width,height-1));
      ground.createFixture(groundShape, 0.0f);

if(showDebugDraw){
          CanvasImage image = graphics().createImage(
                  (int)(width / M_PER_PIXEL),
                  (int)(height / M_PER_PIXEL)
          );
          layer.add(graphics().createImageLayer(image));
          debugDraw = new DebugDrawBox2D();
          debugDraw.setCanvas(image);
          debugDraw.setFlipY(false);
          debugDraw.setStrokeAlpha(150);
          debugDraw.setFlags(DebugDraw.e_shapeBit |
                  DebugDraw.e_jointBit |
                  DebugDraw.e_aabbBit);
          debugDraw.setCamera(0, 0, 1f / M_PER_PIXEL);
          world.setDebugDraw(debugDraw);
      }

 world.setContactListener(new ContactListener() {
          @Override
          public void beginContact(Contact contact) {

             Body a =  contact.getFixtureA().getBody();
            Body b = contact.getFixtureB().getBody();
         
              if(contact.getFixtureA().getBody()==z.getBody()||
                      contact.getFixtureB().getBody() == z.getBody()){
                     z.contact(contact);
              }
              for (Leaf l : leafList){
                  if(contact.getFixtureA().getBody()==l.getBody()||
                          contact.getFixtureB().getBody() == l.getBody()){
                      l.contact(contact);
                  }


            ....
            }
    }
    


    


    @Override
   public void update(int delta) {
      super.update(delta);
      world.step(0.033f, 10, 10);
      }


    @Override
    public void paint(Clock clock) {
        super.paint(clock);




*/






