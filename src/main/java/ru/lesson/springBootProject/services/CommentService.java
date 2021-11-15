package ru.lesson.springBootProject.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lesson.springBootProject.models.Comment;

public interface CommentService {
    Page findAllByAuthor(Long userId, Pageable pageable);

    Page findAll(Pageable pageable);

    Comment save(Comment comment);
}
