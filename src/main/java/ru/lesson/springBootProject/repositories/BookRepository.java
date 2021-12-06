package ru.lesson.springBootProject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.Genre;

import java.util.Set;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("select new ru.lesson.springBootProject.dto.BookDto (b, count(lk), sum(case when :userId = lk.userId then 1 else 0 end)>0) " +
            "from Book b left join b.likes lk group by b")
    Page<BookDto> findAll(@Param("userId") Long userId, Pageable pageable);

    @Query("select b.genres from Book b where b.bookId=:bookId")
    Set<Genre> findByBookId(@Param("bookId") Long bookId);
}
