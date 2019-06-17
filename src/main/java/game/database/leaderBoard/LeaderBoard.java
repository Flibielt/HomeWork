package game.database.leaderBoard;

import com.google.inject.Guice;
import com.google.inject.Injector;
import game.database.guice.PersistenceModule;
import game.database.leaderBoard.model.Player;

import java.util.List;

public class LeaderBoard {

    private static LeaderBoard leaderBoardInstance = null;

    private PlayerDAO playerDAO;

    private LeaderBoard() {
        Injector injector = Guice.createInjector(new PersistenceModule("test"));
        playerDAO = injector.getInstance(PlayerDAO.class);
    }

    public static LeaderBoard getInstance() {
        if (leaderBoardInstance == null) {
            leaderBoardInstance = new LeaderBoard();
        }
        return leaderBoardInstance;
    }

    public void newPlayer(String name, int steps) {
        Player player = Player.builder()
                .name(name)
                .steps(steps)
                .build();

        playerDAO.persist(player);
    }

    public List<Player> getFirst(int players) {
        return playerDAO.getFirst(players);
    }

    
}
