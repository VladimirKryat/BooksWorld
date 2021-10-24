package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.lesson.springBootProject.exceptions.UserServiceException;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.services.UserService;

import java.util.Map;

@Controller
public class SignupController{
    @Autowired
    private UserService userService;
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @PostMapping("signup")
    public String addUser(User user, Map<String,Object> mapModel){
       try {
           userService.addUser(user);
       } catch (UserServiceException ex){
           mapModel.put("message",ex.getMessage());
           return "signup";
       }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        if (isActivated){
            model.addAttribute("message", "Email successfully activated");
        }
        else {
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }
}
