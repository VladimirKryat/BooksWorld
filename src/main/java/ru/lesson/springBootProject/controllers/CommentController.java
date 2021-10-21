package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.lesson.springBootProject.forms.CommentForm;
import ru.lesson.springBootProject.models.Comment;
import ru.lesson.springBootProject.repositories.CommentRepository;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CommentController {
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
            Comment comment
    ){
        comment.setAuthor(userDetails.getUser());
        commentRepository.save(comment);
        return "redirect:/comment";
    }
}
