package game.mechanics;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

class Leaderboard {

    private LinkedList<Player> leaderboard;

    Leaderboard() {
        leaderboard = new LinkedList<Player>();
        upload();
    }

    /**
     * Uploads the leaderboard from a file
     */
    private void upload() {
        String name = "";
        int steps;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            JsonReader jsonReader = new JsonReader(new FileReader(classLoader.getResource("leaderboard.json").getPath()));

            jsonReader.beginObject();
            jsonReader.nextName();
            jsonReader.beginArray();

            while (jsonReader.peek() == JsonToken.END_DOCUMENT) {
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
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    /**
     * Creates a new player and puts in the leaderboard, if it is good enough
     * @param name the name of the player
     * @param steps the steps the player did to reach the goal
     */
    void newPlayer(String name, int steps) {
        Player player = new Player(name, steps);
        for (int i = 0; i < leaderboard.size(); i++) {
            if (!leaderboard.get(i).equals(player)) {
                if (leaderboard.get(i).isBetterThan(player)) {
                    leaderboard.add(i, player);
                }
            }
        }

        while (leaderboard.size() > 9) {
            leaderboard.remove(10);
        }
    }

    void update() {
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            JsonWriter jsonWriter = new JsonWriter(new FileWriter(classLoader.getResource("state.json").getPath()));

            jsonWriter.beginObject();
            jsonWriter.name("players");
            jsonWriter.beginArray();
            for (Player player : leaderboard) {
                jsonWriter.beginArray();
                jsonWriter.value(player.getName());
                jsonWriter.value(player.getSteps());
                jsonWriter.endArray();
            }
            jsonWriter.endArray();
            jsonWriter.endObject();

            jsonWriter.close();
        } catch (IOException e) {
            System.out.println("IO error");
        } catch (NullPointerException e) {
            System.out.println("Null error");
        }
    }

    Player getPlayer(int position) {
        try {
            return leaderboard.get(position);
        } catch (NullPointerException e) {
            return null;
        }
    }
}

class Player {
    private String name;
    private int steps;

    Player(String name, int steps) {
        this.name = name;
        this.steps = steps;
    }

    /**
     * Returns the player's name
     * @return the player's name
     */
    String getName() {
        return name;
    }

    /**
     * Returns the player's steps
     * @return the steps
     */
    int getSteps() {
        return steps;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }

        Player player = (Player) o;

        return name.equals(player.getName()) && steps == player.steps;
    }

    /**
     * Returns if the current player made the game with fewer steps
     * @param o the other player
     * @return true or false
     */
    boolean isBetterThan(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }

        Player player = (Player) o;
        return steps < player.getSteps();
    }


}
