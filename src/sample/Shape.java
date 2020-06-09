package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class Shape {
    Block[] blocks = new Block[4];
    Color[] colors;
    private final String name;
    public Shape(){
        this.name = randomShape();
        this.colors = randomColors();
        constructBlocks();
    }
    public Block[] getBlocks(){
        return blocks;
    }
    private void constructBlocks() {
        switch (this.name){
            case "Row":
                for(int i=0;i<4;i++){
                    blocks[i] = new Block(Game.NEWSHAPEX+i*Game.BOXSIZE, Game.NEWSHAPEY, colors);
                }
                break;
            case "Triangle":
                blocks[0] = new Block(Game.NEWSHAPEX, Game.NEWSHAPEY, colors);
                for (int i = 1; i < 4; i++) {
                    blocks[i] = new Block(Game.NEWSHAPEX + (i-1)*Game.BOXSIZE, Game.NEWSHAPEY + Game.BOXSIZE, colors);
                }
                break;
            case "Box":
                for (int i = 0; i < 4; i++) {
                    blocks[i] = new Block(Game.NEWSHAPEX + (i%2)*Game.BOXSIZE, Game.NEWSHAPEY+ (i/2)* Game.BOXSIZE , colors);
                }
                break;
            case "InvertedL":
                for (int i = 0; i < 3; i++) {
                    blocks[i] = new Block(Game.NEWSHAPEX, Game.NEWSHAPEY + i * Game.BOXSIZE, colors);
                }
                blocks[3] = new Block(Game.NEWSHAPEX, Game.NEWSHAPEY + 2 * Game.BOXSIZE, colors);;
                break;
        }
    }

    private String randomShape(){
        return Game.SHAPENAMES[(int)(Math.random() * Game.SHAPENAMES.length)];
    }
    private Color[] randomColors(){
        return Game.COLORPALETE[(int)(Math.random() * Game.COLORPALETE.length)];
    }

}
class Block{
    private int x, y;
    private final Color[] boxColors;

    public boolean isBlackBlock() {
        return blackBlock;
    }

    private boolean blackBlock = false;
    private boolean set=false;

    public void drawBlock(@NotNull GraphicsContext gc){
        gc.setFill(Game.BOXBORDERCOLOR);
        gc.fillRect(this.x+Game.BOXOFFSET, this.y+Game.BOXOFFSET, Game.BOXSIZE-2*Game.BOXOFFSET, Game.BOXSIZE-2*Game.BOXOFFSET);
        //upper shadow
        gc.setFill(boxColors[1]);
        gc.fillPolygon(new double[]{this.x + Game.BORDERSIZE, this.x + Game.BOXSIZE - Game.BORDERSIZE,
                this.x + Game.BOXSIZE - Game.BORDERSIZE}, new double[]{this.y + Game.BORDERSIZE,
                this.y + Game.BORDERSIZE, this.y + Game.BOXSIZE - Game.BORDERSIZE}, 3);
        //bottom shadow
        gc.setFill(boxColors[2]);
        gc.fillPolygon(new double[]{this.x + Game.BORDERSIZE, this.x + Game.BORDERSIZE,
                this.x + Game.BOXSIZE - Game.BORDERSIZE}, new double[]{this.y + Game.BORDERSIZE,
                this.y + Game.BOXSIZE - Game.BORDERSIZE, this.y + Game.BOXSIZE - Game.BORDERSIZE}, 3);
        // inner box
        gc.setFill(boxColors[0]);
        gc.fillRect(this.x + Game.BORDERSIZE + Game.SHADESIZE, this.y + Game.BORDERSIZE + Game.SHADESIZE,
                Game.INNERBOXSIZE, Game.INNERBOXSIZE);
    }
    public Block(int x, int y, Color[] colors){
        this.x = x;
        this.y = y;
        this.boxColors = colors;
        this.set=true;
    }
    public Block(int x, int y){
        this.boxColors = Game.BLACKBOXCOLORS;
        this.blackBlock = true;
        this.set=true;
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

    public boolean isSet() {
        return set;
    }
}
