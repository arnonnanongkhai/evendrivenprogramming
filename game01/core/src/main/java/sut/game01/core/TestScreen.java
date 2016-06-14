package sut.game01.core;

import static playn.core.PlayN.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
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

    private  Image pauseImage;
    private ScreenStack ss;
    private ImageLayer bgLayer;
    private ImageLayer backLayer;
    private ImageLayer headLayer;
    private ImageLayer hart1Layer;
    private ImageLayer hart2Layer;
    private ImageLayer hart3Layer;
    private ImageLayer hart4Layer;
    private ImageLayer coiniconLayer;
    private ImageLayer cloudLayer;
    private ImageLayer pushLayer;
    private Image number;


    private  Zealot z;
    //private  Boss boss;
   // private Thief t;
    private int countB=0;
    private int countT=0;
    private  int countS=0;
    private static List <Leaf> leafList;
    private static List <Fire> fireList;
    private static List <Barring> barringList;
    private List<ImageLayer> scoreList1;
    private List<ImageLayer> scoreList2;
    private List<ImageLayer> coincountList1;
    private List<ImageLayer> coincountList2;
    private List<ImageLayer> coincountList3;
    private List<Thief> thiefList;
    private List<Boss> bossList;

   // private List<Body> groundList;

    private World world;
    private  Body ground;
    private  int score = 0;
    private int countcoin = 0;
    private GamepauseScreen gamepushS;
    private int coutcontactBoss =0;
    private int barringcounttime=0;
    private  int counthart  =0;
    public static float bgx =0;
    public static float bgy = -1450;
    private float zx = 100;
    private float zy =400;
    private GameOver gameover;
    private int thiefTime = 0;
    private int thiefTimeB = 0;
    private boolean pauseCheck = true;



    public static float M_PER_PIXEL = 1/26.666667f;
    private static int width = 24;
    private static int height = 18;
    private boolean showDebugDraw =  false;
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
      Vec2 gravity = new Vec2(0.0f,40.0f);
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
      fireList = new ArrayList<Fire>();
     // groundList = new ArrayList<Body>();
        bossList = new ArrayList<Boss>();



      Image  bgImage = assets().getImage("images/bg3.png");
      bgLayer = graphics().createImageLayer(bgImage);
      bgLayer.setTranslation(bgx,bgy);
   
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

      Image cloudImage = assets().getImage("images/cloud.png");
      cloudLayer = graphics().createImageLayer(cloudImage);
      cloudLayer.setTranslation(0,450);

      pauseImage = assets().getImage("images/pp.png");
      pushLayer = graphics().createImageLayer(pauseImage);
      pushLayer.setTranslation(600f,10f);


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


      gamepushS = new GamepauseScreen(ss, bgImage);

      pushLayer.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
              ss.push(gamepushS);
              //pauseCheck = !pauseCheck;
          }
      });


      z = new Zealot(world,zx, zy);
     // t = new Thief(world,500f,400f);



     // boss = new Boss(world,500f,400f);


      

  }

  
  public void wasShown(){
    super.wasShown();
    
      this.layer.add(bgLayer);
      this.layer.add(backLayer);
      this.layer.add(z.layer());
//      this.layer.add(t.layer());
  //    this.layer.add(boss.layer());
      this.layer.add(headLayer);
      this.layer.add(hart1Layer);
      this.layer.add(hart2Layer);
      this.layer.add(hart3Layer);
      this.layer.add(hart4Layer);
      this.layer.add(coiniconLayer);
      this.layer.add(cloudLayer);
      this.layer.add(pushLayer);





       ground = world.createBody(new BodyDef());
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





 /*     Body groundL11 = world.createBody(new BodyDef());
      EdgeShape groundShapeL11 = new EdgeShape();
      groundShapeL11.set(new Vec2(width-22,height-8),new Vec2(width-13,height-8));
      groundL11.createFixture(groundShapeL11, 0.0f);
     // groundList.add(groundL11);

      Body groundL12 = world.createBody(new BodyDef());
      EdgeShape groundShapeL12 = new EdgeShape();
      groundShapeL12.set(new Vec2(width-15,height-14),new Vec2(width-4,height-14));
      groundL12.createFixture(groundShapeL12, 0.0f);
    //  groundList.add(groundL12);






*/







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
              for (Leaf l : leafList){
                  if(contact.getFixtureA().getBody()==l.getBody()||
                          contact.getFixtureB().getBody() == l.getBody()){
                      l.contact(contact);
                  }
                 /* for(Thief t:thiefList){
                      if((a == l.getBody() &&  b == t.getBody()) || (a == t.getBody() && b == l.getBody())){
                          l.contact(contact);
                         // Score+=1;
                          checkNumber();
                          t.contact(contact);
                      }
                  }*/

                  if((a == l.getBody() &&  b == z.getBody()) || (a == z.getBody() && b == l.getBody())){
                      z.contact(contact);

                  }
              }
              for (Fire f : fireList){
                  if(contact.getFixtureA().getBody()==f.getBody()||
                          contact.getFixtureB().getBody() == f.getBody()){
                      f.contact(contact);
                  }

              }
       /*      if(contact.getFixtureA().getBody()==coin.getBody()||
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
*/
              for(Leaf l :leafList){
                  for(Thief t:thiefList){
                      if((a == l.getBody() &&  b == t.getBody()) || (a == t.getBody() && b == l.getBody())){
                          l.contact(contact);
                          t.contact(contact);
                          //while (true){
                              countS++;
                              //if(countS%200==0){
                               //   score++;
                          System.out.println(score);
                          checkNumber();
                              //}
                          //}
                      }
                  }

                  if((a == l.getBody() &&  b == z.getBody()) || (a == z.getBody() && b == l.getBody())){
                      z.contact(contact);

                  }
              for(Boss boss: bossList) {
                  if ((a == l.getBody() && b == boss.getBody()) || (a == boss.getBody() && b == l.getBody())) {
                      coutcontactBoss++;
                      if (coutcontactBoss == 20) {
                          l.contact(contact);
                          boss.contact(contact);
                         // while (true){
                        //      countS++;
                        //      if(countS%200==0){
                                //  score+=2;
                          //System.out.println(score);
                                  checkNumber();
                        //      }
                         // }
                      }

                  }
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
              for(Fire bb :fireList){
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
                          t.contact(contact);
                          checkPoint = true;

                      }
                  }
              }
              for(Boss t: bossList){
                  for(Leaf leaf: leafList) {
                      if ((a == leaf.getBody() && b == t.getBody()) || (b == leaf.getBody() && a == t.getBody())) {
                          t.contact(contact);
                          checkPoint = true;

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

      for(Thief g:thiefList){
          this.layer.add(g.layer());
      }



  }
  @Override
   public void update(int delta) {
      super.update(delta);
      if(pauseCheck == true){
          gameover.update(delta);
          z.update(delta);
          // t.update(delta);
          //
          checkNumber();

          for(Leaf l : leafList){
              l.update(delta);
              this.layer.add(l.layer());
          }
          for(Barring b : barringList){
              b.update(delta);
              this.layer.add(b.layer());
          }
          for(Fire f : fireList){
              f.update(delta);
              this.layer.add(f.layer());
          }
          for(Thief f : thiefList){
              f.update(delta);
              this.layer.add(f.layer());
          }

          for(Boss f : bossList){
              f.update(delta);
              this.layer.add(f.layer());
          }



          thiefTime++;

          if(thiefTime > 100){
              int randomNum = 0 + (int)(Math.random() * 2);
              //System.out.println(randomNum);
              if(randomNum == 1){
                  thiefList.add(new Thief(world,z.getBody().getPosition().x+500, z.getBody().getPosition().y));
              }else if(randomNum == 0){
                  thiefList.add(new Thief(world,z.getBody().getPosition().x+300  , z.getBody().getPosition().y));
              }
              thiefTime = 0;


          }

          thiefTimeB++;
          if(thiefTimeB > 300){
              int randomNum1 = 0 + (int)(Math.random() * 2);
              //System.out.println(randomN um);
              if(randomNum1 == 1){
                  bossList.add(new Boss(world,z.getBody().getPosition().x+680, z.getBody().getPosition().y));
              }else if(randomNum1 == 0){
                     bossList.add(new Boss(world,z.getBody().getPosition().x+400  , z.getBody().getPosition().y));
              }
              thiefTimeB = 0;
          }
          // zx = z.getX();
          zy = z.getY();
          // System.out.println("zx = " + zx );
          //  System.out.println("zy = " + zy );
          // System.out.println("bgx = " + bgx );
          //  System.out.println("bgy = " + bgy );

          if(bgy >= -10){
              bgLayer.setTranslation(bgx,bgy);
              ss.push(new HomeScreen(ss));
          }else {
              bgy+=1;
              bgLayer.setTranslation(bgx,bgy);

          }
      }
      world.step(0.033f, 10, 10);


  }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(pauseCheck == true){
            z.paint(clock);
            //    t.paint(clock);
//        boss.paint(clock);
            gameover.paint(clock);
            barringcounttime+=10;
            // System.out.println(barringcounttime);

            for(Thief t:thiefList){
                if(barringcounttime %2000==0){
                    t.shooting();
                }
            }
            for(Boss boss : bossList){
                if(barringcounttime %2000 ==0){
                    boss.shooting();
                }
            }


            for (Leaf l : leafList){
                l.paint(clock);
            }
            for (Barring b : barringList){
                b.paint(clock);
            }
            for (Fire f: fireList){
                f.paint(clock);
            }
            for (Thief f: thiefList){
                f.paint(clock);
            }
            for (Boss f: bossList){
                f.paint(clock);
            }



            if(showDebugDraw){
                debugDraw.getCanvas().clear();
                world.drawDebugData();
                debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
                debugDraw.getCanvas().drawText(String.valueOf(countcoin),100,100);
            }

        }




    }
    public static void addLeaf(Leaf l){
        leafList.add(l);
    }

    public void someoneScored()
    {
        scoreLabel.setBounds(10, 10, 100, 50);
        scoreLabel.setText("Score: " + score);
    }

    public void shootThief(Barring barring) {
        barringList.add(barring);
    }
    public void shootBoss(Fire fire) {
        fireList.add(fire);
    }
    public void toGameOver(){
        ss.push(new GameOver(ss));
    }
    public void checkNumber(){
        int front, back;

        front = score/10;
        back = score%10;

        for(ImageLayer l: scoreList1)  l.setVisible(false);
        for(ImageLayer l: scoreList2)  l.setVisible(false);

        scoreList1.get(front).setTranslation(305f,15f);
        scoreList2.get(back).setTranslation(340f,15f);
        scoreList1.get(front).setVisible(true);
        scoreList2.get(back).setVisible(true);
        checkPoint = false;
    }


}
