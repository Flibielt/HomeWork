package game.mechanics;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Direction GetOppositeDirection(Direction direction) {
        if (direction == LEFT) {
            return RIGHT;
        } else if (direction == RIGHT) {
            return LEFT;
        } else if (direction == UP) {
            return DOWN;
        } else if (direction == DOWN) {
            return UP;
        } else {
            return null;
        }
    }

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
        return null;
    }
}
