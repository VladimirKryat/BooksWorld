package ru.lesson.springBootProject.repositories;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lesson.springBootProject.dto.AuthorInfoDto;
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/truncate-all.sql","/copyAllFromDev.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/truncate-all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentAuthorRepository commentAuthorRepository;


    @Test
    public void findAllByAuthors() {
        Pageable pageable = PageRequest.of(0,1);
        Author author = authorRepository.findById(2L).get();
        Page<BookDto> result = bookRepository.findAllByAuthors(2L, author.getAuthorId(), pageable );
        Assert.assertTrue(result.getContent().size()==2);
    }

    @Test
    public void existsAuthorByAuthorIdAndSubscriptionsIn() {
        User user = userRepository.findById(1L).get();
        Assert.assertTrue(authorRepository.existsAuthorByAuthorIdAndSubscriptionsIn(2L, Collections.singleton(user)));
    }
    @Test
    public void findAll(){
        List<Author> authors = authorRepository.findAll();
        List<Book> books = bookRepository.findAll();
        Pageable pageable = PageRequest.of(0,6);
        Page<BookDto> result = bookRepository.findAll(2L, pageable);
        Page<CommentAuthor> allComment = commentAuthorRepository.findAllByAuthor_AuthorId(2L, pageable);
        Assert.assertNotNull(result.getContent());
        Assert.assertTrue(allComment.getContent().size()==2);
    }
    @Test
    public void findAuthorInfo(){
        Optional<AuthorInfoDto> author = authorRepository.findAuthorInfo(1L, 2L);
        Assert.assertNotNull(author.orElse(null));
        Assert.assertTrue(author.get().getCountSubscribers()==3);

    }
}