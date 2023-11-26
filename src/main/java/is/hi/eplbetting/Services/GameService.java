package is.hi.eplbetting.Services;

import java.util.List;

import is.hi.eplbetting.Persistence.Entities.Game;

public interface GameService {

    Game createGame(Game game);
    Game getGame(long id);
    List<Game> getGamesList();
    void deleteGame(Game game);
    
}
