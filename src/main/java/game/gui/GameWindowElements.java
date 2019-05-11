package game.gui;

import game.mechanics.Game;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static game.gui.GraphicElements.fieldRectangle;

/**
 * The main elements of the {@link game.gui.GameWindow}
 */
public class GameWindowElements {

    private static Rectangle[][] rectangles;

    /**
     * Creates the menu for the {@link game.gui.GameWindow}
     *
     * @param game the game mechanics
     * @param stackPane the {@code StackPane} of the {@link game.gui.GameWindow}
     */
    public static void CreateMenu(Game game, StackPane stackPane) {
        MenuBar menuBar = new MenuBar();

        Menu menu1 = new javafx.scene.control.Menu("Game");
        MenuItem saveGame = new MenuItem("Save game");
        menu1.getItems().add(saveGame);
        MenuItem exitGame = new MenuItem("Exit game");
        menu1.getItems().add(exitGame);
        menuBar.getMenus().add(menu1);
        javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu("Help");
        MenuItem help = new MenuItem("Show help");
        helpMenu.getItems().add(help);
        menuBar.getMenus().add(helpMenu);


        saveGame.setOnAction(e -> {
            e.consume();
            game.saveGame();
        });

        exitGame.setOnAction(e -> {
            e.consume();
            ExtraWindows.ExitWindow(game);
        });

        help.setOnAction(e -> {
            e.consume();
            ExtraWindows.HelpWindow();
        });

        stackPane.getChildren().add(menuBar);
        menuBar.setTranslateY(-240);
    }

    /**
     * Creates the fields for the {@link game.gui.GameWindow}
     *
     * @param game the game mechanics
     * @param stackPane the {@code StackPane} of the {@link game.gui.GameWindow}
     */
    public static void CreateFields(Game game, StackPane stackPane) {
        Text[][] fields = new Text[8][8];
        rectangles = new Rectangle[8][8];

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
    }

    /**
     * Changes a field in the {@link game.gui.GameWindow}
     *
     * @param row the row of the field
     * @param col the column of the field
     * @param color the new color of the field
     */
    public static void ChangeRectangleColor(int row, int col, Color color) {
        rectangles[row][col].setFill(color);
    }
}
