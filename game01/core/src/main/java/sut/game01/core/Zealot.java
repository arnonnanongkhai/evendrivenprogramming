package sut.game01.core;

import static playn.core.PlayN.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.*;
//import tripleplay.game.ScreenStack;
import tripleplay.game.*;
import sut.game01.core.*;
import org.jbox2d.dynamics.contacts.Contact;
public class Zealot {

  
    private static TestScreen testScreen = new TestScreen();
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    public float x1;
    public float y1;
    public float zx1;
    public float zy1;
    private Body body;
    private boolean contacted;
    private int contactCheck;
    private Body other;
    private World world;

      public enum State {
        IDLE, RUN, ATTK, LIDLE, LRUN, LATTK, UPRUN
      };

    public enum Direction{
        LEFT, RIGHT
    };

     private State state  = State.IDLE;
    private Direction direction = Direction.RIGHT;
    private boolean d = false;
    private int d1 = 0;
     private int e =0;
     private int offset = 0;

     public Zealot (final World world, final float x , final float y){
         this.x1 = x;
         this.y1 = y;
         this.world = world;
           sprite = SpriteLoader.getSprite("images/zealot.json");



           sprite.addCallback(new Callback<Sprite>() {

            @Override
            public void onSuccess(Sprite result){
              sprite.setSprite(spriteIndex);
              sprite.layer().setOrigin(sprite.width() / 2f,sprite.height() /2f);
              sprite.layer().setTranslation(x,y);
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
     public Layer layer(){
      return sprite.layer();
     }
      
   public void update(int delta) {
      if (hasLoaded == false) return;



       PlayN.keyboard().setListener((new  Keyboard.Adapter(){
           @Override
           public void onKeyDown(Keyboard.Event event) {
               if(event.key() == Key.RIGHT){
                  switch(state){
                       case IDLE: state =State.RUN;
                           if(state == State.RUN){
                               body.applyForce(new Vec2(500f,0f),body.getPosition());
                               break;
                           }
                      case LIDLE: state = State.IDLE;
                          if(state == State.RUN){
                              body.applyForce(new Vec2(500f,0f),body.getPosition());
                              break;
                          }
                   }

               }

               if(event.key() == Key.LEFT){
                   switch(state){
                       case LIDLE: state =State.LRUN;
                           if(state == State.LRUN){
                               body.applyForce(new Vec2(-500f,0f),body.getPosition());
                               break;
                           }
                       case IDLE: state = State.LRUN;
                           if(state == State.LRUN){
                               body.applyForce(new Vec2(-500f,0f),body.getPosition());
                               break;
                           }
                   }
               }




               if(event.key() == Key.UP){
                   jump();
               }
             
             /* if(event.key() == Key.SPACE){
                   Leaf l;
                   switch (state){
                       case IDLE: state = State.ATTK;
                           l = new Leaf(world,body.getPosition().x /TestScreen.M_PER_PIXEL +100,body.getPosition().y / TestScreen.M_PER_PIXEL,'R');
                           testScreen.addLeaf(l);
                           break;
                       case LIDLE: state = State.LATTK;
                           l = new Leaf(world,body.getPosition().x /TestScreen.M_PER_PIXEL -100,body.getPosition().y / TestScreen.M_PER_PIXEL,'L');
                           testScreen.addLeaf(l);
                           break;
                       case RUN:  state = State.ATTK;
                           l = new Leaf(world,body.getPosition().x /TestScreen.M_PER_PIXEL +100,body.getPosition().y / TestScreen.M_PER_PIXEL,'R');
                           testScreen.addLeaf(l);
                           break;
                       case LRUN: state = State.LATTK;
                           l = new Leaf(world,body.getPosition().x /TestScreen.M_PER_PIXEL -100,body.getPosition().y / TestScreen.M_PER_PIXEL,'L');
                           testScreen.addLeaf(l);
                           break;
                       case  ATTK: state = State.ATTK;
                           l = new Leaf(world,body.getPosition().x /TestScreen.M_PER_PIXEL +100,body.getPosition().y / TestScreen.M_PER_PIXEL,'R');
                           testScreen.addLeaf(l);
                           break;
                       case LATTK: state = State.LATTK;
                           l = new Leaf(world,body.getPosition().x /TestScreen.M_PER_PIXEL -100,body.getPosition().y / TestScreen.M_PER_PIXEL,'L');
                           testScreen.addLeaf(l);
                           break;
                   }
               }*/


           }
       }));


      e += delta;

      if(e >150){
        switch(state){
          case IDLE: offset = 0;
                     break;
          case RUN: offset = 4;
                  if(spriteIndex ==7){
                      state = State.IDLE;
                  }
              break;
          case ATTK: offset = 8;
                 if(spriteIndex == 11)
                     state = State.IDLE;
                 break;
            case LIDLE: offset = 12;
                break;
            case LRUN: offset = 16;
                if (spriteIndex ==19)
                    state = State.LIDLE;
                break;
            case LATTK: offset = 20;
                if(spriteIndex == 23)
                    state = State.LIDLE;
                break;
            case UPRUN:offset = 24;
                if(spriteIndex == 26)
                    state = State.IDLE;
                break;

        }
          
          spriteIndex = offset + ((spriteIndex + 1) %4);
          sprite.setSprite(spriteIndex);
          sprite.layer().setTranslation(body.getPosition().x / TestScreen.M_PER_PIXEL,
                  body.getPosition().y / TestScreen.M_PER_PIXEL);
          e = 0;
      }

     //  zx1 = body.getPosition().x;
     //  zy1 = body.getPosition().y;

  }
    private Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0, 0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100 * TestScreen.M_PER_PIXEL/2,
                sprite.layer().height()*TestScreen.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.2f;
        fixtureDef.filter.groupIndex = -3;
        fixtureDef.friction = 0.1f;
       // fixtureDef.restitution = 0.35f;
        body.createFixture(fixtureDef);

        body.setFixedRotation(true);
        body.setBullet(true);

        //body.createFixture(fixtureDef);

       // body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }
    public void paint(Clock clock){
        if(!hasLoaded) return;
       // sprite.layer().setRotation(body.getAngle());
        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL ),
                body.getPosition().y / TestScreen.M_PER_PIXEL);

    }

    public void contact(Contact contact){
        contacted = true;
        contactCheck = 0;

        if(state == State.ATTK ){
            state = State.IDLE;
        }
        if(contact.getFixtureA().getBody()==body){
            other = contact.getFixtureB().getBody();
        }else{
            other = contact.getFixtureA().getBody();
        }
    }
    public void jump(){
        body.applyForce(new Vec2(-10,-1600f),body.getPosition());
        //zy1=100;
    }
    public Body getBody(){
        return this.body;
    }

    public float getX(){
        return zx1;
    }
    public float getY(){
        return zy1;
    }

}
