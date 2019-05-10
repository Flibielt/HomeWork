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

public class GameLoader {

    public void LoadNewGameCreator(Game game, Stage primaryStage) {
        primaryStage.setTitle("Create new game");
        HBox hBox = new HBox();
        Text nameText = new Text("Name:");
        TextField textField = new TextField();
        hBox.getChildren().addAll(nameText, textField);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();

        Button newGameButton = new Button("New game");
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!textField.getText().equals("")) {
                    game.setName(textField.getText());
                    GameWindow.ShowGameWindow(game, primaryStage);
                }
            }
        });
        vBox.getChildren().addAll(hBox, newGameButton);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(vBox, 400, 200));
    }

    public void LoadPreviousGame(Game game, Stage primaryStage) {
        GameWindow.ShowGameWindow(game, primaryStage);
    }
}
