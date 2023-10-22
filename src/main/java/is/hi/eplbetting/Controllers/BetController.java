package is.hi.eplbetting.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.eplbetting.Persistence.Entities.Game;
import is.hi.eplbetting.Services.BetService;
import is.hi.eplbetting.Services.GameService;


@Controller
public class BetController {
    private BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

}
