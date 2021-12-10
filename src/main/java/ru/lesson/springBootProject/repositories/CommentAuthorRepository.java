package ru.lesson.springBootProject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.springBootProject.models.Comment;
import ru.lesson.springBootProject.models.CommentAuthor;

import java.util.Optional;

public interface CommentAuthorRepository extends JpaRepository<CommentAuthor, Long> {
    Page<CommentAuthor> findAll(Pageable pageable);
    Page<CommentAuthor> findAllByUser_UserId(Long userId, Pageable pageable);

    @Query(value = "select comm from CommentAuthor comm left join comm.author auth where auth.authorId=:authorId",
    countQuery = "select count(comm) from CommentAuthor comm left join comm.author auth where auth.authorId=:authorId")
    Page<CommentAuthor> findAllByAuthor_AuthorId(@Param("authorId") Long authorId, Pageable pageable);

    @Query("select c from CommentAuthor c left join fetch c.author where c.commentId=:commentId")
    Optional<CommentAuthor> findByCommentIdWithAuthor(Long commentId);
}
