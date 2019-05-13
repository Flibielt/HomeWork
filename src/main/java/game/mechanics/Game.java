package game.mechanics;

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
     * The game's {@link Leaderboard}.
     */
    private Leaderboard leaderboard;

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
    public boolean IsGoal(){
        return getField(getCurrentRow(), getCurrentCol()) == 0;
    }

    /**
     * Puts the player to the {@link Leaderboard}.
     */
    public void updateLeaderboard() {
        leaderboard.newPlayer(gameState.getName(), gameState.getSteps());
        leaderboard.update();
    }

    /**
     * Gives a {@link PlayerFromLeaderboard} from the {@link Leaderboard}.
     *
     * @param index the position in the {@link Leaderboard}
     * @return a {@link PlayerFromLeaderboard}, if the index was valid
     */
    public PlayerFromLeaderboard getPlayerFromLeaderboard(int index) {
        return leaderboard.getPlayer(index);
    }

    /**
     * Gives the count of the records in the {@link Leaderboard}.
     *
     * @return the size of the leader board
     */
    public int getLeaderboardSize() {
        return leaderboard.getLeaderboardSize();
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
                log.error(e.toString());
            }
        }
    }

    /**
     * Loads a previous game.
     */
    public void LoadGame() {
        gameState.LoadState();
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
    public void LoadSate(int row, int col, Direction notAllowedDirection, int steps) {
        gameState.LoadState(row, col, notAllowedDirection, steps);
    }

    /**
     * The constructor of the {@link Game}, creates a new {@link Table}, {@link GameState}, {@link Leaderboard}.
     */
    public Game() {
        table = new Table();
        gameState = new GameState();
        leaderboard = new Leaderboard();
    }

}

