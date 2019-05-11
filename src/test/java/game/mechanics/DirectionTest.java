package game.mechanics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testGetOppositeDirection() {
        assertEquals(Direction.LEFT, Direction.GetOppositeDirection(Direction.RIGHT));
        assertEquals(Direction.RIGHT, Direction.GetOppositeDirection(Direction.LEFT));
        assertEquals(Direction.UP, Direction.GetOppositeDirection(Direction.DOWN));
        assertEquals(Direction.DOWN, Direction.GetOppositeDirection(Direction.UP));
        assertThrows(IllegalArgumentException.class, () -> Direction.GetOppositeDirection(null));
    }

    @Test
    void testStringToEnum() {
        String[] directions = {"left", "right", "up", "down"};
        assertEquals(Direction.LEFT, Direction.StringToEnum(directions[0]));
        assertEquals(Direction.LEFT, Direction.StringToEnum(directions[0].toUpperCase()));
        assertEquals(Direction.RIGHT, Direction.StringToEnum(directions[1]));
        assertEquals(Direction.RIGHT, Direction.StringToEnum(directions[1].toUpperCase()));
        assertEquals(Direction.UP, Direction.StringToEnum(directions[2]));
        assertEquals(Direction.UP, Direction.StringToEnum(directions[2].toUpperCase()));
        assertEquals(Direction.DOWN, Direction.StringToEnum(directions[3]));
        assertEquals(Direction.DOWN, Direction.StringToEnum(directions[3].toUpperCase()));

        assertThrows(IllegalArgumentException.class, () -> Direction.StringToEnum("Eh"));
    }
}