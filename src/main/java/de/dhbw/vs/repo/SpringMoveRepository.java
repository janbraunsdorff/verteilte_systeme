package de.dhbw.vs.repo;

import de.dhbw.vs.domain.game.logic.Move;
import org.springframework.data.repository.CrudRepository;

public interface SpringMoveRepository extends CrudRepository<Move, byte[] > {
}
