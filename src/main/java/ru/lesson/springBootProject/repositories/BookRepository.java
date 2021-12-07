package ru.lesson.springBootProject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.GenreBook;
import ru.lesson.springBootProject.models.GenreName;

import java.util.Set;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("select new ru.lesson.springBootProject.dto.BookDto (b, count(lk), sum(case when :userId = lk.userId then 1 else 0 end)>0) " +
            "from Book b left join b.likes lk group by b")
    Page<BookDto> findAll(@Param("userId") Long userId, Pageable pageable);

    @Query("select b.genres from Book b where b.bookId=:bookId")
    Set<GenreBook> findByBookId(@Param("bookId") Long bookId);

    @Query("select new ru.lesson.springBootProject.dto.BookDto (b, count(lk), sum(case when :userId = lk.userId then 1 else 0 end)>0) " +
            "from Book b left join b.likes lk where exists (select gn from GenreBook gn where gn.book=b and gn.genre=:genreFilter) group by b")
    Page<BookDto> findAllWithParam(@Param("userId") Long userId, Pageable pageable, @Param("genreFilter") GenreName genreFilter);

@Query(value = "select new ru.lesson.springBootProject.dto.BookDto (b, count(lk), sum(case when :userId = lk.userId then 1 else 0 end)>0) " +
        "from Book b left join b.likes lk left join b.authors auth where :author in (auth) group by b",
        countQuery = "select count(b) from Book b left join b.authors authors where :author in (authors)")
    Page<BookDto> findAllByAuthors(@Param("userId") Long userId, Pageable pageable, @Param("author") Author author);
}
