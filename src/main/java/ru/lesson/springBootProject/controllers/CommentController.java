package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.lesson.springBootProject.forms.CommentForm;
import ru.lesson.springBootProject.models.Comment;
import ru.lesson.springBootProject.repositories.CommentRepository;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class CommentController {
    //говорим чтобы Spring вытащил из property переменную
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/comment")
    public String getComment(Map<String, Object> mapModel){
        Iterable<CommentForm> comments = commentRepository.findAll().stream()
                .map(x-> CommentForm.from(x)).
                collect(Collectors.toList());
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

        if (bindingResult.hasErrors()){
            //преобразуем ошибки в мапу (название поля+Error,message)
            Map<String, String> errorMap = ControllerUtils.getErrorMap(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("comment",comment);
        }
        else {
            //проверяем что имя файла задано
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                //проверяем наличие директории для загрузки
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String resultFilename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
                try {
                    file.transferTo(new File(uploadPath + "/" + resultFilename));
                    comment.setFilename(resultFilename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            comment.setAuthor(userDetails.getUser());
            commentRepository.save(comment);
            model.addAttribute("comment",null);
        }
        Iterable<CommentForm> comments = commentRepository.findAll().stream()
                .map(x-> CommentForm.from(x)).
                collect(Collectors.toList());
        model.addAttribute("comments", comments);
        return "comment";
    }


}
