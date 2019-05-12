package game.mechanics;

/**
 * Enum representing the four possible direction.
 */
public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    /**
     * Gives back the opposite of the given direction.
     *
     * @param direction the direction to be reversed
     * @return the opposite of the given direction
     */
    public static Direction GetOppositeDirection(Direction direction) {
        if (direction == LEFT) {
            return RIGHT;
        } else if (direction == RIGHT) {
            return LEFT;
        } else if (direction == UP) {
            return DOWN;
        } else if (direction == DOWN) {
            return UP;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Converts a string to {@code Direction} if possible.
     *
     * @param direction a possible direction in {@code String}
     * @return a {@code @Direction} if the argument was a valid direction
     */
    public static Direction StringToEnum(String direction) {
        switch (direction.toUpperCase()) {
            case "LEFT":
                return LEFT;
            case "RIGHT":
                return RIGHT;
            case "UP":
                return UP;
            case "DOWN":
                return DOWN;
        }
        throw new IllegalArgumentException();
    }
}
