package is.hi.eplbetting.Controllers;

import java.net.http.HttpRequest;
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
public class BetController {
    private BetService betService;
    private GameService gameService;
    private UserService userService;
    private UserController userController;

    public BetController(BetService betService, GameService gameService, UserService userService, UserController userController) {
        this.betService = betService;
        this.gameService = gameService;
        this.userService = userService;
        this.userController = userController;
    }

    @RequestMapping("/betslist")
    public String betsPage(Model model, HttpSession session) {
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            User user = (User) session.getAttribute("LoggedInUser");
            List<Bet> bets = betService.getBetsByUserId(user.getId());
            model.addAttribute("bets", bets);
            return "bets";
        }
    }

    @RequestMapping(value = "/gameinfo/{id}", method = RequestMethod.GET)
    public String viewGame(@PathVariable("id") int id, Model model, HttpSession session, HttpServletRequest request) {
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Game game = (Game) gameService.getGame(id);
            if (!game.isHasElapsed()) {
                model.addAttribute("homeTeam", game.getHomeTeam());
                model.addAttribute("awayTeam", game.getAwayTeam());
                User user = (User) session.getAttribute("LoggedInUser");
                Bet bet = betService.getBet(id, user.getId());
                if (bet == null) {
                    Bet newBet = betService.createBet(new Bet());
                    newBet.setGameId(game.getId());
                    newBet.setUserId(user.getId());
                    session.setAttribute("newBet", newBet);
                    model.addAttribute("bet", newBet);
                    session.setAttribute("gameId", newBet.getGameId());
                    String currentUrl = request.getRequestURL().toString();
                    session.setAttribute("currentUrl", currentUrl);
                    return "bet";
                }
            }
            return "redirect:/gameslist";
        }
    }

    @RequestMapping(value = "/betPOST", method = RequestMethod.POST)
    public String betPOST(@ModelAttribute("bet") Bet bet, User user, BindingResult result, Model model, HttpSession session) {
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            String currentUrl = (String) session.getAttribute("currentUrl");
            user = (User) session.getAttribute("LoggedInUser");
            if (result.hasErrors()) {
                return "redirect:" + currentUrl;
            }
            if (bet.getAmount() < 1) {
                return "redirect:" + currentUrl;
            }
            Bet newBet = (Bet) session.getAttribute("newBet");
            newBet.setSelectedTeam(bet.getSelectedTeam()); //
            newBet.setAmount(bet.getAmount());
            betService.createBet(newBet);
            return "redirect:/gameslist";
        }
    }

    @RequestMapping(value="/deleteBet/{id}", method = RequestMethod.GET)
    public String deleteBet(@PathVariable("id") long id, Model model, HttpSession session){
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Bet bet = betService.getBet(id);
            betService.deleteBet(bet);
            return "redirect:/betslist";
        }
    }

}
