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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.lesson.springBootProject.models.Comment;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
public class CommentController {

    //говорим чтобы Spring вытащил из property переменную
    @Value("${upload.comment.img.path}")
    private String uploadPath;
    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"/comment","/comment/{userId}","/comment/{userId}/{commentId}"})
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
            page = commentService.findAllByAuthor(userId,pageable);
        }
        else {
            page = commentService.findAll(pageable);
        }
        mapModel.put("commentsPage",page);
        mapModel.put("url", request.getRequestURL());
        return "comment";
    }
    @PostMapping(value = {"/comment/{userId}","/comment"})
    public String addComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid Comment comment,
            BindingResult bindingResult,
//            bindingResult должен идти перед model, иначе ошибки будут проходить без обработки
            Model model,
            @RequestParam("file") MultipartFile file,
            @PageableDefault(sort = {"commentId"}, direction = Sort.Direction.ASC, size = 6) Pageable pageable
    ){
        boolean isCorrect = true;
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
                    model.addAttribute("comment",comment);
                    e.printStackTrace();
                }
            }
            //проверяем, что файл сохранился без исключений
            if (isCorrect) {
                comment.setUser(userDetails.getUser());
                commentService.save(comment);
                model.addAttribute("comment", null);
                return "redirect:/comment";
            }
        }
        model.addAttribute("comments", commentService.findAll(pageable));
        return "comment";
    }


    @PostMapping("/comment/{userId}/{commentId}")
    public String saveChangingComment(
            @PathVariable Long userId,
            @PathVariable (name = "commentId") Comment oldComment,
            @Valid Comment changeComment,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(sort = {"commentId"}, direction = Sort.Direction.ASC, size = 6) Pageable pageable,
            Model model
    )
    {
        if (bindingResult.hasErrors()){
            //преобразуем ошибки в мапу (название поля+Error,message)
            Map<String, String> errorMap = ControllerUtils.getErrorMap(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("comment",changeComment);
            return "userComment";
        }
        //если пользователь редактирует свой коммент, то сохраняем его
        if (oldComment.getUser().equals(userDetails.getUser())){
            //если добавлен новый файл
            if (ControllerUtils.checkFilename(file)) {
                try {
                    changeComment.setFilename(ControllerUtils.saveFileWithNewFilename(file,uploadPath));
                } catch (IOException e) {
                    model.addAttribute("fileError",e.getMessage());
                    model.addAttribute("comment",changeComment);
                    e.printStackTrace();
                    return "userComment";
                }
                //если сохранение файла прошло успешно, удаляем старый файл
                File oldFile = new File(uploadPath+"/"+oldComment.getFilename());
                if (oldFile.exists()){
                    oldFile.delete();
                }
            }
            //если новый файл не введён, то в изменяющемся коменте, нужно сохранить старое значение filename
            else{
                changeComment.setFilename(oldComment.getFilename());
            }
            model.addAttribute("comment", null);
            changeComment.setUser(userDetails.getUser());
            commentService.save(changeComment);
        }
        model.addAttribute("model",null);
        model.addAttribute("comments", commentService.findAllByAuthor(userDetails.getUser().getUserId(),pageable));
        return "comment";
    }


}
