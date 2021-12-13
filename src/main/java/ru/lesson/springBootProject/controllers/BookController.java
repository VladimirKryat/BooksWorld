package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.GenreName;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.AuthorService;
import ru.lesson.springBootProject.services.BookService;
import ru.lesson.springBootProject.services.filters.FilterBook;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

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
            model.addAttribute("book", new BookDto(book,0L,false));
        }

        model.addAttribute("allAuthors",authorService.findAll());
        model.addAttribute("allGenres", GenreName.values());
        return "bookEditor";
    }
    @PostMapping("/manager/bookEditor")
    public String saveBookEditor(
            @RequestParam(name = "bookId", required = false) Book oldBook,
            @RequestParam(required = false, name = "genresName") String[] genresName,
            @Valid Book book,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes
    ){
        //если жанр не выбран, то создаём ошибку поля
        if (genresName==null||genresName.length==0){
            bindingResult.addError(new FieldError("book","genresName","Genre can't be empty"));
        }
        if (bindingResult.hasErrors()){
            model.mergeAttributes(ControllerUtils.getErrorMap(bindingResult));
            model.addAttribute("book",new BookDto(book,0L,false));
            model.addAttribute("allAuthors",authorService.findAll());
            model.addAttribute("allGenres", GenreName.values());
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
                model.addAttribute("book",new BookDto(book,0L,false));
                model.addAttribute("allAuthors",authorService.findAll());
                model.addAttribute("allGenres", GenreName.values());
                return "bookEditor";
            }
        }
        //если книга уже есть восстанавливаем лайки и старый список жанров
        if (book.getBookId()!=null&&oldBook!=null){
            book.setLikes(oldBook.getLikes());
            book.setGenres(oldBook.getGenres());
        }
        bookService.changeGenre(book,genresName);

        bookService.save(book);
        redirectAttributes.addAttribute("message","Book save success");
        return "redirect:/bookList";
    }

    @GetMapping("/bookList")
    public String getBooks(
            Model model,
            @RequestParam(required = false) String message,
            @RequestParam(name = "sortedByLikes", required = false) String sortedByLikes,
            @RequestParam(name = "genreName", required = false) String genreName,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Qualifier("books") @PageableDefault(size=3, sort ={"bookId"}, direction = Sort.Direction.ASC )Pageable pageable,
            HttpServletRequest request
    ){
        if (message!=null){
            model.addAttribute("message",message);
        }
        FilterBook filterBook = null;
        //если введены значения отличные от NONE сохраняем их
        if ((sortedByLikes!=null&&!sortedByLikes.equals("NONE"))||(genreName!=null&&!genreName.equals("NONE"))){
            filterBook = new FilterBook();
            if (genreName!=null&&!genreName.equals("NONE")) filterBook.setGenreName(GenreName.valueOf(genreName));
            if (sortedByLikes!=null&&!sortedByLikes.equals("NONE")) filterBook.setSortedByLikes(FilterBook.SortedByLikes.valueOf(sortedByLikes));
            model.addAttribute("url",request.getRequestURL()+"?"+ filterBook.toStringURLParams()+"&amp;");
            model.addAttribute("filterBook",filterBook);
        }
        else {
            model.addAttribute("url",request.getRequestURL()+"?");
        }
        Page<BookDto> page = bookService.findAllWithFilter(userDetails.getUser().getUserId(),pageable, filterBook);
        model.addAttribute("allGenres",GenreName.values());
        model.addAttribute("books",page);
        if (page.getNumberOfElements()==0) {
            model.mergeAttributes(Map.of("message","No content. Please, choose another filter param"));
        }
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

    @GetMapping("/manager/bookDelete")
    public String bookDelete(
            @RequestParam(name="book") Long bookId
    ){
        bookService.delete(bookId);
        return "redirect:/bookList";
    }

    @GetMapping("/like")
    public String getLikes(
            @RequestParam(name = "user") Long userId,
            Model model,
            @Qualifier("books") @PageableDefault(size=3, sort ={"bookId"}, direction = Sort.Direction.ASC )Pageable pageable,
            HttpServletRequest request
    ){
        model.addAttribute("url",request.getRequestURL()+"?user="+userId+"&amp;");
        Page<BookDto> page = bookService.findAllLikes(userId,pageable);
        model.addAttribute("books",page);
        if (page.getNumberOfElements()==0) {
            model.mergeAttributes(Map.of("message","You have not got any likes"));
        }
        return "likeList";
    }

    @GetMapping("bookInfo")
    public String getBookInfo(
            @RequestParam(name = "book") Long bookId,
            @Qualifier("comments") @PageableDefault(size=6, sort ={"commentId"}, direction = Sort.Direction.ASC )Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("url",request.getRequestURL()+"?book="+bookId+"&amp;");
        model.addAttribute("book", bookService.findBookDtoById(userDetails.getUser().getUserId(), bookId));
        model.addAttribute("bookComments",bookService.findAllComments(bookId,pageable));
        return "bookInfo";
    }
}
