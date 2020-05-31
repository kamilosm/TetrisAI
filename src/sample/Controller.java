package sample;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import static sample.Game.game;

public class Controller {
    @FXML
    private Pane paneNode;
    @FXML
    public void initialize(){
        paneNode.setOnKeyReleased((e) -> {
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
        paneNode.setOnKeyPressed((e) -> {
            if(e.getCode()== KeyCode.S || e.getCode()== KeyCode.DOWN)
                game.setSpeedUp();
        });
        paneNode.setOnMouseClicked((e) -> {
            //TODO CLICKS if necessary
        });
    }
}
