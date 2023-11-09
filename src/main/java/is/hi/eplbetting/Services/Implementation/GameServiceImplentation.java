package is.hi.eplbetting.Services.Implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            if (jsonObject.has("started") && !jsonObject.get("started").isJsonNull()) {
                game.setHasStarted(jsonObject.get("started").getAsBoolean());
                if (jsonObject.has("team_a_score") && !jsonObject.get("team_a_score").isJsonNull()) {
                    game.setAwayTeamScore(jsonObject.get("team_a_score").getAsInt());
                }
                else {
                    game.setAwayTeamScore(0);
                }
                if (jsonObject.has("team_h_score") && !jsonObject.get("team_h_score").isJsonNull()) {
                    game.setHomeTeamScore(jsonObject.get("team_h_score").getAsInt());
                }
                else {
                    game.setHomeTeamScore(0);
                }
                
            }
            else {
                game.setHasStarted(false);
            }
            game.setHasFinished(jsonObject.get("finished").getAsBoolean());
            gameRepository.save(game);

            if (jsonObject.has("kickoff_time") && !jsonObject.get("kickoff_time").isJsonNull()) {
                String kickoffTime = jsonObject.get("kickoff_time").getAsString();

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                Date date;

                try {

                    date = inputFormat.parse(kickoffTime);

                } catch (ParseException e) {

                    e.printStackTrace();
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", Locale.US);
                String formattedDate = dateFormat.format(date);

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
                String formattedTime = timeFormat.format(date);

                game.setDateStr(formattedDate);
                game.setTimeStr(formattedTime);

            }
            else {

                game.setDateStr("TBD");
                game.setTimeStr("TBD");

            }
    
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
        return gameRepository.findById(id);
    }
    
}
