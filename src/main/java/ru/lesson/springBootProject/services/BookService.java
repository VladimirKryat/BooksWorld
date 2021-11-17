package ru.lesson.springBootProject.services;

import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();

    List<BookDto> findAll(Long userId);
}
