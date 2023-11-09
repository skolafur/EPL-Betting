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
            List<Bet> bets;
            if (user.isAdmin()) {
                bets = betService.getBetsList();
            }
            else {
                bets = betService.getBetsByUserId(user.getId());
            }
            model.addAttribute("bets", bets);
            return "bets";
        }
    }

    @RequestMapping(value = "/makeormodify/{id}", method = RequestMethod.GET)
    public String makeormodify(@PathVariable("id") int id, Model model, HttpSession session, HttpServletRequest request) {
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Game game = (Game) gameService.getGame(id);
            if (game.isUserHasBetted()) {
                return "redirect:/modifyBet/{id}";
            }
            else {
                return "redirect:/gameinfo/{id}";
            }
        }
    }

    @RequestMapping(value = "/gameinfo/{id}", method = RequestMethod.GET)
    public String viewGame(@PathVariable("id") int id, Model model, HttpSession session, HttpServletRequest request) {
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Game game = (Game) gameService.getGame(id);
            if (!game.isHasStarted()) {
                model.addAttribute("homeTeam", game.getHomeTeam());
                model.addAttribute("awayTeam", game.getAwayTeam());
                User user = (User) session.getAttribute("LoggedInUser");
                Bet bet = betService.getBet(id, user.getId());
                if (bet == null) {
                    Bet newBet = betService.createBet(new Bet());
                    newBet.setGameId(game.getId());
                    newBet.setUserId(user.getId());
                    newBet.setMultiplier1(2);
                    newBet.setMultiplierX(2);
                    newBet.setMultiplier2(2);
                    betService.createBet(newBet);
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
            if (user.getBalance() < bet.getAmount()) {
                return "redirect:" + currentUrl;
            }
            user.setBalance(user.getBalance() - bet.getAmount());
            Bet newBet = (Bet) session.getAttribute("newBet");
            newBet.setSelectedTeam(bet.getSelectedTeam()); //
            newBet.setAmount(bet.getAmount());
            userService.createUser(user);
            betService.createBet(newBet);
            Game game = gameService.getGame(newBet.getGameId());
            game.setUserHasBetted(true);
            game.setBetId(newBet.getId());
            gameService.createGame(game);
            return "redirect:/gameslist";
        }
    }

    @RequestMapping(value = "/modifybetPOST", method = RequestMethod.POST)
    public String modifybetPOST(@ModelAttribute("bet") Bet bet, User user, BindingResult result, Model model, HttpSession session) {
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            String currentUrl = (String) session.getAttribute("currentUrl");
            Bet oldBet = (Bet) session.getAttribute("oldbet");
            user = userService.getById(oldBet.getUserId());
            if (result.hasErrors()) {
                return "redirect:" + currentUrl;
            }
            if ((oldBet.getSelectedTeam() == bet.getSelectedTeam()) && (oldBet.getAmount() == bet.getAmount())) {
                return "redirect:" + currentUrl;
            }
            if (bet.getAmount() < 1) {
                return "redirect:" + currentUrl;
            }
            if (bet.getAmount() > oldBet.getAmount()) {
                if (bet.getAmount() - oldBet.getAmount() > user.getBalance()) {
                    return "redirect:" + currentUrl;
                }
            }
            user.setBalance(user.getBalance() - (bet.getAmount() - oldBet.getAmount()));
            oldBet.setAmount(bet.getAmount());
            if (oldBet.getSelectedTeam() != bet.getSelectedTeam()) {
                oldBet.setSelectedTeam(bet.getSelectedTeam());
            }
            betService.createBet(oldBet);
            userService.createUser(user);
            return "redirect:/betslist";
        }
    }

    @RequestMapping(value="/modifyBet/{id}", method = RequestMethod.GET)
    public String modifyBet(@PathVariable("id") long id, Model model, HttpSession session, HttpServletRequest request){
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Bet bet = betService.getBet(id);
            Game game = gameService.getGame(bet.getGameId());
            model.addAttribute("homeTeam", game.getHomeTeam());
            model.addAttribute("awayTeam", game.getAwayTeam());
            model.addAttribute("bet", bet);
            session.setAttribute("oldbet", bet);
            String currentUrl = request.getRequestURL().toString();
            session.setAttribute("currentUrl", currentUrl);
            return "modifybet";
        }
    }

    @RequestMapping(value="/deleteBet/{id}", method = RequestMethod.GET)
    public String deleteBet(@PathVariable("id") long id, Model model, HttpSession session){
        if (userController.checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            Bet bet = betService.getBet(id);
            User user = userService.getById(bet.getUserId());
            user.setBalance(user.getBalance() + bet.getAmount());
            userService.createUser(user);
            Game game = gameService.getGame(bet.getGameId());
            game.setUserHasBetted(false);
            game.setBetId(bet.getId());
            gameService.createGame(game);
            betService.deleteBet(bet);
            return "redirect:/betslist";
        }
    }

}
