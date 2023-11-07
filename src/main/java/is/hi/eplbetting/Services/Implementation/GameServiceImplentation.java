package is.hi.eplbetting.Services.Implementation;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import is.hi.eplbetting.Persistence.Entities.Game;
import is.hi.eplbetting.Persistence.Repositories.GameRepository;
import is.hi.eplbetting.Services.GameService;

@Service
public class GameServiceImplentation implements GameService{
    private GameRepository gameRepository;

    public GameServiceImplentation(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

        String[] teams = {"ARS", "AVL", "BOU", "BRE", "BHA", "BUR", "CHE", "CRY", "EVE", "FUL", "LIV", "LUT", "MCI", "MUN", "NEW", "NFO", "SHU", "TOT", "WHU", "WOL"};

        JsonArray fixtures = getFixtures();

        for (int i = 0; i  < fixtures.size(); i++) {
            JsonObject jsonObject = fixtures.get(i).getAsJsonObject();
            Game game = new Game();
            
            game.setHomeTeam(teams[jsonObject.get("team_h").getAsInt() - 1]);
            game.setAwayTeam(teams[jsonObject.get("team_a").getAsInt() - 1]);
            game.setHasElapsed(jsonObject.get("finished").getAsBoolean());
            gameRepository.save(game);
        }
    }

    public JsonArray getFixtures() {
        String apiUrl = "https://fantasy.premierleague.com/api/fixtures/";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            String responseBody;
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    responseBody = EntityUtils.toString(response.getEntity());
                } else {
                    return null;
                }
            }
            return JsonParser.parseString(responseBody).getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
