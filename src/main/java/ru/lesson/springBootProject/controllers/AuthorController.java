package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lesson.springBootProject.dto.AuthorInfoDto;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.AuthorService;
import ru.lesson.springBootProject.services.BookService;
import ru.lesson.springBootProject.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;

@Controller
@RequestMapping
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

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
        //если null то ошибка добавится из binding
        //а если день рождения в будущем, то добавляем ошибку сами
        if (author.getBirthday()!=null && author.getBirthday().isAfter(LocalDate.now())) {
            bindingResult.addError(new FieldError("author","birthday","Birthday can't be in the future"));
        }
        if (author.getDateOfDeath()!=null && author.getDateOfDeath().isAfter(LocalDate.now())){
            bindingResult.addError(new FieldError("author","dateOfDeath","Don't fill if it didn't happen"));
        }
        //если обе даты корректно введены, проверяем их очерёдность
        //нужно проверить что дата смерти введена
        if (author.getBirthday()!=null && author.getDateOfDeath()!=null && !author.getBirthday().isBefore(author.getDateOfDeath())){
            bindingResult.addError(new FieldError("author","birthday","Birthday cannot be later than the date of death"));
            bindingResult.addError(new FieldError("author","dateOfDeath","Date of death cannot be earlier than the birthday"));
        }

        if (bindingResult.hasErrors()){
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
            @RequestParam(name = "author") Long authorId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Qualifier("books") @PageableDefault( size=6, sort ={"bookId"}, direction = Sort.Direction.DESC ) Pageable pageableBooks,
            @Qualifier("comments") @PageableDefault(sort = {"commentId"}, direction = Sort.Direction.DESC, size = 6) Pageable pageableComments,
            HttpServletRequest request,
            Model model
    ){
        AuthorInfoDto author = authorService.getAuthorInfoDto(userDetails.getUser().getUserId(), authorId);
        model.addAttribute("author",author);
        model.addAttribute("books",bookService.findAllByAuthors(userDetails.getUser().getUserId(),authorId, pageableBooks));
        model.addAttribute("url",request.getRequestURL()+"?author="+authorId+"&amp;");
        model.addAttribute("commentsPage",authorService.findAllComments(authorId,pageableComments));
        return "authorInfo";
    }
}
