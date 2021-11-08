package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.services.AuthorService;
import ru.lesson.springBootProject.services.BookService;

import javax.validation.Valid;

@Controller

public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @GetMapping("/manager/bookEditor")
    public String getBookEditor(
            @RequestParam (name="book", required = false) Book book,
            Model model
    ){
        if (book!=null) {
            model.addAttribute("book", book);
        }

        model.addAttribute("allAuthors",authorService.findAll());
        return "bookEditor";
    }
    @PostMapping("/manager/bookEditor")
    public String saveBookEditor(
            @Valid Book book,
            BindingResult bindingResult,
            Model model
    ){
        if (bindingResult.hasErrors()){
            model.mergeAttributes(ControllerUtils.getErrorMap(bindingResult));
            model.addAttribute("book",book);
            model.addAttribute("allAuthors",authorService.findAll());
            return "bookEditor";
        }
        bookService.save(book);
        model.addAttribute("book",null);
        model.addAttribute("message","Book save success");
        return "main";
    }

    @GetMapping("/bookList")
    public String getBooks(
            Model model
    ){
        model.addAttribute("books",bookService.findAll());
        return "bookList";
    }
}
