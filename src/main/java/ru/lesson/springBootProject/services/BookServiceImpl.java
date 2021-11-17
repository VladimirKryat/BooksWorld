package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.repositories.BookRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EntityManager em;
    @Override
    public Book save(Book book){
        return bookRepository.save(book);
    }
    @Override
    public List<Book>findAll(){
        return bookRepository.findAll();
    }

    @Override
    public List<BookDto> findAll(Long userId){
        return bookRepository.findAll(userId);
    }
}
