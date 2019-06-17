package game.database.guice;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;

public class JpaInitializer {

    @Inject
    public JpaInitializer (PersistService persistService) {
        persistService.start();
    }
}
