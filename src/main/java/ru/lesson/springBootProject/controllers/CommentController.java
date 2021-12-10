package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.lesson.springBootProject.models.*;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.AuthorService;
import ru.lesson.springBootProject.services.BookService;
import ru.lesson.springBootProject.services.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Controller
public class CommentController {

    //говорим чтобы Spring вытащил из property переменную
    @Value("${upload.comment.img.path}")
    private String uploadPath;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    @GetMapping(value = {"/comment","/comment/{userId}"})
    public String getListComment(
            Map<String, Object> mapModel,
            @PathVariable (name = "userId", required = false) Long userId,
            @PathVariable (name = "commentId", required = false) Comment comment,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(sort = {"commentId"}, direction = Sort.Direction.ASC, size = 6) Pageable pageable,
            HttpServletRequest request
            ){
        if (userId!=null && comment!=null){
            mapModel.put("comment", comment);
            return "comment";
        }
        Page<Comment> page;
        if (userId!=null){
            page = commentService.findAllByUser(userId,pageable);
        }
        else {
            page = commentService.findAll(pageable);
        }
        mapModel.put("commentsPage",page);
        mapModel.put("url", request.getRequestURL());
        return "comment";
    }


    @GetMapping(value = "/commentedit")
    public String editComment(
            @RequestParam(name = "comment") Long commentId,
            @RequestHeader(required = false) String referer,
            Model model
    ){
        model.addAttribute("refUrl",referer);
        Comment oldComment=null;
        if (referer.toLowerCase(Locale.ROOT).contains("authorinfo")){
            oldComment=new CommentAuthor();
            oldComment.setCommentId(commentId);
        }else if(referer.toLowerCase(Locale.ROOT).contains("bookinfo")){
            oldComment =new CommentBook();
            oldComment.setCommentId(commentId);
        }
        oldComment = commentService.findByIdAndInstanceofWithOwner(oldComment).orElseGet(null);
        model.addAttribute("comment",oldComment);
        return"comment";
    }

    @PostMapping(value = {"/commentauthor","/commentauthor","/commentedit"})
    public String addCommentAuthor(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid Comment comment,
            @RequestParam(name = "ownerId") Long ownerId,
            @RequestParam(name="refUrl", required = false) String refUrl,
            @RequestHeader(required = false) String referer,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file,
            @PageableDefault(sort = {"commentId"}, direction = Sort.Direction.ASC, size = 6) Pageable pageable,
            HttpServletRequest request
    ){
        boolean isCorrect = true;
        //если пользователь редактирует не свой коммент отправляем его на главную страницу
        if (comment.getUser()!=null
                &&comment.getCommentId()!=null
                &&comment.getCommentId()>0
                &&!comment.getUser().equals(userDetails.getUser())){
            return "redirect:/";
        }
            if (bindingResult.hasErrors()){
            isCorrect=false;
            //преобразуем ошибки в мапу (название поля+Error,message)
            Map<String, String> errorMap = ControllerUtils.getErrorMap(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("comment",comment);
        }
        else {
            //проверяем что имя файла задано
            if (ControllerUtils.checkFilename(file)) {
                try {
                    comment.setFilename(ControllerUtils.saveFileWithNewFilename(file,uploadPath));
                } catch (IOException e) {
                    isCorrect=false;
                    model.addAttribute("fileError",e.getMessage());
                    //                    e.printStackTrace();
                }
            }
            //проверяем, что файл сохранился без исключений
            if (isCorrect) {
                comment.setUser(userDetails.getUser());
                //если пришли с commentedit могли не получить ownerId
                Comment commentOld =null;
                if (ownerId==null || ownerId<=0){
                    commentOld = commentService.findByIdAndInstanceofWithOwner(comment).get();
                    commentOld.setFilename(comment.getFilename());
                    commentOld.setUser(comment.getUser());
                    commentOld.setText(comment.getText());
                    commentOld.setStars(comment.getStars());
                }
                else if (request.getRequestURL().indexOf("author")!=-1){
                    Author author = authorService.findById(ownerId);
                    commentOld = new CommentAuthor(comment, author);
                }
                else if (request.getRequestURL().indexOf("book")!=-1){
                    Book book = bookService.findById(ownerId);
                    commentOld = new CommentBook(comment, book);
                }
                commentService.save(commentOld);
                if (refUrl==null||refUrl.isBlank()||refUrl.isEmpty()){
                    return "redirect:"+referer;
                }else {
                    return "redirect:"+refUrl;
                }
            }
        }
        //сохраняем прошлую ссылку если она не сохранена(в случае если пришли со страницы authorinfo)
        if(refUrl==null){
            refUrl=referer;
        }
        model.addAttribute("comment",comment);
        model.addAttribute("refUrl",refUrl);
        model.addAttribute("ownerId",ownerId);
        return "comment";
    }

}
