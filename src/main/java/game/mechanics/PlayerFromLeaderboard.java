package game.mechanics;

/**
 * A player in the leader board.
 */
public class PlayerFromLeaderboard {

    private String name;
    private int steps;

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
     * @return the steps
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
     * @return true or false
     */
    boolean isBetterThan(PlayerFromLeaderboard playerFromLeaderboard) {
        return steps <= playerFromLeaderboard.getSteps();
    }
}
