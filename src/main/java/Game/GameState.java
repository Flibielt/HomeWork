package Game;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages the game state
 */
class GameState {
    private int[] index;
    private direction previousField;

    enum direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    GameState(){
        index = new int[2];
        previousField = direction.LEFT;
    }

    /**
     * Returns the current row
     * @return the current row
     */
    int getRow(){return index[0];}

    /**
     * Returns the current column
     * @return the current column
     */
    int getCol(){return index[1];}

    /**
     * Modifies the current row
     * @param distance the added score
     * @return true if successful
     */
    private boolean setRow(int distance){
        if (index[0] + distance < 8 && index[0] + distance >= 0) {
            index[0] += distance;
            return true;
        }
        return false;
    }

    /**
     * Modifies the current column
     * @param distance the added score
     * @return true if successful
     */
    private boolean setCol(int distance){
        if (index[1] + distance < 8 && index[1] + distance >= 0) {
            index[1] += distance;
            return true;
        }
        return false;
    }

    /**
     * Gives the direction of the previous field
     * @return the opposite direction of the previous step
     */
    String getPreviousField() {
        return previousField.toString();
    }

    /**
     * Updates the game state
     * @param previous the direction of the step
     * @param distance the length of the step
     */
    void updateState(String previous, int distance) {
        switch (previous.toUpperCase()) {
            case "UP":
                if (setRow(-distance)) {
                    previousField = direction.DOWN;
                }
                break;
            case "DOWN":
                if (setRow(distance)) {
                    previousField = direction.UP;
                }
                break;
            case "LEFT":
                if (setCol(-distance)) {
                    previousField = direction.RIGHT;
                }
                break;
            case "RIGHT":
                if (setCol(distance)) {
                    previousField = direction.LEFT;
                }
                break;
        }
    }

    /**
     * Writes the current game state into a file
     */
    void saveState() {
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            JsonWriter jsonWriter = new JsonWriter(new FileWriter(classLoader.getResource("state.json").getPath()));

            jsonWriter.beginObject();

            jsonWriter.name("index");
            jsonWriter.beginArray();
            for (int value : index) {
                jsonWriter.value(value);
            }
            jsonWriter.endArray();

            jsonWriter.name("previousField");
            jsonWriter.value(previousField.toString());

            jsonWriter.endObject();
            jsonWriter.close();

        } catch (IOException e) {
            System.out.println("IOException error");
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception");
        }
    }

    /**
     * Loads the game state of a previous game from a file
     */
    void loadState() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            JsonReader jsonReader = new JsonReader(new FileReader(classLoader.getResource("table.json").getPath()));

            jsonReader.beginObject();
            jsonReader.nextName();
            jsonReader.beginArray();
            for (int i = 0; i < 2; i++) {
                index[i] = jsonReader.nextInt();
            }
            jsonReader.endArray();

            jsonReader.nextName();
            switch (jsonReader.nextString()) {
                case "UP":
                    previousField = direction.UP;
                    break;
                case "DOWN":
                    previousField = direction.DOWN;
                    break;
                case "LEFT":
                    previousField = direction.LEFT;
                    break;
                case "RIGHT":
                    previousField = direction.RIGHT;
                    break;
            }

            jsonReader.endObject();
            jsonReader.close();
        } catch (IOException e) {
            System.out.println("IOException error");
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception error");
        }
    }
}
