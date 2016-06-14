package sut.game01.core;

import static playn.core.PlayN.*;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;


/**
 * Created by NeoNeo on 1/6/2559.
 */
public class GamepauseScreen extends Screen{
    private final ScreenStack ss;
    private Image bgImage;
    private ImageLayer bg;
    private Image pauseImage;
    private ImageLayer pauseZone;
    private Image resumeImage;
    private ImageLayer resume;
    private Image settingImage;
    private ImageLayer setting;
    private Image mainImage;
    private ImageLayer main;

    private HomeScreen home;

    public GamepauseScreen(final ScreenStack ss, Image bgImage){
        this.ss = ss;
        this.bgImage = bgImage;
        //home = new HomeScreen(ss);
        bg = graphics().createImageLayer(bgImage);

        pauseImage = assets().getImage("images/pausegame.png");
        pauseZone = graphics().createImageLayer(pauseImage);
        pauseZone.setTranslation(60f,20f);
        pauseZone.setTranslation(60f,20f);
        resumeImage = assets().getImage("images/resume.png");
        resume = graphics().createImageLayer(resumeImage);
        resume.setTranslation(90f, 260f);

        mainImage = assets().getImage("images/home.png");
        main = graphics().createImageLayer(mainImage);
        main.setTranslation(360f, 260f);


        resume.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top());
            }
        });

        //
        main.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(new HomeScreen(ss));
            }
        });



    }

    @Override
    public void wasShown() {
        super.wasShown();
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(pauseZone);
        this.layer.add(resume);
        this.layer.add(main);

    }
}
