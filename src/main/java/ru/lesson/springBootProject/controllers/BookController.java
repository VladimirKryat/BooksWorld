package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.services.AuthorService;
import ru.lesson.springBootProject.services.BookService;

@Controller

@RequestMapping("/manager")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @GetMapping("/bookEditor")
    public String getBookEditor(
            @RequestParam (name="book", required = false) Book book,
            Model model
    ){
        if (book!=null) {
            model.addAttribute("book", book);
        }

        model.addAttribute("authors",authorService.findAll());
        return "bookEditor";
    }
    @PostMapping("/bookEditor")
    public String saveBookEditor(
            Book book,
            Model model
    ){
        if (book!=null){
            bookService.save(book);
            model.addAttribute("message","Book save success");
        }
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
