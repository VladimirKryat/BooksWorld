package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.services.AuthorService;

@Controller
@RequestMapping("/manager")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping("/authorEditor")
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
    @PostMapping("/authorEditor")
    public String setAuthor(
            Author author,
            Model model
    ){
        authorService.save(author);
        model.addAttribute("message", "Author saved");
        return "main";
    }
}
