package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.AuthorService;
import ru.lesson.springBootProject.services.UserService;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private UserService userService;
    @GetMapping("/manager/authorEditor")
    public String getAuthorEditor(
            @RequestParam(name="author", required = false) Author author,
            Model model
    )
    {
        if (author!=null){
            model.addAttribute("author",author);
        }
        return "authorEditor";
    }
    @PostMapping("/manager/authorEditor")
    public String setAuthor(
            @Valid Author author,
            BindingResult bindingResult,
            Model model
    ){
        boolean isCorrectBirthday = true;
        boolean isCorrectDateDeath = true;
        //если null то ошибка добавится из binding
        //а если день рождения в будущем, то добавляем ошибку сами
        if (author.getBirthday()!=null) {
            isCorrectBirthday = author.getBirthday().isBefore(LocalDate.now());
        }
        if (author.getDateOfDeath()!=null){
            isCorrectDateDeath=author.getDateOfDeath().isBefore(LocalDate.now());
        }
        //если обе даты корректно введены, проверяем их очерёдность
        //нужно проверить что дата смерти введена
        if (isCorrectBirthday && isCorrectDateDeath && author.getDateOfDeath()!=null && !author.getBirthday().isBefore(author.getDateOfDeath())){
            isCorrectBirthday = false;
            isCorrectDateDeath = false;
        }

        if (bindingResult.hasErrors() ||
                !isCorrectDateDeath ||
                !isCorrectBirthday){
            if (!isCorrectDateDeath){
                model.addAttribute("dateOfDeathError", "Date of death incorrect");
            }
            if (!isCorrectBirthday){
                model.addAttribute("birthdayError", "Birthday incorrect");
            }
            model.mergeAttributes(ControllerUtils.getErrorMap(bindingResult));
            model.addAttribute("author", author);
            return "authorEditor";
        }
        authorService.save(author);
        model.addAttribute("message", "Author saved");
        return "main";
    }

    @GetMapping("/authorInfo")
    public String getAuthorInfo(
            @RequestParam Author author,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ){
        User user = userService.getSubscriptions(userDetails.getUser());
        model.addAttribute("author",author);
        model.addAttribute("isSubscription", user.getSubscriptions().contains(author));
        model.addAttribute("countSubscribers",authorService.countSubscribers(author.getAuthorId()));
        model.addAttribute("countBooksInShop",authorService.countBook(author.getAuthorId()));
        return "authorInfo";
    }
}
