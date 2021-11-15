package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.models.Comment;
import ru.lesson.springBootProject.repositories.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Override
    public Page findAllByAuthor(Long userId, Pageable pageable){
        return commentRepository.findAllByAuthor_UserId(userId,pageable);
    }

    @Override
    public Page findAll(Pageable pageable){
        return commentRepository.findAll(pageable);
    }
    @Override
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }
}
