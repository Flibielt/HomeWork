package game.mechanics;

import lombok.extern.slf4j.Slf4j;

/**
 * Manages the game itself
 */
@Slf4j
public class Game {
    private Table table;
    private GameState gameState;
    private Leaderboard leaderboard;
    private String name;

    /**
     * Gives the score of the field
     * @param row is between 0 and 8
     * @param col is between 0 and 8
     * @return the score of the given field
     */
    public int getField(int row, int col) {
        return table.getField(row, col);
    }

    /**
     * Returns the current row
     * @return the current row
     */
    public int getCurrentRow() {
        return gameState.getRow();
    }

    /**
     * Returns the current column
     * @return the current column
     */
    public int getCurrentCol() {
        return gameState.getCol();
    }

    /**
     * Returns true if the current field is the goal
     * @return true or false
     */
    public boolean isGoal(){
        return getField(getCurrentRow(), getCurrentCol()) == 0;
    }

    public void updateLeaderboard() {
        leaderboard.newPlayer(name, gameState.getSteps());
        leaderboard.update();
    }

    /**
     * Gives a player from the leader board
     * @param index the position in the leader board
     * @return a {@code PlayerFromLeaderboard}, if the position was valid
     */
    public PlayerFromLeaderboard getPlayerFromLeaderboard(int index) {
        return leaderboard.getPlayer(index);
    }

    /**
     * Gives the count of the records in the leader board
     * @return the size of the leader board
     */
    public int getLeaderboardSize() {
        return leaderboard.getLeaderboardSize();
    }

    /**
     * Steps the player into a specific direction
     * @param direction is where the player steps
     */
    public void step(Direction direction) {
        int distance = getField(getCurrentRow(), getCurrentCol());
        if (!gameState.getUnallowedDirection().equals(direction)) {
            try {
                gameState.updateState(direction, distance);
            } catch (ArrayIndexOutOfBoundsException e) {
                log.error(e.toString());
            }
        }
    }

    /**
     * Loads a previous game
     */
    public void loadGame() {
        gameState.loadState();
        name = gameState.getName();
    }

    /**
     * Sets the name of the current player
     * @param name the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gives the name of the current player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Saves the current game state
     */
    public void saveGame() {
        gameState.saveState();
    }

    /**
     * Gives the count of steps the player did so far
     * @return the count of steps
     */
    public int getSteps() {
        return gameState.getSteps();
    }

    public Game() {
        table = new Table();
        gameState = new GameState();
        leaderboard = new Leaderboard();
        name = "";
    }

}

