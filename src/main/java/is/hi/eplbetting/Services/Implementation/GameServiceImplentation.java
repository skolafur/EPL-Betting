package is.hi.eplbetting.Services.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import is.hi.eplbetting.Persistence.Entities.Game;
import is.hi.eplbetting.Persistence.Repositories.GameRepository;
import is.hi.eplbetting.Services.GameService;

@Service
public class GameServiceImplentation implements GameService{
    private GameRepository gameRepository;

    public GameServiceImplentation(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

        String[] teams = {"ARS", "AVL", "BOU", "BRE", "BHA", "BUR", "CHE", "CRY", "EVE", "FUL", "LIV", "LUT", "MCI", "MUN", "NEW", "NFO", "SHU", "TOT", "WHU", "WOL"};

        String url_fixtures = "https://fantasy.premierleague.com/api/fixtures/";

        WebClient.Builder builder = WebClient.builder();

        String fixturesString = builder.build().get().uri(url_fixtures).retrieve().bodyToMono(String.class).block();

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(fixturesString, JsonArray.class);

        for (int i = 0; i  < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            Game game = new Game();
            
            game.setHomeTeam(teams[jsonObject.get("team_h").getAsInt() - 1]);
            game.setAwayTeam(teams[jsonObject.get("team_a").getAsInt() - 1]);
            game.setHasElapsed(jsonObject.get("finished").getAsBoolean());
            gameRepository.save(game);
            //System.out.println(teams[jsonObject.get("team_h").getAsInt() - 1]);
        }
    }

    @Override
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(Game game) {
       gameRepository.delete(game);
        
    }

    @Override
    public String getGameInfo(long id) {

        return null;
    }

    @Override
    public List<Game> getGamesList() {
        return gameRepository.findAll();
    }

    @Override
    public Game getGame(long id) {
        return gameRepository.getById(id);
    }
    
}
