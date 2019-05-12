package game.mechanics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    private void assertDefaultState() {
        assertEquals(0, game.getCurrentRow());
        assertEquals(0, game.getCurrentCol());
        assertEquals(0, game.getSteps());
    }

    @Test
    void testLoadState() {
        Direction[] allDirections = {Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.DOWN};
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int d = 0; d < 4; d++) {
                    game.LoadSate(i, j, allDirections[d], 0);
                    assertEquals(i, game.getCurrentRow());
                    assertEquals(j, game.getCurrentCol());
                }
            }
        }

        game.LoadSate(-1, 0, Direction.RIGHT, 0);
        assertDefaultState();
        game.LoadSate(8, 0, Direction.RIGHT, 0);
        assertDefaultState();
        game.LoadSate(0, -1, Direction.RIGHT, 0);
        assertDefaultState();
        game.LoadSate(0, 8, Direction.RIGHT, 0);
        assertDefaultState();
        game.LoadSate(0, 0, Direction.RIGHT, -1);
        assertDefaultState();
    }

    @Test
    void testStep() {
        game.LoadSate(0,0,Direction.LEFT, 0);
        game.step(Direction.LEFT);
        assertEquals(0, game.getSteps());
        game.step(Direction.UP);
        assertEquals(0, game.getSteps());
        game.step(Direction.RIGHT);
        assertEquals(1, game.getSteps());
        assertEquals(0, game.getCurrentRow());
        assertEquals(6, game.getCurrentCol());
        game.LoadSate(0, 0, Direction.LEFT, 0);
        game.step(Direction.DOWN);
        assertEquals(1, game.getSteps());
        assertEquals(6, game.getCurrentRow());
        assertEquals(0, game.getCurrentCol());
    }

    @Test
    void testIsGoal() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                game.LoadSate(i, j, Direction.RIGHT, 10);
                assertFalse(game.IsGoal());
            }
        }

        for (int j = 0; j < 7; j++) {
            game.LoadSate(7, j, Direction.RIGHT, 10);
            assertFalse(game.IsGoal());
        }

        game.LoadSate(7,7, Direction.RIGHT, 10);
        assertTrue(game.IsGoal());
    }
}