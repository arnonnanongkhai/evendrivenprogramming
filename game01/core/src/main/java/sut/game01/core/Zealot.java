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
public class Zealot {

  
     
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private float x;
    private float y;
    private Body body;

      public enum State {
        IDLE,RUN,ATTK
      }; 

     private State state  = State.IDLE;
     private int e =0;
     private int offset = 0;

     public Zealot (final World world, final float x , final float y){
         this.x = x;
         this.y = y;
           sprite = SpriteLoader.getSprite("images/zealot.json");



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

         /*  sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event){
              state = State.ATTK;
              spriteIndex = -1;
              e = 0;
            }
           });*/

     }
     public Layer layer(){
      return sprite.layer();
     }
      
   public void update(int delta) {
      if (hasLoaded == false) return;

       PlayN.keyboard().setListener(new Keyboard.Adapter() {
           @Override
           public void onKeyUp(Keyboard.Event event){
               if(event.key() == Key.SPACE){
                   switch(state){
                       case IDLE: state =State.RUN; break;
                       case RUN: state =State.ATTK; break;
                       case ATTK: state =State.IDLE; break;
                   }
               }
           }
       });



      e += delta;

      if(e >150){
        switch(state){
          case IDLE: offset = 0;
                     break;
          case RUN: offset = 4;
                     break;
          case ATTK: offset = 8;
                    if(spriteIndex ==10){
                      state = State.IDLE;
                    }
                    break;

        }
          
          spriteIndex = offset + ((spriteIndex + 1) %4);
          sprite.setSprite(spriteIndex);
          e = 0;
      }


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
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.35f;
        body.createFixture(fixtureDef);

        //body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }
    public void paint(Clock clock){
        if(!hasLoaded) return;

        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL ),
                body.getPosition().y / TestScreen.M_PER_PIXEL);

    }

}
