package game.mechanics;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lombok.extern.slf4j.Slf4j;

/**
 * Class for the game table.
 */
@Slf4j
class Table {

    /**
     * The game table in {@code int} array.
     */
    private int[][] table;
    /**
     * {@link FileOperations} contains json file operations.
     */
    private FileOperations fileOperations;

    /**
     * The constructor of {@link Table}.
     */
    Table(){
        table = new int[8][8];
        fileOperations = new FileOperations();
        uploadTable();
    }

    /**
     * Uploads the table from json file.
     */
    private void uploadTable(){
        try {

            JsonReader jsonReader = fileOperations.CopyFileFromJar("table.json");

            jsonReader.beginObject();
            int row = 0;
            int col = 0;
            while (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                if (jsonReader.peek() == JsonToken.END_ARRAY) {
                    jsonReader.endArray();
                    if (row < table[0].length && col < table[1].length) {
                        throw new IllegalArgumentException("Not enough elements in the " + row + ". row");
                    }
                    row++;
                    col = 0;

                } else if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                    jsonReader.beginArray();


                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                    table[row][col] = jsonReader.nextInt();
                    col++;

                } else if (jsonReader.peek() == JsonToken.NAME) {
                    jsonReader.nextName();

                } else if (jsonReader.peek() == JsonToken.END_OBJECT) {
                    jsonReader.endObject();

                }
            }
            jsonReader.close();
        } catch (Exception e){
            log.error(e.toString());
            uploadDefaultTable();
        }
    }

    /**
     * Loads the default table.
     */
    private void uploadDefaultTable() {
        log.info("Upload default table");
        int[][] table = {
                {6, 2, 7, 4, 2, 4, 4, 2},
                {2, 2, 2, 5, 4, 2, 1, 6},
                {3, 4, 2, 4, 2, 5, 5, 3},
                {4, 5, 4, 3, 2, 2, 3, 4},
                {2, 1, 2, 3, 4, 4, 2, 3},
                {2, 4, 3, 2, 3, 3, 6, 4},
                {6, 4, 2, 3, 3, 2, 6, 3},
                {6, 7, 4, 3, 3, 2, 2, 0}
        };
        this.table = table;
    }

    /**
     * Gives the score of the given field.
     *
     * @param row the field's row
     * @param col the field's column
     * @return the score of the field
     */
    int getField(int row, int col){
        return table[row][col];
    }

    /**
     * Writes out the table on the console.
     */
    void writeOut(){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

}
