package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lesson.springBootProject.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
@Query(nativeQuery = true, value = "SELECT * FROM comment c LEFT JOIN userdata u on c.user_id = u.user_id")
    List<Comment> findAll();
}
