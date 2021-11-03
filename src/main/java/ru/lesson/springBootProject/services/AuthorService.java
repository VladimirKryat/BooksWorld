package ru.lesson.springBootProject.services;

import org.springframework.transaction.annotation.Transactional;
import ru.lesson.springBootProject.exceptions.AuthorServiceException;
import ru.lesson.springBootProject.models.Author;

import java.util.List;

public interface AuthorService {
    Author findByName(String name)throws AuthorServiceException;
    Author save(Author author);
    @Transactional
    Author findByIdWithBooks(Author author)throws AuthorServiceException;
    List<Author> findAll();
}
