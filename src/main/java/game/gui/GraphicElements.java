package game.gui;

import javafx.animation.FadeTransition;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Contains some graphic elements for the {@link GameWindow}.
 */
public class GraphicElements {

    /**
     * Creates a {@code HBox} with red border which contains a {@code Text}.
     *
     * @param text the value of the {@code Text}
     * @return the {@code HBox}
     */
    public static HBox borderedHBox(String text) {
        Text text1 = new Text();
        text1.setText(text);
        text1.setFont(Font.font("verdana", FontWeight.BOLD, 20));

        HBox hBox = new HBox();
        hBox.getChildren().add(text1);
        hBox.setStyle("-fx-background-color: white;" +
                " -fx-border-color: red;" +
                " -fx-border-width: 5px;" +
                "-fx-padding: 3px;"
        );
        hBox.setMaxSize(text1.getScaleX(), text1.getScaleY());
        return hBox;
    }

    /**
     * Creates a {@code Rectangle} which represents a field of the table.
     *
     * @return {@code Rectangle} with border
     */
    public static Rectangle fieldRectangle() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(50);
        rectangle.setHeight(50);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.toBack();
        return rectangle;
    }

    /**
     * Creates a new {@code FadeTransition} that will be used in {@link GameWindow}.
     *
     * @param from the starting point of the animation
     * @param to the end point of the animation
     * @return a {@code FadeTransition}
     */
    public static FadeTransition transitionInitialize(double from, double to) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1250));
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        return fadeTransition;
    }
}
