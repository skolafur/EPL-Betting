package is.hi.eplbetting.Controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
public class UserController {

    @RequestMapping("/")
    public String UserController() {
        return "home";
    }
    
}
