package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.BookRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Book save(Book book){
        return bookRepository.save(book);
    }
    @Override
    public List<Book>findAll(){
        return bookRepository.findAll();
    }

    @Override
    public Page<BookDto> findAll(Long userId, Pageable pageable){
        return bookRepository.findAll(userId,pageable);
    }

    @Override
    public Book like(Book book, User user){
        if (book.getLikes().contains(user)){
            book.getLikes().remove(user);
        }else{
            book.getLikes().add(user);
        }
        return bookRepository.save(book);
    }
}
