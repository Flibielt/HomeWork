package game.main;

import game.gui.GameLoader;
import game.gui.LeaderboardWindow;
import game.mechanics.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * The main menu of the game.
 */
@Slf4j
public class MyApplication extends Application {

    /**
     * Access point to the {@link Game} mechanics.
     */
    private Game game;
    /**
     * Loads a new or a previous game.
     */
    private GameLoader gameLoader;

    @Override
    public void start(Stage primaryStage) {
        game = new Game();
        gameLoader = new GameLoader();
        primaryStage.setTitle("Game");
        primaryStage.setResizable(false);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        Button newGameButton = new Button();
        Button loadGameButton = new Button();
        Button leaderboardButton = new Button();
        vBox.getChildren().addAll(newGameButton, loadGameButton, leaderboardButton);

        newGameButton.setText("START NEW GAME");
        loadGameButton.setText("LOAD GAME");
        leaderboardButton.setText("SHOW LEADERBOARD");

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameLoader.loadNewGameCreator(game, primaryStage);
            }
        });

        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.loadGame();
                if (game.getName().equals("")) {
                    log.warn("There is no saved game to be loaded");
                    gameLoader.loadNewGameCreator(game, primaryStage);
                } else {
                    log.info("Load saved game");
                    gameLoader.loadPreviousGame(game, primaryStage);
                }
            }
        });

        leaderboardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LeaderboardWindow.showLeaderboard(game);
            }
        });

        primaryStage.setScene(new Scene(vBox, 400, 200));
        primaryStage.show();
    }
}
