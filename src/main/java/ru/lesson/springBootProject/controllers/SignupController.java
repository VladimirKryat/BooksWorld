package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lesson.springBootProject.exceptions.UserServiceException;
import ru.lesson.springBootProject.exceptions.UsernameNotUniqueException;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.services.UserService;

import javax.validation.Valid;

@Controller
public class SignupController{
    @Autowired
    private UserService userService;
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @PostMapping("signup")
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          @RequestParam String passwordConfirm,
                          Model model
    ){
        //если валидация с ошибками возвращаемся к регистрации
        if (bindingResult.hasErrors()||!userService.checkPasswordConfirm(user.getPassword(),passwordConfirm)){
            if (!userService.checkPasswordConfirm(user.getPassword(),passwordConfirm)){
                model.addAttribute("passwordConfirmError","Password confirmation failed");
            }
            model.mergeAttributes(ControllerUtils.getErrorMap(bindingResult));
            model.addAttribute("user",user);
            return "signup";
        }

       try {
           userService.addUser(user);
       } catch (UserServiceException ex){
           if (ex instanceof UsernameNotUniqueException){
               model.addAttribute("usernameError","Username exist, try another username");
           }else {
               model.addAttribute("message", ex.getMessage());
           }
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
