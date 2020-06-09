package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Timer;
import java.util.stream.Stream;

public class Game {
    public final static int BOXSIZE=30;
    public final static int BOXOFFSET=1;
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

    public final static int GAMESPEED = 70;
    public final static int XBLOCKS=15;
    public final static int WIDTH=BOXSIZE*XBLOCKS;
    public final static int YBLOCKS=20;
    public final static int PANELWIDTH = 200;
    public final static int HEIGHT=BOXSIZE*YBLOCKS;
    public final static Color BOXBORDERCOLOR = Color.BLACK;
    public final static int BORDERSIZE = 3;
    public final static int SHADESIZE = 4;
    public final static int INNERBOXSIZE=BOXSIZE-2*SHADESIZE-2*BORDERSIZE;
    public final static int NEWSHAPEX = (BOXSIZE*((XBLOCKS-1)/2));
    public final static int NEWSHAPEY = 20;
    public final static Game game = new Game();
    public final static Color[] BLACKBOXCOLORS = new Color[]{Color.BLACK, Color.GRAY, Color.DARKGRAY};

    private GraphicsContext gc;
    private boolean speedUp = false;
    private int points = 0;
    private Block[][] mesh = new Block[XBLOCKS][YBLOCKS];
    private boolean gameStarted=false;

    private Shape controledShape;

    public Scene getScene() {
        return scene;
    }

    private Scene scene;

    public Scene initializeScene(){
        Pane pane = new Pane();
        pane.setId("paneNode");
        Canvas mainCanvas = new Canvas();
        mainCanvas.setWidth(WIDTH+ 200);
        mainCanvas.setHeight(HEIGHT);
        game.gc = mainCanvas.getGraphicsContext2D();
        pane.getChildren().addAll(mainCanvas);
        game.scene = new Scene(pane, WIDTH + PANELWIDTH, HEIGHT);
        // Controll
        game.getScene().setOnKeyReleased((e) -> {
            switch (e.getCode()){
                case UP:
                case W:
                    game.rotate();
                    break;
                case DOWN:
                case S:
                    game.resetSpeedUp();
                    break;
                case LEFT:
                case A:
                    game.moveLeft();
                    break;
                case RIGHT:
                case D:
                    game.moveRight();
                    break;
            }
        });
        game.getScene().setOnKeyPressed((e) -> {
            if(e.getCode()== KeyCode.S || e.getCode()== KeyCode.DOWN)
                game.setSpeedUp();
        });
        game.getScene().setOnMouseClicked((e) -> {
            //TODO CLICKS if necessary
        });
        return game.scene;
    }
    private void initializeMesh(){
        for (int i = 0; i < YBLOCKS; i++) {
            game.mesh[0][i] = new Block(0, i*BOXSIZE);
            game.mesh[XBLOCKS-1][i] = new Block((XBLOCKS-1)*BOXSIZE, i*BOXSIZE);
        }
        for (int i = 1; i < XBLOCKS-1; i++) {
            game.mesh[i][YBLOCKS-1] = new Block(i*BOXSIZE, (YBLOCKS-1)*BOXSIZE);
        }
    }
    public void start(){
        initializeMesh();
        game.controledShape = generateShape();
            new AnimationTimer(){
                private long lastUpdate = System.nanoTime();
                @Override
                public void handle(long nanoTime) {
                    if(System.nanoTime()-this.lastUpdate>1000000000/GAMESPEED){
                        this.lastUpdate=System.nanoTime();
                        game.gc.clearRect(0,0, game.gc.getCanvas().getWidth(), game.gc.getCanvas().getHeight());
                        Stream.of(game.mesh).flatMap(Arrays::stream).forEach((Block b) -> {
                            if(b!=null){
                                if(b.isSet()){
                                    b.drawBlock(game.gc);
                                }
                            }
                        });
                        for (Block b: game.controledShape.getBlocks()
                             ) {
                            if(game.mesh[b.getX()/BOXSIZE][((b.getY())/BOXSIZE)+1]!=null){
                                for (Block block: game.controledShape.getBlocks()
                                ) {
                                    block.setY((block.getY()/BOXSIZE)*BOXSIZE);
                                    block.setX((block.getX()/BOXSIZE)*BOXSIZE);
                                    game.mesh[(block.getX()/BOXSIZE)][(block.getY()/BOXSIZE)] = block;
                                }
                                game.controledShape = generateShape();
                                checklines();
                                break;
                            }else{
                                if(game.speedUp){
                                    b.setY(b.getY()+2);
                                }else{
                                    b.setY(b.getY()+1);
                                }
                                b.drawBlock(game.gc);
                            }
                        }
                        gc.setFill(Color.BLACK);
                        gc.setFont(Font.font(28));
                        gc.fillText("Score: " + String.valueOf(game.points), XBLOCKS*BOXSIZE+10, 30);
                        game.points++;
                    }
                }
            }.start();
    }

    //TODO rotate shapes
    public void rotate() {
    }
    public void setSpeedUp(){
        game.speedUp=true;
    }
    public void resetSpeedUp(){
        game.speedUp=false;
    }
    public void moveLeft(){
        for (Block b: controledShape.getBlocks()
             ) {
            if(b.getX()<BOXSIZE){
               return;
            }
            if (game.mesh[(b.getX()/BOXSIZE)-1][b.getY()/BOXSIZE]!=null){
                return;
            }
        }
        // move left
        for (Block b: controledShape.getBlocks()){
            b.setX(b.getX()-BOXSIZE);
        }
    }
    public void moveRight(){
        for (Block b: controledShape.getBlocks()
        ) {
            if(b.getX()>BOXSIZE*(XBLOCKS-1)){
                return;
            }
            if (game.mesh[(b.getX()/BOXSIZE)+1][b.getY()/BOXSIZE]!=null){
                return;
            }
        }
        // move right
        for (Block b: controledShape.getBlocks()){
            b.setX(b.getX()+BOXSIZE);
        }
    }
    private Shape generateShape(){
        return new Shape();
    }
    private void checklines(){
        for (int row =YBLOCKS-1;row>0;row--) {
            boolean full = true;
            for(int nb=1;nb<XBLOCKS-1;nb++){
                if(game.mesh[nb][row]==null) {
                    full = false;
                }
            }
            if(full&&!game.mesh[1][row].isBlackBlock()){
                for (int i = 1; i < XBLOCKS-1; i++) {
                    game.mesh[i][row]=null;
                }
                game.points+=2700;
                for (int i = row; i > 0; i--) {
                    for (int j = 1; j < XBLOCKS-1; j++) {
                        game.mesh[j][i] = game.mesh[j][i-1];
                        if(game.mesh[j][i]!=null)
                            game.mesh[j][i].setY(game.mesh[j][i].getY()+BOXSIZE);
                    }
                }
            }
        }
    }

}
