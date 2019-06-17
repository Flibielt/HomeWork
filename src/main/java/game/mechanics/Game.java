package game.mechanics;

import game.database.leaderBoard.LeaderBoard;
import lombok.extern.slf4j.Slf4j;

/**
 * Manages the game itself.
 */
@Slf4j
public class Game {
    /**
     * The game {@link Table}.
     */
    private Table table;
    /**
     * The current {@link GameState}.
     */
    private GameState gameState;

    /**
     * Gives the score of the field.
     *
     * @param row is between 0 and 8
     * @param col is between 0 and 8
     * @return the score of the given field
     */
    public int getField(int row, int col) {
        return table.getField(row, col);
    }

    /**
     * Returns the current row.
     *
     * @return the current row
     */
    public int getCurrentRow() {
        return gameState.getRow();
    }

    /**
     * Returns the current column.
     *
     * @return the current column
     */
    public int getCurrentCol() {
        return gameState.getCol();
    }

    /**
     * Returns true if the current field is the goal.
     *
     * @return {@code true} if the current field is 0, else {@code false}
     */
    public boolean isGoal(){
        return getField(getCurrentRow(), getCurrentCol()) == 0;
    }

    /**
     * Puts the player into the leader board
     */
    public void updateLeaderboard() {
        LeaderBoard.getInstance().newPlayer(gameState.getName(), gameState.getSteps());
    }

    /**
     * Steps the player into a specific direction.
     *
     * @param direction the {@link Direction} of the step
     */
    public void step(Direction direction) {
        int distance = getField(getCurrentRow(), getCurrentCol());
        if (!gameState.getUnallowedDirection().equals(direction)) {
            try {
                gameState.updateState(direction, distance);
            } catch (Exception e) {
                log.warn(e.toString());
            }
        }
    }

    /**
     * Loads a previous game.
     */
    public void loadGame() {
        gameState.loadState();
    }

    /**
     * Sets the name of the current player.
     *
     * @param name the name of the player
     */
    public void setName(String name) {
        gameState.setName(name);
    }

    /**
     * Gives the name of the current player.
     *
     * @return the name of the player
     */
    public String getName() {
        return gameState.getName();
    }

    /**
     * Saves the current {@link GameState}.
     */
    public void saveGame() {
        gameState.saveState();
    }

    /**
     * Gives the count of steps the player did so far.
     *
     * @return the count of steps
     */
    public int getSteps() {
        return gameState.getSteps();
    }

    /**
     * Loads a specific {@link GameState}.
     *
     * @param row the row of the player's position
     * @param col the column of the player's position
     * @param notAllowedDirection the {@link Direction} of the previous field
     * @param steps the steps the player did so far
     */
    public void loadSate(int row, int col, Direction notAllowedDirection, int steps) {
        gameState.loadState(row, col, notAllowedDirection, steps);
    }

    /**
     * The constructor of the {@link Game}, creates a new {@link Table}, {@link GameState}.
     */
    public Game() {
        table = new Table();
        gameState = new GameState();
    }

}

