package game.database.leaderBoard;

import game.database.jpa.GenericJpaDao;
import game.database.leaderBoard.model.Player;

import java.util.List;

public class PlayerDAO extends GenericJpaDao<Player> {

    public PlayerDAO() {
        super(Player.class);
    }

    /**
     * Gets the leader board from the database.
     *
     * @return the leader board in a list
     */
    private List<Player> getAllElements() {
        return entityManager.createQuery("select p from Player p order by p.steps", Player.class).getResultList();
    }

    /**
     * Gets the first n player from the leader board
     * @param players the length of the new list
     * @return the first {@code players} length list
     */
    List<Player> getFirst(int players) {
        int size = getAllElements().size();
        return getAllElements().subList(0, players > size ? size : players);
    }
}
