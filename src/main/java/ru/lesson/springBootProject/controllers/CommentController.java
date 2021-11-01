package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import ru.lesson.springBootProject.repositories.CommentRepository;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class CommentController {

    //говорим чтобы Spring вытащил из property переменную
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/comment")
    public String getListComment(Map<String, Object> mapModel){
        Iterable<Comment> comments = commentRepository.findAll();
        mapModel.put("comments", comments);
        return "comment";
    }
    @PostMapping("/comment")
    public String addComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid Comment comment,
            BindingResult bindingResult,
//            bindingResult должен идти перед model, иначе ошибки будут проходить без обработки
            Model model,
            @RequestParam("file") MultipartFile file
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
            if (checkFilename(file)) {
                try {
                    comment.setFilename(saveFileWithNewFilename(file));
                } catch (IOException e) {
                    isCorrect=false;
                    model.addAttribute("fileError",e.getMessage());
                    model.addAttribute("comment",comment);
                    e.printStackTrace();
                }
            }
            //проверяем, что файл сохранился без исключений
            if (isCorrect) {
                comment.setAuthor(userDetails.getUser());
                commentRepository.save(comment);
                model.addAttribute("comment", null);
            }
        }
        Iterable<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        return "comment";
    }
    //отображение коментов пользователя
    @GetMapping("/userComment/{userId}")
    public String getUserComments(
            @PathVariable(name="userId") Long user,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ){
        model.addAttribute("comments",commentRepository.findAllByAuthor_UserId(user));
        return "comment";
    }

    @GetMapping("/userComment/{userId}/{commentId}")
    public String changeComment(
            @PathVariable Long userId,
            @PathVariable Long commentId,
            Model model
    ){
        Comment comment = commentRepository.findById(commentId).get();
        model.addAttribute("comment",comment);
        return "userComment";
    }
    @PostMapping("/userComment/{userId}/{commentId}")
    public String saveChangingComment(
            @PathVariable Long userId,
            @PathVariable (name = "commentId") Comment oldComment,
            @Valid Comment changeComment,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
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
        if (oldComment.getAuthor().equals(userDetails.getUser())){
            //если добавлен новый файл
            if (checkFilename(file)) {
                try {
                    changeComment.setFilename(saveFileWithNewFilename(file));
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
            changeComment.setAuthor(userDetails.getUser());
            commentRepository.save(changeComment);
        }
        model.addAttribute("model",null);
        model.addAttribute("comments",commentRepository.findAllByAuthor_UserId(userDetails.getUser().getUserId()));
        return "comment";
    }

    private boolean checkFilename(MultipartFile file){
        return file != null && !file.getOriginalFilename().isEmpty();
    }
    private String saveFileWithNewFilename(MultipartFile file) throws IOException{
        File uploadDir = new File(uploadPath);
        //проверяем наличие директории для загрузки
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String resultFilename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));
        return resultFilename;
    }


}
