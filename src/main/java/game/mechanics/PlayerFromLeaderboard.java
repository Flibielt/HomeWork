package game.mechanics;

/**
 * A player in the {@link Leaderboard}.
 */
public class PlayerFromLeaderboard {

    /**
     * The name of the player.
     */
    private String name;
    /**
     * The count of steps the player did to reach the goal.
     */
    private int steps;

    /**
     * The constructor of {@link PlayerFromLeaderboard}.
     *
     * @param name the name of the player
     * @param steps the count of steps the player did to reach the goal
     */
    PlayerFromLeaderboard(String name, int steps) {
        this.name = name;
        this.steps = steps;
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the count of the player's steps.
     *
     * @return the count of steps
     */
    public int getSteps() {
        return steps;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerFromLeaderboard)) {
            return false;
        }

        PlayerFromLeaderboard playerFromLeaderboard = (PlayerFromLeaderboard) o;

        return name.equals(playerFromLeaderboard.getName()) && steps == playerFromLeaderboard.steps;
    }

    /**
     * Returns if the current player made the game with fewer steps.
     *
     * @param playerFromLeaderboard the other player
     * @return {@code true} if the current player was better, else {@code false}
     */
    boolean isBetterThan(PlayerFromLeaderboard playerFromLeaderboard) {
        return steps <= playerFromLeaderboard.getSteps();
    }
}
