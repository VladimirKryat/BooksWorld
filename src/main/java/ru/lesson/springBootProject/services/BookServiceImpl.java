package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.Genre;
import ru.lesson.springBootProject.models.GenreName;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.BookRepository;
import ru.lesson.springBootProject.repositories.GenreRepository;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
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

    @Override
    public void changeGenre(Book book, String[] genresName){

        //приводим массив String к Set<GenreName>(набор допустимых жанров согласно enum)
        Set<GenreName> newGenres = Arrays.stream(genresName).map(genreName->GenreName.valueOf(genreName)).collect(Collectors.toSet());
        //нужно пройти по множеству старых жанров(которые сейчас в бд) и убрать те которых нет среди вновь выбранных жанров
        //при этом если среди старых жанров есть вновь выбранные, то нужно удалить их из новых, чтобы не было попытки дважды включить в бд одну и ту же запись
        Iterator<Genre> iteratorGenres = book.getGenres().iterator();
        while (iteratorGenres.hasNext()){
            Genre nextGenre = iteratorGenres.next();
            if (!newGenres.contains(nextGenre.getGenre())){
                iteratorGenres.remove();
            }else{
                newGenres.remove(nextGenre);
            }
        }
        //добавляем новые жанры в список старых, при этом приводим из GenreName->Genre
        for (GenreName newGenreName : newGenres){
            book.getGenres().add(Genre.builder().genre(newGenreName).book(book).build());
        }

    }

    @Override
    public void delete(Long bookId) {
        Optional<Book> candidateBook = bookRepository.findById(bookId);
        if (candidateBook.isEmpty()) return;
        bookRepository.delete(candidateBook.get());
    }
}
