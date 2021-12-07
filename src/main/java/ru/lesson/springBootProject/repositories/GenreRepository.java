package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lesson.springBootProject.models.GenreBook;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreBook,Long> {
    List<GenreBook> findAllByBook_BookId(Long bookId);

    void deleteByGenreBookId(Long id);
}
