package is.hi.eplbetting.Controllers;

import java.util.List;

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
public class UserController {

    UserService userService;
    BetService betService;
    GameService gameService;

    public UserController(UserService userService, BetService betService, GameService gameService) {
        this.userService = userService;
        this.betService = betService;
        this.gameService = gameService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loggedinGET(Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            return "redirect:/main";
        }
    }
    
    @RequestMapping(value = "/main")
    public String mainPage(Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            User sessionUser = (User) session.getAttribute("LoggedInUser");
            model.addAttribute("LoggedInUsername", sessionUser.getUsername());
            model.addAttribute("userRole", sessionUser.isAdmin());
            System.out.println(sessionUser.isAdmin());
            return "main";
        }
    }

    @RequestMapping(value = "/usersList")
    public String usersPage(Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            List<User> users = userService.getUsers();
            model.addAttribute("users", users);
            return "users";
        }
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(User user) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/signup";
        }
        User exists = userService.getByUsername(user.getUsername());
        if (exists == null) {
            user.setBalance(10);
            userService.createUser(user);
            return "redirect:/login";
        }
        return "redirect:/signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "redirect:/login";
        }
        User exists = userService.login(user);

        if (exists != null) {
            session.setAttribute("LoggedInUser", exists);
            List<Bet> bets = betService.getBetsByUserId(user.getId());
            for (Bet bet: bets) {
                Game game = gameService.getGame(bet.getGameId());
                game.setUserHasBetted(true);
                game.setBetId(bet.getId());
                gameService.createGame(game);
            }
            return "redirect:/";
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/funds")
    public String fundsPage(Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            User sessionUser = (User) session.getAttribute("LoggedInUser");
            model.addAttribute("user", sessionUser);
            model.addAttribute("currBalance", sessionUser.getBalance());
            return "funds";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpSession session) {
        User user = new User();
        session.setAttribute("LoggedInUser", user);
        List<Game> games = gameService.getGamesList();
        for (Game game: games) {
            game.setUserHasBetted(false);
            gameService.createGame(game);
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            User user = (User) session.getAttribute("LoggedInUser");
            logout(model, session);
            List<Game> games = gameService.getGamesList();
            for (Game game: games) {
                game.setUserHasBetted(false);
                gameService.createGame(game);
            }
            List<Bet> bets = betService.getBetsByUserId(user.getId());
            for (Bet bet: bets) {
                betService.deleteBet(bet);
            }
            userService.deleteUser(user);
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/deleteUser/{id}", method = RequestMethod.GET)
    public String deleteUserById(@PathVariable("id") long id, Model model, HttpSession session){
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            User user = userService.getById(id);
            List<Game> games = gameService.getGamesList();
            for (Game game: games) {
                game.setUserHasBetted(false);
                gameService.createGame(game);
            }
            List<Bet> bets = betService.getBetsByUserId(user.getId());
            for (Bet bet: bets) {
                betService.deleteBet(bet);
            }
            userService.deleteUser(user);
            return "redirect:/main";
        }
    }

    

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String deposit(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            if (user.getDeposit() > 0) {
                User currUser = (User) session.getAttribute("LoggedInUser");
                currUser.setBalance(currUser.getBalance() + user.getDeposit());
                userService.createUser(currUser);
            }
            return "redirect:/funds";
        }
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdraw(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        if (checkLogin(session)) {
            return "redirect:/login";
        }
        else {
            if (user.getWithdrawal() > 0) {
                User currUser = (User) session.getAttribute("LoggedInUser");
                if (currUser.getBalance() >= user.getWithdrawal()) {
                    currUser.setBalance(currUser.getBalance() - user.getWithdrawal());
                    userService.createUser(currUser);
                }
            }
            return "redirect:/funds";
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
