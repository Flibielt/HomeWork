package game.gui;

import game.mechanics.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Contains the help and the exit windows.
 */
@Slf4j
public class ExtraWindows {

    /**
     * Creates a new window, which describes the game's rules.
     */
    public static void HelpWindow() {
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Text movetext = new Text("You can move with the arrows or with the WASD buttons");
        Text stepText = new Text("You will step as many as the current field holds");
        Text directionText = new Text("You cannot step back");
        Text goalText = new Text("The goal is to reach the bottom right corner");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                helpStage.close();
            }
        });

        vBox.getChildren().addAll(movetext, stepText, directionText, goalText, closeButton);
        vBox.setSpacing(10);
        helpStage.setScene(new Scene(vBox, 400, 200));
        helpStage.show();
    }

    /**
     * Creates a new window, where the player can exit with or without saving or return to the game.
     *
     * @param game {@link Game} access point to the game mechanics
     */
    public static void ExitWindow(Game game) {
        HBox hBox = new HBox();
        Button exitButton = new Button("Exit");
        Button saveExitButton = new Button("Save and exit");
        Button cancelButton = new Button("Cancel");
        hBox.getChildren().addAll(exitButton, cancelButton, saveExitButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Stage exitStage = new Stage();
        exitStage.setTitle("Exit window");
        exitStage.setScene(new Scene(hBox, 400, 100));
        exitStage.show();

        log.info("PlayerFromLeaderboard wants to exit");

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                log.info("Exit game without save");
                Platform.exit();
            }
        });

        saveExitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.saveGame();
                log.info("Exit game");
                Platform.exit();
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                log.info("Return to game");
                exitStage.close();
            }
        });

    }
}
