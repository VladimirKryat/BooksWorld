package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("select new ru.lesson.springBootProject.dto.BookDto (b, count(lk), sum(case when :userId = lk.userId then 1 else 0 end)>0) " +
            "from Book b left join b.likes lk group by b")
    List<BookDto> findAll(@Param("userId") Long userId);
}
