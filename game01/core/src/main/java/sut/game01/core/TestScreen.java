package sut.game01.core;

import static playn.core.PlayN.*;

import playn.core.*;
//import tripleplay.game.ScreenStack;
import tripleplay.game.*;
import sut.game01.core.Zealot;
public class TestScreen extends Screen {

  private final ScreenStack ss;

   
   private ImageLayer bgLayer;
   private ImageLayer backLayer; 
   private  Zealot z;

  public static final Font TITLE_FONT = graphics().createFont("Helvetica",Font.Style.PLAIN,24);

  public TestScreen(final ScreenStack ss) {
      this.ss = ss;
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

      

  }

  
  public void wasShown(){
    super.wasShown();
    
    this.layer.add(bgLayer);
    this.layer.add(backLayer);
     z = new Zealot(560f,400f);
      this.layer.add(z.layer());

  }
  @Override
   public void update(int delta) {
    super.update(delta);
    z.update(delta);
   }

}
