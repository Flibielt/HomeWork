package game.mechanics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameStateTest {

    private GameState gameState;

    private void assertNoChange(int row, int col, Direction notAllowedDirection, int steps) {
        assertEquals(row, gameState.getRow());
        assertEquals(col, gameState.getCol());
        assertEquals(notAllowedDirection, gameState.getUnallowedDirection());
        assertEquals(steps, gameState.getSteps());
    }

    @BeforeEach
    void setUp() {
        gameState = new GameState();
    }

    @Test
    void testUpdateState() {
        gameState.loadState(0,0,Direction.LEFT, 0);
        assertThrows(IllegalArgumentException.class, () -> gameState.updateState(Direction.LEFT, 3));
        assertNoChange(0, 0, Direction.LEFT, 0);
        assertThrows(IllegalArgumentException.class, () -> gameState.updateState(Direction.UP, 3));
        assertNoChange(0, 0, Direction.LEFT, 0);
        assertThrows(IllegalArgumentException.class, () -> gameState.updateState(Direction.RIGHT, 8));
        assertNoChange(0, 0, Direction.LEFT, 0);
        assertThrows(IllegalArgumentException.class, () -> gameState.updateState(Direction.DOWN, 8));
        assertNoChange(0, 0, Direction.LEFT, 0);

        gameState.updateState(Direction.RIGHT, 3);
        assertEquals(Direction.LEFT, gameState.getUnallowedDirection());
        assertEquals(3, gameState.getCol());
        assertEquals(0, gameState.getRow());
        assertEquals(1, gameState.getSteps());
        gameState.loadState(0, 0, Direction.LEFT, 0);
        gameState.updateState(Direction.DOWN, 3);
        assertEquals(Direction.UP, gameState.getUnallowedDirection());
        assertEquals(0, gameState.getCol());
        assertEquals(3, gameState.getRow());
        assertEquals(1, gameState.getSteps());
    }
}
