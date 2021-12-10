package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.models.Comment;
import ru.lesson.springBootProject.models.CommentAuthor;
import ru.lesson.springBootProject.models.CommentBook;
import ru.lesson.springBootProject.repositories.CommentAuthorRepository;
import ru.lesson.springBootProject.repositories.CommentBookRepository;
import ru.lesson.springBootProject.repositories.CommentRepository;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentAuthorRepository commentAuthorRepository;
    @Autowired
    CommentBookRepository commentBookRepository;


    @Override
    public Page findAllByUser(Long userId, Pageable pageable){
        return commentRepository.findAllByUser_UserId(userId,pageable);
    }

    @Override
    public Page findAll(Pageable pageable){
        return commentRepository.findAll(pageable);
    }
    @Override
    public Comment save(Comment comment){
        if (comment instanceof CommentAuthor){
            return commentAuthorRepository.save((CommentAuthor)comment);
        }
        if (comment instanceof CommentBook){
            return commentBookRepository.save((CommentBook) comment);
        }
        //возвращаем null если привести к конкретному типу не удалось
        return commentRepository.save(comment);
    }

    @Override
    public Optional<? extends Comment> findByIdAndInstanceofWithOwner(Comment comment) {
        if (comment instanceof CommentAuthor){
            return commentAuthorRepository.findByCommentIdWithAuthor(comment.getCommentId());
        }
        if (comment instanceof CommentBook){
            return commentBookRepository.findByCommentIdWithBook(comment.getCommentId());
        }
        //наихудший случай когда Hibernate ищет во всех таблицах comment
        return commentRepository.findById(comment.getCommentId());
    }
}
