package sut.game01.core;

import static playn.core.PlayN.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import org.jbox2d.collision.shapes.EdgeShape;
//import tripleplay.game.ScreenStack;
import playn.core.util.Clock;
import tripleplay.game.*;
import org.jbox2d.dynamics.*;
import java.util.*;
import sut.game01.core.Zealot;
public class TestScreen extends Screen {

  private final ScreenStack ss;
  // private ImageLayer bgLayer;
   private ImageLayer backLayer;

   private  Zealot z;

    private World world;
    private Coin coin;
    private Coin coin2;
    private Coin coin3;
    private Coin coin4;
    private int coinCheck = 1;
    private int coinCheck2 = 1;
    private int coinCheck3 = 1;
    private int coinCheck4 = 1;
    private int core = 0;

    public static float M_PER_PIXEL = 1/26.666667f;
    private static int width = 24;
    private static int height = 18;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;

    private List<Zealot> plantMap;
    private HashMap<Object, String> bodies;
    private List<Coin> coinList;
    public static final Font TITLE_FONT = graphics().createFont("Helvetica",Font.Style.PLAIN,36);

    private int i = 0;



  public TestScreen(final ScreenStack ss) {
      this.ss = ss;
      Vec2 gravity = new Vec2(0.0f,10.0f);
      world = new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);

      plantMap = new ArrayList<Zealot>();
      coinList = new ArrayList<Coin>();
      bodies =  new HashMap<Object, String>();


     // Image  bgImage = assets().getImage("images/bg2.png");
      //bgLayer = graphics().createImageLayer(bgImage);
   
      Image  startImage = assets().getImage("images/back.png");
      backLayer = graphics().createImageLayer(startImage);

      backLayer.addListener(new Mouse.LayerAdapter(){
        @Override
        public void onMouseUp(Mouse.ButtonEvent event){
            ss.remove(ss.top());
        }
      });


      z = new Zealot(world,100f, 400f);
      bodies.put(z.getBody(), "plantman_1");
      coin = new Coin(world,100f,250f);
      coin2 = new Coin(world,200,250f);
      coin3 = new Coin(world,300,250f);
      coin4 = new Coin(world,400,250f);

      

  }

  
  public void wasShown(){
    super.wasShown();
    
    //this.layer.add(bgLayer);
      this.layer.add(backLayer);
      this.layer.add(z.layer());
      this.layer.add(coin.layer());
      this.layer.add(coin2.layer());
      this.layer.add(coin3.layer());
      this.layer.add(coin4.layer());




      Body ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(0,height), new Vec2(width, height));
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
     /* mouse().setListener(new Mouse.Adapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event) {
              //Zealot he = new Zealot(world, (float)event.x(), (float)event.y());
              //plantMap.add(he);
              BodyDef bodyDef = new BodyDef();
              bodyDef.type = BodyType.DYNAMIC;
              bodyDef.position = new Vec2(event.x() /26.666667f, event.y() / 26.666667f);
              Body body = world.createBody(bodyDef);

              bodies.put(body, "test_ " + i);
              i++;
              CircleShape shape = new CircleShape();
              shape.setRadius(1f);
              FixtureDef fixtureDef = new FixtureDef();
              fixtureDef.shape = shape;
              fixtureDef.density = 0.4f;
              fixtureDef.friction = 0.1f;
              fixtureDef.restitution = 1f;
              body.createFixture(fixtureDef);
              body.setLinearDamping(0.2f);
          }
      });
*/

      for(Zealot h: plantMap){
          System.out.println("add");
          this.layer.add(h.layer());
      }

      world.setContactListener(new ContactListener() {
          @Override
          public void beginContact(Contact contact) {
            Body a =  contact.getFixtureA().getBody();
            Body b = contact.getFixtureB().getBody();
            //  if(bodies.get(a) != null){
            //      a.applyForce(new Vec2(200f,0f),b.getPosition());
            //      b.applyLinearImpulse(new Vec2(200f, 0f), b.getPosition());
            //  }
              if(contact.getFixtureA().getBody()==z.getBody()||
                      contact.getFixtureB().getBody() == z.getBody()){
                  z.contact(contact);
              }
              if(contact.getFixtureA().getBody()==coin.getBody()||
                      contact.getFixtureB().getBody() == coin.getBody()){
                  coin.contact(contact);
                  coinCheck = 0;
                  core++;
              }
              if(contact.getFixtureA().getBody()==coin2.getBody()||
                      contact.getFixtureB().getBody() == coin2.getBody()){
                  coin2.contact(contact);
                  coinCheck2 = 0;
                  core++;
              }
              if(contact.getFixtureA().getBody()==coin3.getBody()||
                      contact.getFixtureB().getBody() == coin3.getBody()){
                  coin3.contact(contact);
                  coinCheck3 = 0;
                  core++;
              }
              if(contact.getFixtureA().getBody()==coin4.getBody()||
                      contact.getFixtureB().getBody() == coin4.getBody()){
                  coin4.contact(contact);
                  coinCheck4 = 0;
                  core++;
              }

          }

          @Override
          public void endContact(Contact contact) {

          }

          @Override
          public void preSolve(Contact contact, Manifold manifold) {

          }

          @Override
          public void postSolve(Contact contact, ContactImpulse contactImpulse) {

          }
      });

  }
  @Override
   public void update(int delta) {
    super.update(delta);
    z.update(delta);
      coin.update(delta);
      coin2.update(delta);
      coin3.update(delta);
      coin4.update(delta);
      for(Zealot h: plantMap){
          //System.out.println("update");
          this.layer.add(h.layer());
          h.update(delta);
      }
      world.step(0.033f, 10, 10);
      if(coinCheck == 0){
          coin.layer().setVisible(false);
      }
      if(coinCheck2 == 0){
          coin2.layer().setVisible(false);
      }
      if(coinCheck3 == 0){
          coin3.layer().setVisible(false);
      }
      if(coinCheck4 == 0){
          coin4.layer().setVisible(false);
      }

  }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        z.paint(clock);
        coin.paint(clock);
        coin2.paint(clock);
        coin3.paint(clock);
        coin4.paint(clock);
        for(Zealot h: plantMap){
            //System.out.println("paint");
            h.paint(clock);
        }
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
            debugDraw.getCanvas().drawText(String.valueOf(core),100,100);
        }
    }
}
