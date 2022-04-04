package com.tts.techtalenttwitter.Controller;

import javax.validation.Valid;

import com.tts.techtalenttwitter.Model.User;
import com.tts.techtalenttwitter.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//By adding the @Controller annotation, Spring Boot will
//know that this class is a controller and will
//automatically create a controller for us
@Controller
public class AuthorizationController {

    @Autowired
    private UserService userService;
    
    //Specify the URL path for the login page

    @GetMapping(value="/login")
    public String login() {
        return "login";
    //return value is the name of a template
    }

    @GetMapping(value="/signup")
    public String registration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    //When we signup with PostMapping that is going to an attempt to sign up
    @PostMapping(value="/signup")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model ) {
       // user at this point holds the input form data
       // that the user has set up for registration
       
       //Check to see if user already exists

       User foundUser = userService.findByUsername(user.getUsername());
         if (foundUser != null) {
                bindingResult.rejectValue("username", "error.user", "This username already exists");    
              return "registration";
         }
         if(!bindingResult.hasErrors()) {
            userService.saveNewUser(user);
            model.addAttribute("success", "Sign up successful!");
            User user2 = new User();
            user2.setLastName("Willis");
            model.addAttribute("user", user2);
            return "registration";
         }
    }
}
