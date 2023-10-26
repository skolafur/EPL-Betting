package is.hi.eplbetting.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import is.hi.eplbetting.Services.GameService;

@Controller
public class GameController {
    private GameService gameService;
    private UserController userController;

    public GameController(GameService gameService, UserController userController) {
        this.gameService = gameService;
        this.userController = userController;
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

}
