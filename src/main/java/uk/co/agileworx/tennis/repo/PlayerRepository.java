package uk.co.agileworx.tennis.repo;

import org.springframework.data.repository.CrudRepository;
import uk.co.agileworx.tennis.model.Player;

/**
 * Created by conor on 10/04/2017.
 */
public interface PlayerRepository extends CrudRepository<Player, String> {
}
