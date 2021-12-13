package ru.lesson.springBootProject.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.CommentBook;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.services.filters.FilterBook;

import java.util.List;

public interface BookService {
    Book save(Book book);

    Page<BookDto> findAll(Long userId, Pageable pageable);

    Book like(Book book, User user);

    void changeGenre(Book book, String[] genresName);

    void delete(Long bookId);

    Page<BookDto> findAllWithFilter(Long userId, Pageable pageable, FilterBook filterBook);

    Page<BookDto> findAllByAuthors(Long userId,Long authorId, Pageable pageable);

    Book findById(Long ownerId);

    Page<BookDto> findAllLikes(Long userId, Pageable pageable);

    Page<CommentBook> findAllComments(Long bookId, Pageable pageable);

     BookDto findBookDtoById(Long userId, Long bookId);
}
