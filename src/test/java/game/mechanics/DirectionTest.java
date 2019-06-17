package game.mechanics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectionTest {

    @Test
    void testGetOppositeDirection() {
        assertEquals(Direction.LEFT, Direction.getOppositeDirection(Direction.RIGHT));
        assertEquals(Direction.RIGHT, Direction.getOppositeDirection(Direction.LEFT));
        assertEquals(Direction.UP, Direction.getOppositeDirection(Direction.DOWN));
        assertEquals(Direction.DOWN, Direction.getOppositeDirection(Direction.UP));
        assertThrows(IllegalArgumentException.class, () -> Direction.getOppositeDirection(null));
    }

    @Test
    void testStringToEnum() {
        String[] directions = {"left", "right", "up", "down"};
        assertEquals(Direction.LEFT, Direction.stringToEnum(directions[0]));
        assertEquals(Direction.LEFT, Direction.stringToEnum(directions[0].toUpperCase()));
        assertEquals(Direction.RIGHT, Direction.stringToEnum(directions[1]));
        assertEquals(Direction.RIGHT, Direction.stringToEnum(directions[1].toUpperCase()));
        assertEquals(Direction.UP, Direction.stringToEnum(directions[2]));
        assertEquals(Direction.UP, Direction.stringToEnum(directions[2].toUpperCase()));
        assertEquals(Direction.DOWN, Direction.stringToEnum(directions[3]));
        assertEquals(Direction.DOWN, Direction.stringToEnum(directions[3].toUpperCase()));

        assertThrows(IllegalArgumentException.class, () -> Direction.stringToEnum("Eh"));
    }
}