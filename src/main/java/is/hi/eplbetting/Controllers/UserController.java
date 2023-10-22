package is.hi.eplbetting.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import is.hi.eplbetting.Persistence.Entities.User;
import is.hi.eplbetting.Services.UserService;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loggedinGET(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser != null) {
            return "redirect:/main";
        }
        return "redirect:/login";
    }
    
    @RequestMapping(value = "/main")
    public String mainPage(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        model.addAttribute("LoggedInUsername", sessionUser.getUsername());
        return "main";
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
            userService.createUser(user);
            return "redirect:/login";
        }
        return "signup";
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
            return "redirect:/";
        }

        return "redirect:/login";
    }
}
