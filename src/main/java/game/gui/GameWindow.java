package game.gui;

import game.mechanics.Direction;
import game.mechanics.Game;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;

import static game.gui.GraphicElements.*;

@Slf4j
public class GameWindow {

    public static void ShowGameWindow(Game game, Stage primaryStage) {
        primaryStage.setTitle("Game");
        StackPane stackPane = new StackPane();
        GameWindowElements.CreateMenu(game, stackPane);
        GameWindowElements.CreateFields(game, stackPane);

        HBox illegalStepHBox = borderedHBox("ILLEGAL STEP");
        HBox winHBox = borderedHBox("YOU WIN!");
        illegalStepHBox.setVisible(false);
        illegalStepHBox.toFront();
        winHBox.setVisible(false);
        winHBox.toFront();
        stackPane.getChildren().addAll(illegalStepHBox, winHBox);
        illegalStepHBox.setAlignment(Pos.CENTER);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
                ExtraWindows.ExitWindow(game);
            }
        });

        Scene gameScene = new Scene(stackPane, 500, 500);
        Text steps = new Text("Steps: " + game.getSteps());
        stackPane.getChildren().add(steps);
        steps.setTranslateX(-210);
        steps.setTranslateY(240);

        GameWindowElements.ChangeRectangleColor(game.getCurrentRow(), game.getCurrentCol(), Color.CORNFLOWERBLUE);

        FadeTransition fadeOut = transitionInitialize(1.0, 0.0);
        fadeOut.setNode(illegalStepHBox);

        gameScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            int[] previous = new int[2];
            @Override
            public void handle(KeyEvent keyEvent) {
                previous[0] = game.getCurrentRow();
                previous[1] = game.getCurrentCol();
                GameWindowElements.ChangeRectangleColor(game.getCurrentRow(), game.getCurrentCol(), Color.TRANSPARENT);
                boolean stepKeyPressed = true;
                if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                    game.step(Direction.RIGHT);
                } else if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                    game.step(Direction.LEFT);
                } else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                    game.step(Direction.UP);
                } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                    game.step(Direction.DOWN);
                } else {
                    stepKeyPressed = false;
                }

                GameWindowElements.ChangeRectangleColor(game.getCurrentRow(), game.getCurrentCol(), Color.CORNFLOWERBLUE);
                if (stepKeyPressed) {
                    if (previous[0] == game.getCurrentRow() && previous[1] == game.getCurrentCol()) {
                        illegalStepHBox.setVisible(true);
                        fadeOut.playFromStart();
                        log.warn("Illegal step");
                    }
                }
                if (game.isGoal()) {
                    game.updateLeaderboard();
                    LeaderboardWindow.showLeaderboard(game);
                    primaryStage.close();
                }
                steps.setText("Steps: " + game.getSteps());
            }
        });

        primaryStage.setScene(gameScene);
        primaryStage.show();
    }
}
