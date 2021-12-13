package ru.lesson.springBootProject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.springBootProject.models.Comment;
import ru.lesson.springBootProject.models.CommentAuthor;
import ru.lesson.springBootProject.models.CommentBook;

import java.util.Optional;

public interface CommentBookRepository extends JpaRepository<CommentBook, Long> {
    Page<CommentBook> findAll(Pageable pageable);
    Page<CommentBook> findAllByUser_UserId(Long userId, Pageable pageable);

    @Query("select c from CommentBook c left join fetch c.book where c.commentId=:commentId")
    Optional<CommentBook> findByCommentIdWithBook(@Param("commentId") Long commentId);

    @Query(value = "select comm from CommentBook comm left join comm.book book where book.bookId=:bookId",
            countQuery = "select count(comm) from CommentBook comm left join comm.book book where book.bookId=:bookId")
    Page<CommentBook> findAllByBook_BookId(@Param("bookId") Long bookId, Pageable pageable);
}
