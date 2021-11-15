package ru.lesson.springBootProject.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lesson.springBootProject.models.Author;

import java.time.LocalDate;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
//указываем проперти для тестов
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;
    @Test
    public void save() {
        Author author = new Author();
        author.setName("Ф.М. Достоевский");
        author.setBirthday(LocalDate.of(1821,11,11));
        author.setDateOfDeath(LocalDate.of(1881,3,9));
        author.setBiography("Федор Михайлович Достоевский — значительная личность не только в русской, но и мировой литературе. Великий мыслитель XIX столетия оставил после себя много потрясающих произведений. Он был новатором в направлении русского реализма, однако его заслуги в этой области при жизни мало кто признавал. И только следующее поколение признало Федора Достоевского одним из лучшим романистов в мире. За свою короткую непростую жизнь писатель сумел создать великолепное творческое наследие и повлиять на творчество других литераторов, среди которых были и лауреаты Нобелевской премии.");
        Author resultSave = authorService.save(author);
        Assert.assertTrue(resultSave.getAuthorId()!=null);
    }
}