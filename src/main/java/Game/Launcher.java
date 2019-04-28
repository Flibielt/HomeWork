package Game;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


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

        startStage.setScene(new Scene(stackPane, 400, 400));
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

    private FadeTransition transitionInitialize(double from, double to) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1250));
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        return fadeTransition;
    }

    /**
     * Creates a new window, where the player can exit with or without saving or return to the game
     */
    private void exitWindow() {
        HBox hBox = new HBox();
        Button exitButton = new Button("Exit");
        Button saveExitButton = new Button("Save and exit");
        Button cancelButton = new Button("Cancel");
        hBox.getChildren().addAll(exitButton, cancelButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Stage exitStage = new Stage();
        exitStage.setTitle("Exit window");
        exitStage.setScene(new Scene(hBox, 400, 100));
        exitStage.show();

        if (game.saved()) {
            hBox.getChildren().add(saveExitButton);
        }
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        saveExitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.saveGame();
                Platform.exit();
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                exitStage.close();
            }
        });


    }

    /**
     * Creates a new window, which describes the game's rules
     */
    private void helpWindow() {
        Stage helpStage = new Stage();
        helpStage.setTitle("Help window");
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
     * Creates a stage for the game
     * @param gameStage the previous stage
     */
    private void newGame(Stage gameStage) {
        Text[][] fields = new Text[8][8];
        Rectangle[][] rectangles = new Rectangle[8][8];

        StackPane stackPane = new StackPane();
        MenuBar menuBar = new MenuBar();


        Menu menu1 = new Menu("Game");
        MenuItem saveGame = new MenuItem("Save game");
        menu1.getItems().add(saveGame);
        MenuItem exitGame = new MenuItem("Exit game");
        menu1.getItems().add(exitGame);
        menuBar.getMenus().add(menu1);
        Menu helpMenu = new Menu("Help");
        MenuItem help = new MenuItem("Show help");
        helpMenu.getItems().add(help);
        menuBar.getMenus().add(helpMenu);


        saveGame.setOnAction(e -> {
            e.consume();
            game.saveGame();
        });

        exitGame.setOnAction(e -> {
            e.consume();
            exitWindow();
        });

        help.setOnAction(e -> {
            e.consume();
            helpWindow();
        });


        stackPane.getChildren().add(menuBar);
        menuBar.setTranslateY(-290);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fields[i][j] = new Text();
                fields[i][j].setText(String.valueOf(game.getField(i,j)));

                rectangles[i][j] = fieldRectangle();

                stackPane.getChildren().addAll(fields[i][j], rectangles[i][j]);

                fields[i][j].setTranslateX((j - 3.5) * 50);
                fields[i][j].setTranslateY((i - 3.5) * 50);

                rectangles[i][j].setTranslateX((j - 3.5) * 50);
                rectangles[i][j].setTranslateY((i - 3.5) * 50);
                rectangles[i][j].toBack();

            }
        }

        HBox illegalStepHBox = borderedHBox("ILLEGAL STEP");
        HBox winHBox = borderedHBox("YOU WIN!");
        illegalStepHBox.setVisible(false);
        illegalStepHBox.toFront();
        winHBox.setVisible(false);
        winHBox.toFront();
        stackPane.getChildren().addAll(illegalStepHBox, winHBox);
        illegalStepHBox.setAlignment(Pos.CENTER);


        Scene gameScene = new Scene(stackPane, 500, 500);

        gameScene.heightProperty().addListener((obs, oldVal, newVal) -> {
            menuBar.setTranslateY(-gameScene.getHeight()/2+10);
        });

        rectangles[game.getCurrentRow()][game.getCurrentCol()].setFill(Color.CORNFLOWERBLUE);

        FadeTransition fadeOut = transitionInitialize(1.0, 0.0);
        fadeOut.setNode(illegalStepHBox);

        gameScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            int[] previous = new int[2];
            @Override
            public void handle(KeyEvent keyEvent) {
                previous[0] = game.getCurrentRow();
                previous[1] = game.getCurrentCol();
                rectangles[game.getCurrentRow()][game.getCurrentCol()].setFill(Color.TRANSPARENT);
                boolean step = true;
                if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                    game.step("right");
                } else if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                    game.step("left");
                } else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                    game.step("up");
                } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                    game.step("down");
                } else {
                    step = false;
                }

                rectangles[game.getCurrentRow()][game.getCurrentCol()].setFill(Color.CORNFLOWERBLUE);
                if (step) {
                    if (previous[0] == game.getCurrentRow() && previous[1] == game.getCurrentCol()) {
                        illegalStepHBox.setVisible(true);
                        fadeOut.playFromStart();
                    }
                }
            }
        });


        gameStage.setScene(gameScene);
        gameStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
