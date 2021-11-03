package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lesson.springBootProject.exceptions.AuthorServiceException;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.repositories.AuthorRepository;
import ru.lesson.springBootProject.repositories.BookRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Author findByName(String name)throws AuthorServiceException{
        return authorRepository.findAuthorByName(name).orElseThrow(()->new AuthorServiceException("Author not found"));
    }

    @Override
    public Author save(Author author){
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author findByIdWithBooks(Author author) throws AuthorServiceException {
        Author result = authorRepository.findById(author.getAuthorId()).orElseThrow(()->new AuthorServiceException("Author not found by id"));
        result.getBooks();
        return result;
    }

    @Override
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

}
