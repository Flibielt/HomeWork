package game.mechanics;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Manages the game state.
 */
@Slf4j
class GameState {
    /**
     * The current position of the player.
     */
    private int[] index;
    /**
     * The player cannot step into this {@link Direction}.
     */
    private Direction unallowedDirection;
    /**
     * The name of the player.
     */
    private String name;
    /**
     * The count of steps the player did so far.
     */
    private int steps;
    /**
     * {@link FileOperations} contains json file operations.
     */
    private FileOperations fileOperations;

    /**
     * Logs the player's current and position and the direction the player stepped.
     *
     * @param direction the {@link Direction} of the player's step
     */
    private void logStep(Direction direction) {
        log.info("PlayerFromLeaderboard's position: ({}, {})", index[0], index[1]);
        log.info("PlayerFromLeaderboard stepped to {}", direction);
    }

    /**
     * The constructor of {@link GameState}.
     */
    GameState(){
        index = new int[2];
        unallowedDirection = Direction.LEFT;
        steps = 0;
        fileOperations = new FileOperations();
    }

    /**
     * Returns the current row.
     *
     * @return the current row
     */
    int getRow(){return index[0];}

    /**
     * Returns the current column.
     *
     * @return the current column
     */
    int getCol(){return index[1];}

    /**
     * Sets the name of the current player.
     *
     * @param name the player's name
     */
    void setName(String name) {
        this.name = name;
    }
    /**
     * Gives the name of the current player.
     *
     * @return the name of the current player
     */
    String getName() {
        return name;
    }

    /**
     * Gives the count of steps the player did so far.
     *
     * @return the count of steps
     */
    int getSteps() {
        return steps;
    }

    /**
     * Modifies the current row.
     *
     * @param distance the added score
     * @param direction the {@link Direction} of the step
     */
    private void setRow(int distance, Direction direction){
        if (index[0] + distance < 8 && index[0] + distance >= 0) {
            index[0] += distance;
            unallowedDirection = Direction.GetOppositeDirection(direction);
            steps++;
            logStep(direction);
        } else {
            throw new IllegalArgumentException("You cannot step there");
        }
    }

    /**
     * Modifies the current column.
     *
     * @param distance the added score
     * @param direction the {@link Direction} of the step
     */
    private void setCol(int distance, Direction direction){
        if (index[1] + distance < 8 && index[1] + distance >= 0) {
            index[1] += distance;
            unallowedDirection = Direction.GetOppositeDirection(direction);
            steps++;
            logStep(direction);
        } else {
            throw new IllegalArgumentException("You cannot step there");
        }
    }

    /**
     * Gives the direction of the previous field.
     *
     * @return the opposite {@link Direction} of the previous step
     */
    Direction getUnallowedDirection() {
        return unallowedDirection;
    }

    /**
     * Updates the game state.
     *
     * @param direction the {@link Direction} of the step
     * @param distance the length of the step
     */
    void updateState(Direction direction, int distance) {
        switch (direction) {
            case UP:
                setRow(-distance, direction);
                break;
            case DOWN:
                setRow(distance, direction);
                break;
            case LEFT:
                setCol(-distance, direction);
                break;
            case RIGHT:
                setCol(distance, direction);
                break;
        }
    }

    /**
     * Writes the current game state into a file.
     */
    void saveState() {
        try {

            JsonWriter jsonWriter = fileOperations.CreateJsonWriter("state.json");

            jsonWriter.beginObject();

            jsonWriter.name("index");
            jsonWriter.beginArray();
            for (int value : index) {
                jsonWriter.value(value);
            }
            jsonWriter.endArray();

            jsonWriter.name("unallowedDirection");
            jsonWriter.value(unallowedDirection.toString());

            jsonWriter.name("name");
            jsonWriter.value(name);

            jsonWriter.name("steps");
            jsonWriter.value(steps);

            jsonWriter.endObject();
            jsonWriter.close();
            log.info("State saved");

        } catch (IOException e) {
            log.error("{}, IOException error", e.toString());
        } catch (NullPointerException e) {
            log.error("{}, Null Pointer Exception", e.toString());
        }
    }

    /**
     * Loads the game state of a previous game from a file.
     */
    void LoadState() {
        try {

            JsonReader jsonReader = fileOperations.CopyFileFromJar("state.json");

            jsonReader.beginObject();
            jsonReader.nextName();
            jsonReader.beginArray();
            for (int i = 0; i < 2; i++) {
                index[i] = jsonReader.nextInt();
                if (index[i] > 7 || index[i] < 0) {
                    throw new IllegalArgumentException();
                }
            }
            jsonReader.endArray();

            jsonReader.nextName();

            unallowedDirection = Direction.StringToEnum(jsonReader.nextString());

            jsonReader.nextName();
            name = jsonReader.nextString();

            jsonReader.nextName();
            steps = jsonReader.nextInt();

            jsonReader.endObject();
            jsonReader.close();
        } catch (Exception e) {
            loadDefaultGameState();
            log.error(e.toString());
        }

        if (name.equals("")) {
            loadDefaultGameState();
        }
    }

    /**
     * Loads a specific game state.
     *
     * @param row the row of the player's position
     * @param col the column of the player's position
     * @param notAllowedDirection the {@link Direction} of the previous field
     * @param steps the steps the player did so far
     */
    void LoadState(int row, int col, Direction notAllowedDirection, int steps) {
        try {
            if (row > 7 || row < 0) {
                loadDefaultGameState();
                throw new IllegalArgumentException();
            } else if (col > 7 || col < 0) {
                loadDefaultGameState();
                throw new IllegalArgumentException();
            } else if (steps < 0) {
                loadDefaultGameState();
                throw new IllegalArgumentException();
            } else {
                index[0] = row;
                index[1] = col;
                unallowedDirection = notAllowedDirection;
                this.steps = steps;
                log.info("The player's position is set to ({}, {}), not allowed direction: {}, steps: {}", row, col, notAllowedDirection, steps);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * Loads the current default game state.
     */
    private void loadDefaultGameState() {
        name = "";
        index[0] = index[1] = 0;
        unallowedDirection = Direction.LEFT;
        steps = 0;
    }
}
