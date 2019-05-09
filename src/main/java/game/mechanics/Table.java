package game.mechanics;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;

/**
 * Class for the game table
 */
class Table {

    private int[][] table;

    Table(){
        table = new int[8][8];
        uploadTable();
    }

    /**
     * Uploads the table from json file
     */
    private void uploadTable(){
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            JsonReader jsonReader = new JsonReader(new FileReader(classLoader.getResource("table.json").getPath()));

            jsonReader.beginObject();
            int row = 0;
            int col = 0;
            while (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                if (jsonReader.peek() == JsonToken.END_ARRAY) {
                    jsonReader.endArray();
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
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (NullPointerException e){
            System.out.println("Null pointer exception");
        } catch (IOException e){
            System.out.println("IOException error");
        }
    }

    /**
     * Gives the score of the given field
     * @param row the field's row
     * @param col the field's column
     * @return the score of the field
     */
    int getField(int row, int col){
        return table[row][col];
    }

    /**
     * Writes out the table on the console
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
