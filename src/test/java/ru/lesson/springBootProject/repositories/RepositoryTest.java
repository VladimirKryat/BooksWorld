package ru.lesson.springBootProject.repositories;

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
import ru.lesson.springBootProject.dto.BookDto;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.User;

import java.util.Collections;
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


    @Test
    public void findAllByAuthors() {
        Pageable pageable = PageRequest.of(0,6);
        Author author = authorRepository.findById(2L).get();
        Page<BookDto> result = bookRepository.findAllByAuthors(2L, pageable, author);
        Assert.assertTrue(result.getContent().size()==2);
    }

    @Test
    public void existsAuthorByAuthorIdAndSubscriptionsIn() {
        User user = userRepository.findById(1L).get();
        Assert.assertTrue(authorRepository.existsAuthorByAuthorIdAndSubscriptionsIn(2L, Collections.singleton(user)));
    }
}