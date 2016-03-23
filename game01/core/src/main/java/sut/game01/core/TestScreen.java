package sut.game01.core;

import static playn.core.PlayN.*;

import playn.core.*;

import tripleplay.game.ScreenStack;
import tripleplay.game.*;


public class TestScreen extends Screen {

  private final ScreenStack ss;
  private final ImageLayer bg;
  private final Image backImage;
  private final Image bgImage;
  private final ImageLayer backButton;
  public TestScreen(final ScreenStack ss) {
      this.ss = ss;

    bgImage = assets().getImage("images/bg.png");
    bg = graphics().createImageLayer(bgImage);
    this.layer.add(bg);
  
   backImage = assets().getImage("images/back.png");
   backButton = graphics().createImageLayer(backImage);
   backButton.setTranslation(10,10);
   
   backButton.addListener(new Mouse.LayerAdapter(){
      public void onMouseUp (Mouse.ButtonEvent event){
        ss.remove(ss.top());
      }
   });

  }

   public void wasShown(){
    super.wasShown();
    this.layer.add(bg);
    this.layer.add(backButton);
    }
 
}
