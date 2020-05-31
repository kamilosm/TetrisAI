package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import static sample.Game.game;

public class Shape {
    Block a;
    Block b;
    Block c;
    Block d;
    Color[] colors;
    private String name;

    public Shape(Block a, Block b, Block c, Block d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = randomShape();
        this.colors = randomColors();
    }
    public Shape(){

    }
    private String randomShape(){
        return game.SHAPENAMES[(int)(Math.random() * game.SHAPENAMES.length)];
    }
    private Color[] randomColors(){
        return game.COLORPALETE[(int)(Math.random() * game.COLORPALETE.length)];
    }

}
class Block{
    private int x, y;
    private Color[] boxColors;

    public void drawBlock(@NotNull GraphicsContext gc){
        gc.setFill(game.BOXBORDERCOLOR);
        gc.fillRect(this.x, this.y, game.BOXSIZE, game.BOXSIZE);
        //upper shadow
        gc.setFill(boxColors[1]);
        gc.fillPolygon(new double[]{this.x + game.BORDERSIZE, this.x + game.BOXSIZE - 2*game.BORDERSIZE,
                this.x + game.BOXSIZE - 2*game.BORDERSIZE}, new double[]{this.y + game.BORDERSIZE,
                this.y + game.BORDERSIZE, this.y + game.BOXSIZE - 2*game.BORDERSIZE}, 3);
        //bottom shadow
        gc.setFill(boxColors[2]);
        gc.fillPolygon(new double[]{this.x + game.BORDERSIZE, this.x + game.BORDERSIZE,
                this.x + game.BOXSIZE - 2*game.BORDERSIZE}, new double[]{this.y + game.BORDERSIZE,
                this.y + game.BOXSIZE - 2*game.BORDERSIZE, this.y + game.BOXSIZE - 2*game.BORDERSIZE}, 3);
        // inner box
        gc.setFill(boxColors[0]);
        gc.fillRect(this.x + game.BORDERSIZE + game.SHADESIZE, this.y + game.BORDERSIZE + game.SHADESIZE,
                game.INNERBOXSIZE, game.INNERBOXSIZE);
    }
    public Block(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
