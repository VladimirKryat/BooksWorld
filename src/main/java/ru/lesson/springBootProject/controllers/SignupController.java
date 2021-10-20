package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.UserRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
public class SignupController{
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @PostMapping("signup")
    public String addUser(User user, Map<String,Object> mapModel){
        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb.isPresent()){
            mapModel.put("message","User exist");
            return "signup";
        }
        user.setState(State.ACTIVE);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
