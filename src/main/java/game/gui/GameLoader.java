package game.gui;

import game.mechanics.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Shows the new game creator window, or loads a previous game.
 */
@Slf4j
public class GameLoader {

    /**
     * Creates a window where the player can start a new game.
     *
     * @param game access to the {@link Game} mechanics
     * @param primaryStage the previous {@code Stage}
     */
    public void loadNewGameCreator(Game game, Stage primaryStage) {
        primaryStage.setTitle("Create new game");
        HBox hBox = new HBox();
        Text nameText = new Text("Name:");
        TextField playerName = new TextField();
        hBox.getChildren().addAll(nameText, playerName);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();

        Button newGameButton = new Button("New game");
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!playerName.getText().equals("")) {
                    if (!playerName.getText().equals("")) {
                        game.setName(playerName.getText());
                        log.info("New player: {}", playerName.getText());
                        log.info("New game created");
                        GameWindow.showGameWindow(game, primaryStage);
                    }
                }
            }
        });
        vBox.getChildren().addAll(hBox, newGameButton);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(vBox, 400, 200));
    }

    /**
     * Loads a previous game.
     *
     * @param game access to the {@link Game} mechanics
     * @param primaryStage the previous {@code Stage}
     */
    public void loadPreviousGame(Game game, Stage primaryStage) {
        GameWindow.showGameWindow(game, primaryStage);
    }
}
