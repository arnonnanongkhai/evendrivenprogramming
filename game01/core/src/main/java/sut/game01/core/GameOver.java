package sut.game01.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.*;

public class GameOver extends Screen {
    private ImageLayer bgLayer;
    private ImageLayer overLayer;



    public GameOver(final ScreenStack ss) {
        Image bgImage = assets().getImage("images/bggameover.png");
        bgLayer = graphics().createImageLayer(bgImage);

        Image overImage = assets().getImage("images/gameover.png");
        overLayer = graphics().createImageLayer(overImage);
        overLayer.setTranslation(200,50);
    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bgLayer);
        this.layer.add(overLayer);
    }
    public void paint(Clock clock){

    }
    public void update(int delta) {
        
    }
}
