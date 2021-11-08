package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.lesson.springBootProject.exceptions.UserServiceException;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.UserRepository;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.UserService;

import javax.servlet.http.HttpServletRequest;

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
            model.addAttribute("subscriptionsAuthors",user.getSubscriptions());
            //ссобщения могут прийти по редиректу
            if (message!=null){
                model.addAttribute("message",message);
            }
        }catch (UserServiceException ex){
            System.out.println(ex.getMessage());
        }
        return "userSubscriptionsList";
    }

    @Transactional
    @GetMapping("/unsubscribe")
    public ModelAndView unsubscribe(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Author author,
            Model model
    ){
        try {
            User user = userService.removeSubscriptions(userDetails.getUser(),author);
            model.addAttribute("message", "Subscription is deleted");
            return new ModelAndView("redirect:/mySubscriptions",model.asMap());

        }catch (UserServiceException ex){
            System.out.println(ex.getMessage());
        }
        model.addAttribute("message","Deleting a subscription failed");
        return new ModelAndView("/mySubscriptions",model.asMap());
    }

    @GetMapping("/subscribe/{author}")
    public ModelAndView subscribeFromBookList(
            @PathVariable Author author,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ){
        try {
            User user = userService.addSubscriptions(userDetails.getUser(),author);
            model.addAttribute("message", "Add subscription on "+author.getName());

        }catch (UserServiceException ex){
            System.out.println(ex.getMessage());
            model.addAttribute("message", "Add subscription on "+author.getName()+" failed");
        }
        return new ModelAndView("redirect:/",model.asMap());
    }

}
