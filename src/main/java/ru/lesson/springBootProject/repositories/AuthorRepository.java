package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.User;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByName(String name);
    boolean existsAuthorByNameAndBirthday(String name, LocalDate birthday);
    Optional<Author> findAuthorByNameAndBirthday(String name, LocalDate birthday);
    @Query("SELECT a.subscriptions.size FROM Author a WHERE a.authorId=:authorId")
    Long countSubscribers(@Param("authorId") Long authorId);
    @Query("SELECT a.books.size FROM Author a WHERE a.authorId=:authorId")
    Long countBook(@Param("authorId") Long authorId);

    boolean existsAuthorByAuthorIdAndSubscriptionsIn(Long authorId, Iterable<User> users);
}
