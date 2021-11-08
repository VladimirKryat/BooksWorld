package ru.lesson.springBootProject.services;

import org.springframework.transaction.annotation.Transactional;
import ru.lesson.springBootProject.exceptions.AuthorServiceException;
import ru.lesson.springBootProject.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author findByName(String name)throws AuthorServiceException;
    Author save(Author author);
    @Transactional
    Author findByIdWithBooks(Author author)throws AuthorServiceException;
    List<Author> findAll();

    boolean checkUnique(Author author);

    Optional<Author> findByNameAndBirthday(Author author);

    @Transactional
    Author getSubscriptions(Author author) throws AuthorServiceException;
}
