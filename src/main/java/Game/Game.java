package Game;

/**
 * Manages the game itself
 */
class Game {
    private Table table;
    private GameState gameState;
    private int[] current;

    /**
     * Gives the score of the field
     * @param row is between 0 and 8
     * @param col is between 0 and 8
     * @return the score of the given field
     */
    int getField(int row, int col) {
        return table.getField(row, col);
    }

    /**
     * Returns the current row
     * @return the current row
     */
    int getCurrentRow() {
        return gameState.getRow();
    }

    /**
     * Returns the current column
     * @return the current column
     */
    int getCurrentCol() {
        return gameState.getCol();
    }

    /**
     * Returns true if the current field is the goal
     * @return true or false
     */
    public boolean isGoal(){
        return getField(current[0], current[1]) == 0;
    }

    /**
     * Steps the player into a specific direction
     * @param direction is where the player steps
     */
    void step(String direction) {
        int distance = getField(getCurrentRow(), getCurrentCol());
        if (!gameState.getPreviousField().equalsIgnoreCase(direction)) {
            try {
                gameState.updateState(direction, distance);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array index out of bounds");
            }
        }
    }

    void loadGame() {
        gameState.loadState();
        current[0] = gameState.getRow();
        current[1] = gameState.getCol();
    }

    Game() {
        table = new Table();
        gameState = new GameState();
        current = new int[2];
    }


}

