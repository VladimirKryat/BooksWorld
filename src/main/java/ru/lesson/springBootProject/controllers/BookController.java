package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.AuthorService;
import ru.lesson.springBootProject.services.BookService;

import javax.servlet.http.HttpServletRequest;
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
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(size=3, sort ={"bookId"}, direction = Sort.Direction.ASC )Pageable pageable,
            HttpServletRequest request
            ){
        if (message!=null){
            model.addAttribute("message",message);
        }
        Page<BookDto> page = bookService.findAll(userDetails.getUser().getUserId(),pageable);
        if (page.getTotalPages()-1<page.getNumber())
        {
            pageable= PageRequest.of(page.getTotalPages()-1,pageable.getPageSize(),pageable.getSort());
            page = bookService.findAll(userDetails.getUser().getUserId(), pageable);
        }
        model.addAttribute("books",page);
        model.addAttribute("url",request.getRequestURL());
        return "bookList";
    }

    @GetMapping("/book/{bookId}/like")
    public String like(
            @PathVariable(name = "bookId") Book book,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer,
            HttpServletRequest request
    ){
        book = bookService.like(book,userDetails.getUser());
        return "redirect:"+referer;
    }
}
