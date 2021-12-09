package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.lesson.springBootProject.dto.AuthorInfoDto;
import ru.lesson.springBootProject.exceptions.UserServiceException;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.UserService;

import java.util.stream.Collectors;

@Controller
public class SubscriptionController {
    @Autowired
    private UserService userService;
    @GetMapping("/mySubscriptions")
    public String getUserSubscriptionsList(
            @RequestParam (required = false) String message,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ){
        try {
            User user = userService.getSubscriptions(userDetails.getUser());
            model.addAttribute("subscriptionsAuthors",user.getSubscriptions().stream().map(x->new AuthorInfoDto(x,true,-1,-1)).collect(Collectors.toSet()));
            //ссобщения могут прийти по редиректу
            if (message!=null){
                model.addAttribute("message",message);
            }
        }catch (UserServiceException ex){
            System.out.println(ex.getMessage());
        }
        return "userSubscriptionsList";
    }

    @GetMapping("/unsubscribe")
    public ModelAndView unsubscribe(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Author author,
            @RequestHeader(required = false) String referer,
            Model model
    ){
        try {
            userService.removeSubscriptions(userDetails.getUser(),author);

        }catch (UserServiceException ex){
            ex.printStackTrace();
        }
        return new ModelAndView("redirect:"+referer,model.asMap());
    }

    @GetMapping("/subscribe/{author}")
    public ModelAndView subscribeFromBookList(
            @PathVariable Author author,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader(required = false) String referer,
            Model model
    ){
        try {
            userService.addSubscriptions(userDetails.getUser(),author);
        }catch (UserServiceException ex){
            ex.printStackTrace();
        }
        return new ModelAndView("redirect:"+referer,model.asMap());
    }

}
