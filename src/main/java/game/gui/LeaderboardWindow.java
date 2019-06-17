package game.gui;

import game.database.leaderBoard.LeaderBoard;
import game.database.leaderBoard.model.Player;
import game.mechanics.Game;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The {@code Stage}, which shows the leader board.
 */
public class LeaderboardWindow {

    /**
     * Shows the leader board.
     *
     * @param game access to the {@link Game} mechanics
     */
    public static void showLeaderboard(Game game) {
        Stage leaderboardStage = new Stage();
        leaderboardStage.setResizable(false);
        leaderboardStage.setTitle("Leaderboard");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        getLeaderboard(game, vBox);

        leaderboardStage.setScene(new Scene(vBox, 250, 350));
        leaderboardStage.show();
    }

    /**
     * Loads the leader board.
     *
     * @param game access to the {@link Game} mechanics
     * @param vBox the {@code VBox} is the root of the current {@code Scene}
     */
    private static void getLeaderboard(Game game, VBox vBox) {

        int size = LeaderBoard.getInstance().getFirst(9).size();

        if (size == 0) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(5);
            Text emptyLeaderboard = new Text("The leaderboard is empty");
            hBox.getChildren().add(emptyLeaderboard);
            vBox.getChildren().add(hBox);
        } else {
            int index = 1;
            for (Player player : LeaderBoard.getInstance().getFirst(9)) {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(5);
                Text position = new Text(index + ". ");
                Text playerName = new Text(player.getName());
                Text playerScore = new Text(Integer.toString(player.getSteps()));
                hBox.getChildren().addAll(position, playerName, playerScore);
                vBox.getChildren().add(hBox);
                index++;
            }
        }

    }
}
