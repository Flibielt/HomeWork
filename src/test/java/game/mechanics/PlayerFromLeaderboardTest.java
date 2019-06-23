package game.mechanics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFromLeaderboardTest {

    private PlayerFromLeaderboard player1;
    private PlayerFromLeaderboard player2;
    private PlayerFromLeaderboard player3;

    @BeforeEach
    void setUp() {
        player1 = new PlayerFromLeaderboard("best", 100);
        player2 = new PlayerFromLeaderboard("good", 200);
        player3 = new PlayerFromLeaderboard("weak", 300);
    }

    @Test
    void testEquals1() {
        assertNotEquals(player1, player2);
        assertNotEquals(player1, player3);
        assertNotEquals(player2, player3);

        assertNotEquals(player1, new PlayerFromLeaderboard("best", 101));
        assertNotEquals(player1, new PlayerFromLeaderboard("not best", 101));
        assertEquals(player1, new PlayerFromLeaderboard("best", 100));
    }

    @Test
    void testIsBetterThan() {
        assertTrue(player1.isBetterThan(player2));
        assertTrue(player1.isBetterThan(player3));
        assertTrue(player2.isBetterThan(player3));

        assertFalse(player3.isBetterThan(player1));
        assertFalse(player3.isBetterThan(player2));
        assertFalse(player2.isBetterThan(player1));
    }
}