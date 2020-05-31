package sample;

import javafx.animation.Animation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Game {
    public final static int BOXSIZE=25;
    public final static String[] SHAPENAMES =
            {"InvertedL" , "Row", "Triangle", "Box"};

    // Color palete includes The base color, and up, down shadow for each pack
    public final static Color[][] COLORPALETE = {new Color[]{Color.rgb(255, 0 ,247), Color.rgb(255, 103, 247), Color.rgb(156, 0, 150)},
    new Color[]{Color.rgb(0, 255, 78), Color.rgb(147,255,165), Color.rgb(0, 230, 68)},
    new Color[]{Color.rgb(255, 164, 51), Color.rgb(255, 201, 116), Color.rgb(206, 131, 37)},
    new Color[]{Color.rgb(30, 22, 246), Color.rgb(104, 105, 245), Color.rgb(14, 6, 150)},
    new Color[]{Color.rgb(242, 242, 0), Color.rgb(207, 207, 29), Color.rgb(236, 236, 117)},
    new Color[]{Color.rgb(255, 0 ,19), Color.rgb(255, 134, 136), Color.rgb(224, 0 ,16)},
    new Color[]{Color.rgb(0, 255, 255), Color.rgb(0, 153, 154), Color.rgb(152, 254, 253)}};

    public final static int XBLOCKS=20;
    public final static int WIDTH=BOXSIZE*XBLOCKS;
    public final static int YBLOCKS=35;
    public final static int PANELWIDTH = 200;
    public final static int HEIGHT=BOXSIZE*YBLOCKS;
    public final static Color BOXBORDERCOLOR = Color.BLACK;
    public final static int BORDERSIZE = 4;
    public final static int SHADESIZE = 6;
    public final static int INNERBOXSIZE=BOXSIZE-SHADESIZE-BORDERSIZE;
    public final static Game game = new Game();

    private boolean speedUp = false;
    private int points = 0;
    private Block[][] mesh = new Block[XBLOCKS][YBLOCKS];
    private boolean gameStarted=false;

    private Shape controledShape;

    public Scene initializeScene(){
        Pane pane = new Pane();
        pane.setId("paneNode");
        Canvas mainCanvas = new Canvas();
        mainCanvas.setWidth(WIDTH+ 200);
        mainCanvas.setHeight(HEIGHT);
        pane.getChildren().addAll(mainCanvas);
        return new Scene(pane, WIDTH + PANELWIDTH, HEIGHT);
    }
    public void start(){
        //TODO Animation timer, and initial state of aplication
    }

    //TODO rotate shapes , move the shapes around
    public void rotate() {
    }
    public void setSpeedUp(){
        game.speedUp=true;
    }
    public void resetSpeedUp(){
        game.speedUp=false;
    }
    public void moveLeft(){

    }
    public void moveRight(){

    }
    private void generateFigure(){

    }
}
