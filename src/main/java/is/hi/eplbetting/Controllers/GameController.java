package is.hi.eplbetting.Controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.eplbetting.Persistence.Entities.Bet;
import is.hi.eplbetting.Persistence.Entities.Game;
import is.hi.eplbetting.Persistence.Entities.User;
import is.hi.eplbetting.Services.BetService;
import is.hi.eplbetting.Services.GameService;
import is.hi.eplbetting.Services.UserService;

@Controller
public class GameController {
    private GameService gameService;
    private BetService betService;
    private UserService userService;

    public GameController(GameService gameService, BetService betService, UserService userService) {
        this.gameService = gameService;
        this.betService = betService;
        this.userService = userService;
    }

    @RequestMapping("/gameslist")
    public String gamesPage(Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            User user = (User) session.getAttribute("LoggedInUser");
            model.addAttribute("userRole", user.isAdmin());
            model.addAttribute("games", gameService.getGamesList());
            return "games";
        }
    }

    @RequestMapping("/backtogames")
    public String backToGamesPage(Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Bet bet = (Bet) session.getAttribute("newBet");
            betService.deleteBet(bet);
            return "redirect:/gameslist";
        }
    }

    @RequestMapping(value = "/editGame/{id}", method = RequestMethod.GET)
    public String editGame(@PathVariable("id") int id, Model model, HttpSession session, HttpServletRequest request) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Game game = gameService.getGame(id);
            model.addAttribute("game", game);
            return "modifygame";
        }
    }

    @RequestMapping(value = "/updateGame", method = RequestMethod.POST)
    public String updateGamePOST(@ModelAttribute("game") Game game, BindingResult result, Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            gameService.createGame(game);
            return "redirect:/gameslist";
        }
    }

    public boolean checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        if (user == null) {
            return true;
        }
        else {
            return false;
        }

    }

}