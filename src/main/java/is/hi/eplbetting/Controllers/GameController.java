package is.hi.eplbetting.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.eplbetting.Persistence.Entities.Game;
import is.hi.eplbetting.Services.GameService;


@Controller
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping("/gameslist")
    public String gamesPage(Model model) {

        model.addAttribute("games", gameService.getGamesList());
        return "games";
    }

    @RequestMapping(value = "/gameinfo/{id}", method = RequestMethod.GET)
    public String viewGame(@PathVariable("id") int id, Model model ) {
        Game game = gameService.getGame(id);
        if (!game.isHasElapsed()) {
            model.addAttribute("homeTeam", game.getHomeTeam());
            model.addAttribute("awayTeam", game.getAwayTeam());
            return "bet";
        }
        return "redirect:/gameslist";

    }

}
