package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import static sample.Game.game;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("This is AI playing tetris.");
        primaryStage.setScene(game.initializeScene());
        primaryStage.show();
        game.start();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
