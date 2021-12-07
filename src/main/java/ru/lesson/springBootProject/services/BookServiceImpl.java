package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.GenreBook;
import ru.lesson.springBootProject.models.GenreName;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.BookRepository;
import ru.lesson.springBootProject.repositories.GenreRepository;
import ru.lesson.springBootProject.services.filters.FilterBook;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    EntityManager entityManager;
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
        Iterator<GenreBook> iteratorGenres = book.getGenres().iterator();
        while (iteratorGenres.hasNext()){
            GenreBook nextGenreBook = iteratorGenres.next();
            if (!newGenres.contains(nextGenreBook.getGenre())){
                iteratorGenres.remove();
            }else{
                newGenres.remove(nextGenreBook);
            }
        }
        //добавляем новые жанры в список старых, при этом приводим из GenreName->Genre
        for (GenreName newGenreName : newGenres){
            book.getGenres().add(GenreBook.builder().genre(newGenreName).book(book).build());
        }

    }

    @Override
    public void delete(Long bookId) {
        Optional<Book> candidateBook = bookRepository.findById(bookId);
        if (candidateBook.isEmpty()) return;
        bookRepository.delete(candidateBook.get());
    }

    //метод получения списка книг.
    @Override
    public Page<BookDto> findAllWithFilter(Long userId, Pageable pageable, FilterBook filterBook) {
        Page<BookDto> page =null;
        if (filterBook !=null){
            //если задана сортировка по количеству лайков, то меняем атрибут сортировки у Pageable.
            //если сортировка не задана, но filterBook!=null, что возможно только в случае если задан жанр для фильтрации, то нужно установить сортировку по умолчанию, чтобы избежать ошибок
            if (filterBook.getSortedByLikes()!=null&&filterBook.getSortedByLikes()!= FilterBook.SortedByLikes.NONE){
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), JpaSort.unsafe(Sort.Direction.valueOf(filterBook.getSortedByLikes().name()), "count(lk)"));

            }else{
            //устанавливаем сортировку по умолчанию
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "bookId"));
            }
            if (filterBook.getGenreName()!=null) {
                page = bookRepository.findAllWithParam(userId, pageable, filterBook.getGenreName());
            }
        }
        //page==null когда не задан Жанр для фильтрации или FilterBook
        if (page ==null) page= bookRepository.findAll(userId,pageable);
        //если текущая страница дальше максимальной, то рекурсивно запрашиваем последнюю возможную страницу
        if (page.getNumber()>page.getTotalPages())
        {
            pageable= PageRequest.of(page.getTotalPages()-1,pageable.getPageSize(),pageable.getSort());
            page = findAllWithFilter(userId,pageable, filterBook);
        }
        return page;
    }
}
