package sut.game01.core;

import static playn.core.PlayN.*;

import playn.core.*;
//import tripleplay.game.ScreenStack;
import tripleplay.game.*;

public class HomeScreen extends UIScreen {

  private final ScreenStack ss;
  private final TestScreen testScreen;

   
   private ImageLayer bgLayer;
   private ImageLayer startButton;
   private ImageLayer shopButton;
   private ImageLayer optionButton;
   private ImageLayer exitButton;

  public static final Font TITLE_FONT = graphics().createFont("Helvetica",Font.Style.PLAIN,24);

  public HomeScreen(final ScreenStack ss) {
      this.ss = ss;
      testScreen = new TestScreen(ss);
      Image  bgImage = assets().getImage("images/bg.png");
      bgLayer = graphics().createImageLayer(bgImage);
   
      Image  startImage = assets().getImage("images/start.png");
      this.startButton = graphics().createImageLayer(startImage);
      Image  shopImage = assets().getImage("images/shop.png");
      this.shopButton = graphics().createImageLayer(shopImage);
      Image  optionImage = assets().getImage("images/option.png");
      this.optionButton = graphics().createImageLayer(optionImage);
      Image  exitImage = assets().getImage("images/exit.png");
      this.exitButton = graphics().createImageLayer(exitImage);

      startButton.setTranslation(230,95);
      shopButton.setTranslation(230,195);
      optionButton.setTranslation(230,295);
      exitButton.setTranslation(230,395);

      startButton.addListener(new Mouse.LayerAdapter(){
        @Override
        public void onMouseUp(Mouse.ButtonEvent event){
            ss.push(testScreen);
        }
      });
  }
  
  public void wasShown(){
    super.wasShown();
    
    this.layer.add(bgLayer);
    this.layer.add(startButton);
    this.layer.add(shopButton);
    this.layer.add(optionButton);
    this.layer.add(exitButton);
  }

}
