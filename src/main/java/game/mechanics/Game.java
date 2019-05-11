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
    private int[] current;

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
        return getField(current[0], current[1]) == 0;
    }

    public void updateLeaderboard() {
        leaderboard.newPlayer(name, gameState.getSteps());
        leaderboard.update();
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
                log.error("{}, Array index out of bounds", e.toString());
            }
        }
    }

    public void loadGame() {
        gameState.loadState();
        current[0] = gameState.getRow();
        current[1] = gameState.getCol();
        name = gameState.getName();

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void saveGame() {
        gameState.saveState();
    }

    public int getSteps() {
        return gameState.getSteps();
    }




    public Game() {
        table = new Table();
        gameState = new GameState();
        leaderboard = new Leaderboard();
        current = new int[2];
        name = "";
    }


}

