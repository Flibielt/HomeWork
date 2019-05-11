package game.mechanics;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

@Slf4j
class Leaderboard {

    private LinkedList<PlayerFromLeaderboard> leaderboard;

    Leaderboard() {
        leaderboard = new LinkedList<PlayerFromLeaderboard>();
        upload();
    }

    /**
     * Uploads the leader board from a file
     */
    private void upload() {
        String name = "";
        int steps = 0;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            JsonReader jsonReader = new JsonReader(new FileReader(classLoader.getResource("leaderboard.json").getPath()));

            jsonReader.beginObject();
            jsonReader.nextName();
            jsonReader.beginArray();

            while (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                if (jsonReader.peek() == JsonToken.END_ARRAY) {
                    jsonReader.endArray();
                } else if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                    jsonReader.beginArray();
                } else if (jsonReader.peek() == JsonToken.STRING) {
                    name = jsonReader.nextString();
                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                    steps = jsonReader.nextInt();
                    newPlayer(name, steps);
                } else if (jsonReader.peek() == JsonToken.END_OBJECT) {
                    jsonReader.endObject();
                }
            }

            jsonReader.close();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * Creates a new player and puts in the leaderboard, if it is good enough
     * @param name the name of the player
     * @param steps the steps the player did to reach the goal
     */
    void newPlayer(String name, int steps) {
        PlayerFromLeaderboard playerFromLeaderboard = new PlayerFromLeaderboard(name, steps);
        boolean added = false;
        for (int i = 0; i < leaderboard.size(); i++) {
            if (!leaderboard.get(i).isBetterThan(playerFromLeaderboard)) {
                leaderboard.add(i, playerFromLeaderboard);
                added = true;
                break;
            }
        }
        if (!added) {
            leaderboard.addLast(playerFromLeaderboard);
        }

        try {
            leaderboard.getFirst();
        } catch (Exception e) {
            leaderboard.addFirst(playerFromLeaderboard);
        }

        while (leaderboard.size() > 10) {
            leaderboard.remove(10);
        }
    }

    /**
     * Writes the updated leader board into leaderboard.json
     */
    void update() {
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            JsonWriter jsonWriter = new JsonWriter(new FileWriter(classLoader.getResource("leaderboard.json").getPath()));

            jsonWriter.beginObject();
            jsonWriter.name("players");
            jsonWriter.beginArray();
            for (PlayerFromLeaderboard playerFromLeaderboard : leaderboard) {
                jsonWriter.beginArray();
                jsonWriter.value(playerFromLeaderboard.getName());
                jsonWriter.value(playerFromLeaderboard.getSteps());
                jsonWriter.endArray();
            }
            jsonWriter.endArray();
            jsonWriter.endObject();

            jsonWriter.close();
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    /**
     * Gives a player from the leader board by index
     * @param position the position in the leader board
     * @return a player from the leader board
     */
    public PlayerFromLeaderboard getPlayer(int position) {
        try {
            return leaderboard.get(position);
        } catch (Exception e) {
            log.error(e.toString());
            throw new IllegalArgumentException();
        }
    }

    /**
     * Gives the count of the players in the leader board
     * @return the size of the leader board
     */
    int getLeaderboardSize() {
        return leaderboard.size();
    }
}

