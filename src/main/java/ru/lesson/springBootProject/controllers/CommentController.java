package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.lesson.springBootProject.forms.CommentForm;
import ru.lesson.springBootProject.models.Comment;
import ru.lesson.springBootProject.repositories.CommentRepository;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
            Comment comment,
            @RequestParam("file") MultipartFile file
    ){
        //проверяем что имя файла задано
        if (file!=null&& !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            //проверяем наличие директории для загрузки
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String resultFilename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath+"/"+resultFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            comment.setFilename(resultFilename);
        }
        comment.setAuthor(userDetails.getUser());
        commentRepository.save(comment);
        return "redirect:/comment";
    }
}
