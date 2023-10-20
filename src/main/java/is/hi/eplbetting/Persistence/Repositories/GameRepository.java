package is.hi.eplbetting.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.eplbetting.Persistence.Entities.Game;

public interface GameRepository extends JpaRepository<Game, Long>{

    Game save(Game game);
    void delete(Game game);
    List<Game> findAll();
    Game findById(long id);
    
    
}
