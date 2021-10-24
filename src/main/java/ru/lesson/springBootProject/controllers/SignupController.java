package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.lesson.springBootProject.exceptions.UserServiceException;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.UserRepository;
import ru.lesson.springBootProject.services.UserService;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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
}
