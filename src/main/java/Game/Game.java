package Game;

/**
 * Manages the game itself
 */
public class Game {
    private Table table;
    private GameState gameState;

    /**
     * Gives the score of the field
     * @return the score of the current field
     */
    private int getField() {
        return table.getField(gameState.getRow(), gameState.getCol());
    }

    /**
     * Returns true if the current field is the goal
     * @return true or false
     */
    private boolean isGoal(){
        return getField() == 0;
    }

    /**
     * Steps the player into a specific direction
     * @param direction is where the player steps
     */
    private void step(String direction){
        int distance = getField();
        if (!gameState.getPreviousField().equals(direction)) {
            try {
                gameState.updateState(direction, distance);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array index out of bounds");
            }
        }
    }

    private Game(){
        table = new Table();
        gameState = new GameState();
    }

    public static void main(String[] args) {
        Game game = new Game();

        while (!game.isGoal()){
            System.out.println("Current: (" + game.gameState.getRow() + ", " + game.gameState.getCol() + ")");
            game.table.writeOut();

            System.out.print("Direction: ");
            String direction = System.console().readLine();
            game.step(direction);
        }
    }
}

