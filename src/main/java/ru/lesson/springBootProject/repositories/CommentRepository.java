package ru.lesson.springBootProject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.lesson.springBootProject.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findAllByAuthor_UserId(Long userId, Pageable pageable);
}
