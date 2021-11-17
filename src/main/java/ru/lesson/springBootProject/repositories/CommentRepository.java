package ru.lesson.springBootProject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lesson.springBootProject.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
@Query(value = "FROM Comment c LEFT JOIN c.author")
    List<Comment> findAll();
    List<Comment> findAllByAuthor_UserId(Long userId);

    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findAllByAuthor_UserId(Long userId, Pageable pageable);
}
