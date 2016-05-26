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

import javax.swing.*;

public class TestScreen extends Screen {

    private ScreenStack ss;
    private ImageLayer bgLayer;
    private ImageLayer backLayer;
    private ImageLayer headLayer;
    private ImageLayer hart1Layer;
    private ImageLayer hart2Layer;
    private ImageLayer hart3Layer;
    private ImageLayer hart4Layer;
    private ImageLayer coiniconLayer;
    private Image number;


    private  Zealot z;
    private Thief t;
    private static List <Leaf> leafList;
    private static List <Barring> barringList;
    private List<ImageLayer> scoreList1;
    private List<ImageLayer> scoreList2;
    private List<ImageLayer> coincountList1;
    private List<ImageLayer> coincountList2;
    private List<ImageLayer> coincountList3;
    private List<Thief> thiefList;

    private World world;
    private Coin coin;
    private Coin coin2;
    private Coin coin3;
    private Coin coin4;
    private int coinCheck = 1;
    private int coinCheck2 = 1;
    private int coinCheck3 = 1;
    private int coinCheck4 = 1;
    private int Score = 0;
    private int countcoin = 0;
    private int barringcounttime=0;
    private  int counthart  =0;
    private GameOver gameover;



    public static float M_PER_PIXEL = 1/26.666667f;
    private static int width = 24;
    private static int height = 18;
    private boolean showDebugDraw = true;
    private boolean checkPoint = true;
    private DebugDrawBox2D debugDraw;


    private HashMap<Object, String> bodies;
    public static final Font TITLE_FONT = graphics().createFont("Helvetica",Font.Style.PLAIN,36);
    JLabel scoreLabel = new JLabel("Score: 0");

    private int i = 0;

    public TestScreen(){

    }



  public TestScreen(final ScreenStack ss) {
      this.ss = ss;
      Vec2 gravity = new Vec2(0.0f,10.0f);
      world = new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);
      gameover = new GameOver(ss);


      bodies =  new HashMap<Object, String>();
      leafList = new ArrayList<Leaf>();
      barringList = new ArrayList<Barring>();
      scoreList1 = new ArrayList<ImageLayer>();
      scoreList2 = new ArrayList<ImageLayer>();
      thiefList = new ArrayList<Thief>();




      Image  bgImage = assets().getImage("images/bg3.png");
      bgLayer = graphics().createImageLayer(bgImage);
   
      Image  startImage = assets().getImage("images/back.png");
      backLayer = graphics().createImageLayer(startImage);
      backLayer.setTranslation(0,100);

      Image  headImage = assets().getImage("images/head.png");
      headLayer = graphics().createImageLayer(headImage);
      headLayer.setTranslation(0,0);

      Image  hartImage = assets().getImage("images/hartleaf.png");
      hart1Layer = graphics().createImageLayer(hartImage);
      hart1Layer.setTranslation(50,0);

      hart2Layer = graphics().createImageLayer(hartImage);
      hart2Layer.setTranslation(100,0);

      hart3Layer = graphics().createImageLayer(hartImage);
      hart3Layer.setTranslation(150,0);

      hart4Layer = graphics().createImageLayer(hartImage);
      hart4Layer.setTranslation(200,0);

      Image coiniconImage = assets().getImage("images/coinicon.png");
      coiniconLayer = graphics().createImageLayer(coiniconImage);
      coiniconLayer.setTranslation(400,20);


      number = assets().getImage("images/0.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/1.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/2.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/3.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/4.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/5.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/6.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/7.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/8.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));
      number = assets().getImage("images/9.png");
      scoreList1.add(graphics().createImageLayer(number));
      scoreList2.add(graphics().createImageLayer(number));




      backLayer.addListener(new Mouse.LayerAdapter(){
        @Override
        public void onMouseUp(Mouse.ButtonEvent event){
            ss.remove(ss.top());
        }
      });


      z = new Zealot(world,100f, 400f);
      t = new Thief(world,500f,400f);
      coin = new Coin(world,100f,200f);
      coin2 = new Coin(world,200,200f);
      coin3 = new Coin(world,300,200f);
      coin4 = new Coin(world,400,200f);

      

  }

  
  public void wasShown(){
    super.wasShown();
    
      this.layer.add(bgLayer);
      this.layer.add(backLayer);
      this.layer.add(z.layer());
      this.layer.add(t.layer());
      this.layer.add(coin.layer());
      this.layer.add(coin2.layer());
      this.layer.add(coin3.layer());
      this.layer.add(coin4.layer());
      this.layer.add(headLayer);
      this.layer.add(hart1Layer);
      this.layer.add(hart2Layer);
      this.layer.add(hart3Layer);
      this.layer.add(hart4Layer);
      this.layer.add(coiniconLayer);




      Body ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
     // groundShape.set(new Vec2(0,height), new Vec2(width, height));
      groundShape.set(new Vec2(0,height-1),new Vec2(width,height-1));
      ground.createFixture(groundShape, 0.0f);


      EdgeShape groundShapeYL = new EdgeShape();
      groundShapeYL.set(new Vec2(0,0),new Vec2(0,height));
      ground.createFixture(groundShapeYL, 0.0f);

      EdgeShape groundShapeYR = new EdgeShape();
      groundShapeYR.set(new Vec2(width,0),new Vec2(width,height));
      ground.createFixture(groundShapeYR, 0.0f);



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
                  countcoin+=100;
              }
              if(contact.getFixtureA().getBody()==coin2.getBody()||
                      contact.getFixtureB().getBody() == coin2.getBody()){
                  coin2.contact(contact);
                  coinCheck2 = 0;
                  countcoin+=100;
              }
              if(contact.getFixtureA().getBody()==coin3.getBody()||
                      contact.getFixtureB().getBody() == coin3.getBody()){
                  coin3.contact(contact);
                  coinCheck3 = 0;
                  countcoin+=100;
              }
              if(contact.getFixtureA().getBody()==coin4.getBody()||
                      contact.getFixtureB().getBody() == coin4.getBody()){
                  coin4.contact(contact);
                  coinCheck4 = 0;
                  countcoin+=100;
              }
              for(Leaf l :leafList){
                  if((a == l.getBody() &&  b == t.getBody()) || (a == t.getBody() && b == l.getBody())){
                      l.contact(contact);
                      Score++;
                      checkNumber();

                      t.contact(contact);

                  }

              }
              for(Barring bb :barringList){
                  if((a == bb.getBody() &&  b == z.getBody()) || (a == z.getBody() && b == bb.getBody())){
                      bb.contact(contact);
                      counthart+=1;
                      if(counthart == 2){
                          hart4Layer.setVisible(false);
                      }else if (counthart == 6){
                          hart3Layer.setVisible(false);
                      }
                      else if (counthart ==8){
                          hart2Layer.setVisible(false);
                      }else if (counthart ==16) {
                          hart1Layer.setVisible(false);
                      }else if (counthart >= 25)
                          toGameOver();

                  }
              }

          }

          @Override
          public void endContact(Contact contact) {
              Body a = contact.getFixtureA().getBody();
              Body b = contact.getFixtureB().getBody();
              for(Thief t: thiefList){
                  for(Leaf leaf: leafList) {
                      if ((a == leaf.getBody() && b == t.getBody()) || (b == leaf.getBody() && a == t.getBody())) {
                          Score++;
                          checkPoint = true;
                          checkNumber();
                      }
                  }
              }

          }

          @Override
          public void preSolve(Contact contact, Manifold manifold) {

          }

          @Override
          public void postSolve(Contact contact, ContactImpulse contactImpulse) {

          }
      });


      for(ImageLayer l: scoreList1) {
          this.layer.add(l);
          l.setVisible(false);
      }
      for(ImageLayer l: scoreList2)  {
          this.layer.add(l);
          l.setVisible(false);
      }
      scoreList1.get(0).setTranslation(305f,15f);
      scoreList2.get(0).setTranslation(340f,15f);
      scoreList1.get(0).setVisible(true);
      scoreList2.get(0).setVisible(true);

  }
  @Override
   public void update(int delta) {
      super.update(delta);
      world.step(0.033f, 10, 10);
      gameover.update(delta);
      z.update(delta);
      t.update(delta);
      coin.update(delta);
      coin2.update(delta);
      coin3.update(delta);
      coin4.update(delta);

      for(Leaf l : leafList){
          l.update(delta);
          this.layer.add(l.layer());
      }
      for(Barring b : barringList){
          b.update(delta);
          this.layer.add(b.layer());
      }


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
        t.paint(clock);
        gameover.paint(clock);
        barringcounttime+=10;
        System.out.println(barringcounttime);

            if(barringcounttime %2000==0){
                t.shooting();
            }



        //t.RunThief();
        for (Leaf l : leafList){
            l.paint(clock);
        }
        for (Barring b : barringList){
            b.paint(clock);
        }

        coin.paint(clock);
        coin2.paint(clock);
        coin3.paint(clock);
        coin4.paint(clock);

        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
            debugDraw.getCanvas().drawText(String.valueOf(countcoin),100,100);
        }

    }
    public static void addLeaf(Leaf l){
        leafList.add(l);
    }

    public void someoneScored()
    {
        scoreLabel.setBounds(10, 10, 100, 50);
        scoreLabel.setText("Score: " + Score);
    }

    public void shootThief(Barring barring) {
        barringList.add(barring);
    }
    public void toGameOver(){
        ss.push(new GameOver(ss));
    }
    public void checkNumber(){
        int front, back;

        front = Score/10;
        back = Score%10;

        for(ImageLayer l: scoreList1)  l.setVisible(false);
        for(ImageLayer l: scoreList2)  l.setVisible(false);

        scoreList1.get(front).setTranslation(305f,15f);
        scoreList2.get(back).setTranslation(340f,15f);
        scoreList1.get(front).setVisible(true);
        scoreList2.get(back).setVisible(true);
        checkPoint = false;
    }
}
