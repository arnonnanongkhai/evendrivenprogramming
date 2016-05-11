package sut.game01.core;

import static playn.core.PlayN.*;

import org.jbox2d.common.Vec2;
import org.jbox2d.callbacks.DebugDraw;
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
   private ImageLayer bgLayer;
   private ImageLayer backLayer; 
   private  Zealot z;
    public static float M_PER_PIXEL = 1/26.666667f;
    private static int width = 24;
    private static int height = 18;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;


    private World world;
    //private Map<String, Hero> heroMap;
    private List<Zealot> plantMap;
    private int i = 0;


    public static final Font TITLE_FONT = graphics().createFont("Helvetica",Font.Style.PLAIN,24);

  public TestScreen(final ScreenStack ss) {
      this.ss = ss;
      Vec2 gravity = new Vec2(0.0f,10.0f);
      world = new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);

      plantMap = new ArrayList<Zealot>();


      Image  bgImage = assets().getImage("images/bg2.png");
      bgLayer = graphics().createImageLayer(bgImage);
   
      Image  startImage = assets().getImage("images/back.png");
      backLayer = graphics().createImageLayer(startImage);

      backLayer.addListener(new Mouse.LayerAdapter(){
        @Override
        public void onMouseUp(Mouse.ButtonEvent event){
            ss.remove(ss.top());
        }
      });


      z = new Zealot(world,320f, 200f);
      

  }

  
  public void wasShown(){
    super.wasShown();
    
    this.layer.add(bgLayer);
    this.layer.add(backLayer);


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
      mouse().setListener(new Mouse.Adapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event) {
              Zealot he = new Zealot(world, (float)event.x(), (float)event.y());
              //Hero he = new Hero(world, 100f, 100f);
              //heroMap.put("hero_" + i++, he);
              plantMap.add(he);
          }
      });
      this.layer.add(z.layer());

      for(Zealot h: plantMap){
          System.out.println("add");
          this.layer.add(h.layer());
      }

  }
  @Override
   public void update(int delta) {
    super.update(delta);
    z.update(delta);
      for(Zealot h: plantMap){
          //System.out.println("update");
          this.layer.add(h.layer());
          h.update(delta);
      }
      world.step(0.033f, 10, 10);
   }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);

        z.paint(clock);
        for(Zealot h: plantMap){
            //System.out.println("paint");
            h.paint(clock);
        }
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }
}
