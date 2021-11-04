package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lesson.springBootProject.models.Author;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByName(String name);
    boolean existsAuthorByNameAndBirthday(String name, LocalDate birthday);
    Optional<Author> findAuthorByNameAndBirthday(String name, LocalDate birthday);
}
