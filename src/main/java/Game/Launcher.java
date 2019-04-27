package Game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Launcher extends Application {

    private Game game;


    @Override
    public void start(Stage startStage) {
        game = new Game();
        StackPane stackPane = new StackPane();
        startStage.setTitle("Game");


        Button newGameButton = new Button();
        Button loadGameButton = new Button();

        newGameButton.setText("START NEW GAME");
        loadGameButton.setText("LOAD GAME");


        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newGame(startStage);
            }
        });

        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.loadGame();
                newGame(startStage);
            }
        });

        stackPane.getChildren().addAll(newGameButton, loadGameButton);

        newGameButton.setTranslateY(-25);
        loadGameButton.setTranslateY(25);

        startStage.setScene(new Scene(stackPane, 800, 600));
        startStage.show();

    }

    /**
     * Creates a HBOX with red border which contains a Text
     * @param text the value of the Text
     * @return the HBox
     */
    private HBox borderedHBox(String text) {
        Text text1 = new Text();
        text1.setText(text);
        text1.setFont(Font.font("verdana", FontWeight.BOLD, 20));

        HBox hBox = new HBox();
        hBox.getChildren().add(text1);
        hBox.setStyle("-fx-background-color: white;" +
                        " -fx-border-color: red;" +
                        " -fx-border-width: 5px;"
        );
        hBox.setMaxSize(text1.getScaleX(), text1.getScaleY());
        return hBox;
    }

    /**
     * Creates a Rectangle which represents a field of the table
     * @return rectangle with border
     */
    private Rectangle fieldRectangle() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(50);
        rectangle.setHeight(50);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.toBack();
        return rectangle;
    }

    /**
     * Creates a stage for the game
     * @param gameStage the previous stage
     */
    private void newGame(Stage gameStage) {
        Text[][] fields = new Text[8][8];
        Rectangle[][] rectangles = new Rectangle[8][8];
        StackPane stackPane = new StackPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fields[i][j] = new Text();
                fields[i][j].setText(String.valueOf(game.getField(i,j)));

                rectangles[i][j] = fieldRectangle();

                stackPane.getChildren().addAll(fields[i][j], rectangles[i][j]);

                fields[i][j].setTranslateX((j - 4) * 50);
                fields[i][j].setTranslateY((i - 4) * 50);

                rectangles[i][j].setTranslateX((j - 4) * 50);
                rectangles[i][j].setTranslateY((i - 4) * 50);
                rectangles[i][j].toBack();

                System.out.print(game.getField(i,j) + " ");

            }
            System.out.println();
        }

        HBox illegalStepHBox = borderedHBox("ILLEGAL STEP");
        HBox winHBox = borderedHBox("YOU WIN!");
        illegalStepHBox.setVisible(false);
        winHBox.setVisible(false);
        stackPane.getChildren().addAll(illegalStepHBox, winHBox);


        Scene gameScene = new Scene(stackPane, 800, 600);

        System.out.println(game.getCurrentRow() + " " + game.getCurrentCol());
        rectangles[game.getCurrentRow()][game.getCurrentCol()].setFill(Color.CORNFLOWERBLUE);

        gameScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                rectangles[game.getCurrentRow()][game.getCurrentCol()].setFill(Color.TRANSPARENT);
                if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                    game.step("right");
                    System.out.println("right");
                } else if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                    game.step("left");
                    System.out.println("left");

                } else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                    game.step("up");
                    System.out.println("up");

                } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                    game.step("down");
                    System.out.println("down");

                }
                System.out.println(game.getCurrentRow() + " " + game.getCurrentCol());
                rectangles[game.getCurrentRow()][game.getCurrentCol()].setFill(Color.CORNFLOWERBLUE);
            }
        });


        gameStage.setScene(gameScene);
        gameStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
