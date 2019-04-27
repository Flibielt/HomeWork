package Game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Launcher extends Application {

    private Game game;


    @Override
    public void start(Stage startStage) {
        game = new Game();
        StackPane stackPane = new StackPane();


        Button button = new Button();

        button.setText("START NEW GAME");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newGame(startStage);
            }
        });

        stackPane.getChildren().add(button);



        //TODO: Should call the Game class
        //TODO: The table items could be Text objects, with border and padding
        //TODO: Menu bar, where the player can save the current game state
        //TODO: Start scene where the player can start a new game, or continue a previous one
        startStage.setScene(new Scene(stackPane, 800, 600));
        startStage.show();

        //newGame(startStage);
    }

    private void newGame(Stage gameStage) {
        gameStage.setTitle("Game");
        Text[][] fields = new Text[8][8];
        Group root = new Group();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fields[i][j] = new Text();
                fields[i][j].setText(String.valueOf(game.getField(i,j)));
                fields[i][j].setX(i*50 + 50);
                fields[i][j].setY(j*50 + 50);

                root.getChildren().add(fields[i][j]);
            }
        }


        gameStage.setScene(new Scene(root, 800, 600));
        gameStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
