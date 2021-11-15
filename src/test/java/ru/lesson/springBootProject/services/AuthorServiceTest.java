package ru.lesson.springBootProject.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.repositories.AuthorRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringRunner.class)
//указываем проперти для тестов
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;
    @MockBean
    private AuthorRepository authorRepository;
//    проверяем, что при сохранении существующего автора, изменения сохраняются на его место (по id)
    @Test
    public void save() {
        Author author = new Author();
        author.setName("Ф.М. Достоевский");
        author.setBirthday(LocalDate.of(1821,11,11));
        author.setDateOfDeath(LocalDate.of(1881,3,9));
        author.setBiography("Федор Михайлович Достоевский — значительная личность не только в русской, но и мировой литературе. Великий мыслитель XIX столетия оставил после себя много потрясающих произведений. Он был новатором в направлении русского реализма, однако его заслуги в этой области при жизни мало кто признавал. И только следующее поколение признало Федора Достоевского одним из лучшим романистов в мире. За свою короткую непростую жизнь писатель сумел создать великолепное творческое наследие и повлиять на творчество других литераторов, среди которых были и лауреаты Нобелевской премии.");

        //создаём объект для ответа на вызов метода findAuthorByNameAndBirthday
        Author resultFind = new Author();
        resultFind.setAuthorId(3L);
        resultFind.setName("Ф.М. Достоевский");
        resultFind.setBirthday(LocalDate.of(1821,11,11));

        //устанавливаем результат вызова mock с соответствующими параметрами
        when(authorRepository.findAuthorByNameAndBirthday("Ф.М. Достоевский",LocalDate.of(1821,11,11)))
                .thenReturn(Optional.of(resultFind));
        //вызываем тестируемый метод
        Author resultSave = authorService.save(author);

        verify(authorRepository, times(1))
                .findAuthorByNameAndBirthday(anyString(),any());
        //проверяем количество обращенийб также объект который сохраняем не должен быть объектом resultFind
        verify(authorRepository, times(1)).save(eq(author));

        //проверяем id у найденного объекта в mock и у входного объекта
        assertEquals(author.getAuthorId(),resultFind.getAuthorId());

    }


    @Test
    public void saveNew() {
        Author author = new Author();
        author.setName("Ф.М. Достоевский");
        author.setBirthday(LocalDate.of(1821,11,11));
        author.setDateOfDeath(LocalDate.of(1881,3,9));
        author.setBiography("Федор Михайлович Достоевский — значительная личность не только в русской, но и мировой литературе. Великий мыслитель XIX столетия оставил после себя много потрясающих произведений. Он был новатором в направлении русского реализма, однако его заслуги в этой области при жизни мало кто признавал. И только следующее поколение признало Федора Достоевского одним из лучшим романистов в мире. За свою короткую непростую жизнь писатель сумел создать великолепное творческое наследие и повлиять на творчество других литераторов, среди которых были и лауреаты Нобелевской премии.");

        //вызываем тестируемый метод
        Author resultSave = authorService.save(author);

        verify(authorRepository, times(1))
                .findAuthorByNameAndBirthday(anyString(),any());
        //проверяем количество обращений
        verify(authorRepository, times(1)).save(eq(author));

        //проверяем id у найденного объекта в mock и у входного объекта
        assertEquals(author.getAuthorId(),null);

    }
}