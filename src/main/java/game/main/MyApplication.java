package game.main;

import game.gui.GameLoader;
import game.mechanics.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyApplication extends Application {

    private Game game;
    private GameLoader gameLoader;

    @Override
    public void start(Stage primaryStage) {
        game = new Game();
        gameLoader = new GameLoader();
        primaryStage.setTitle("Game");
        primaryStage.setResizable(false);

        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        Button newGameButton = new Button();
        Button loadGameButton = new Button();
        //stackPane.getChildren().addAll(newGameButton, loadGameButton);
        vBox.getChildren().addAll(newGameButton, loadGameButton);

        newGameButton.setText("START NEW GAME");
        loadGameButton.setText("LOAD GAME");

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameLoader.LoadNewGameCreator(game, primaryStage);
            }
        });

        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.loadGame();
                if (game.getName().equals("")) {
                    gameLoader.LoadNewGameCreator(game, primaryStage);
                } else {
                    gameLoader.LoadPreviousGame(game, primaryStage);
                }
            }
        });

        primaryStage.setScene(new Scene(vBox, 400, 200));
        primaryStage.show();
    }
}
