package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lesson.springBootProject.models.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre,Long> {
    List<Genre> findAllByBook_BookId(Long bookId);

    void deleteByGenreBookId(Long id);
}
