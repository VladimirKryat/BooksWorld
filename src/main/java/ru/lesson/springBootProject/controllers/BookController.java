package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.AuthorService;
import ru.lesson.springBootProject.services.BookService;

import javax.validation.Valid;
import java.io.IOException;

@Controller

public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Value("${default.book}")
    private String defaultBook;
    @Value("${upload.book.img.path}")
    private String uploadBookImgPath;
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
            Model model,
            @RequestParam("file") MultipartFile file,
//            @RequestHeader(required = false, defaultValue = "/") String referer

            RedirectAttributes redirectAttributes
    ){
        if (bindingResult.hasErrors()){
            model.mergeAttributes(ControllerUtils.getErrorMap(bindingResult));
            model.addAttribute("book",book);
            model.addAttribute("allAuthors",authorService.findAll());
            return "bookEditor";
        }
        if (!ControllerUtils.checkFilename(file)){
            book.setFilename(defaultBook);
        } else {
            try {
                book.setFilename(ControllerUtils.saveFileWithNewFilename(file,uploadBookImgPath));
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("fileError",e.getMessage());
                model.addAttribute("book",book);
                model.addAttribute("allAuthors",authorService.findAll());
                return "bookEditor";
            }
        }
        bookService.save(book);
        redirectAttributes.addAttribute("message","Book save success");
        return "redirect:/bookList";
    }

    @GetMapping("/bookList")
    public String getBooks(
            Model model,
            @RequestParam(required = false) String message,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        if (message!=null){
            model.addAttribute("message",message);
        }
        model.addAttribute("books",bookService.findAll(userDetails.getUser().getUserId()));
        return "bookList";
    }
}
