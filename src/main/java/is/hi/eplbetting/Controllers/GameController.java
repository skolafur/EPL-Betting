package is.hi.eplbetting.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import is.hi.eplbetting.Persistence.Entities.Bet;
import is.hi.eplbetting.Services.BetService;
import is.hi.eplbetting.Services.GameService;

@Controller
public class GameController {
    private GameService gameService;
    private UserController userController;
    private BetService betService;

    public GameController(GameService gameService, UserController userController, BetService betService) {
        this.gameService = gameService;
        this.userController = userController;
        this.betService = betService;
    }

    @RequestMapping("/gameslist")
    public String gamesPage(Model model, HttpSession session) {
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            model.addAttribute("games", gameService.getGamesList());
            return "games";
        }
    }

    @RequestMapping("/backtogames")
    public String backToGamesPage(Model model, HttpSession session) {
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Bet bet = (Bet) session.getAttribute("newBet");
            betService.deleteBet(bet);
            return "redirect:/gameslist";
        }
    }

}
