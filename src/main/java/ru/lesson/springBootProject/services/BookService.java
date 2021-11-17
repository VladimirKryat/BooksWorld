package ru.lesson.springBootProject.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.User;

import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();

    Page<BookDto> findAll(Long userId, Pageable pageable);

    Book like(Book book, User user);
}
