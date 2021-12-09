package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lesson.springBootProject.dto.AuthorInfoDto;
import ru.lesson.springBootProject.exceptions.AuthorServiceException;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.AuthorRepository;
import ru.lesson.springBootProject.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

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
        //если автор с таким именем и датой рождения существует, сохраняем данные на его место
        Optional<Author> pretendentAuthor = authorRepository.findAuthorByNameAndBirthday(author.getName(),author.getBirthday());
        if (pretendentAuthor.isPresent()){
            author.setAuthorId(pretendentAuthor.get().getAuthorId());
            return authorRepository.save(author);
        }
        else {
            return authorRepository.save(author);
        }
    }

    @Transactional
    @Override
    public Author findByIdWithBooks(Long authorId) throws AuthorServiceException {
        Author result = authorRepository.findById(authorId).orElseThrow(()->new AuthorServiceException("Author not found by id"));
        result.getBooks().isEmpty();
        return result;
    }

    @Override
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    @Override
    public boolean checkUnique(Author author){
        return authorRepository.existsAuthorByNameAndBirthday(author.getName(),author.getBirthday());
    }
    @Override
    public Optional<Author> findByNameAndBirthday(Author author){
        return authorRepository.findAuthorByNameAndBirthday(author.getName(),author.getBirthday());
    }


    @Transactional
    @Override
    public Author getSubscriptions(Author author) throws AuthorServiceException{
        Author result = authorRepository.findById(author.getAuthorId()).orElseThrow(() -> new AuthorServiceException("Author not found by Id"));
        result.getSubscriptions().isEmpty();
        return result;
    }

    @Override
    public Long countSubscribers(Long authorId){
        return authorRepository.countSubscribers(authorId);
    }

    @Override
    public Long countBook(Long authorId){
        return authorRepository.countBook(authorId);
    }

    @Override
    public Author findById(Long authorId) throws AuthorServiceException{
        return authorRepository.findById(authorId).orElseThrow(()-> new AuthorServiceException("Author not found by Id"));
    }

    @Override
    public boolean existAuthorWithSubscribers(Long authorId, Iterable<User> users){
        return authorRepository.existsAuthorByAuthorIdAndSubscriptionsIn(authorId,users);
    }

    @Override
    public Author findAllComments(Author author) {
        return null;
    }

    @Override
    public AuthorInfoDto getAuthorInfoDto(Long userId,Long authorId) {
        return authorRepository.findAuthorInfo(userId, authorId).get();
    }

}
