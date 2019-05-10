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
        assertNull(Direction.GetOppositeDirection(null));
    }

    @Test
    void testStringToEnum() {
        assertEquals(Direction.LEFT, Direction.StringToEnum("LEFT"));
        assertEquals(Direction.RIGHT, Direction.StringToEnum("RIGHT"));
        assertEquals(Direction.RIGHT, Direction.StringToEnum("right"));

    }
}