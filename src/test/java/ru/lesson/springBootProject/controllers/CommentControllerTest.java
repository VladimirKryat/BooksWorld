package ru.lesson.springBootProject.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/truncate-all.sql","/create-user-before.sql","/add_comments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/truncate-all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "VladimirKryat")

//указываем проперти для тестов
@TestPropertySource("/application-test.properties")
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentController commentController;

    //проверяем что отображается username (VladimirKryat)  в навбаре с помощью xPath
    @Test
    public void commentPageTest() throws Exception{
        this.mockMvc.perform(get("/comment"))
                .andDo(print())
                .andExpect(authenticated())
                //данный xpath получен с помощью chrome->показать код -> copy->xPath
                .andExpect(xpath("//*[@id='navbarSupportedContent']/div/a").string("VladimirKryat"));
    //SAXParseException говорит о том, что наш код не соответствует xPath (причиной может быть теги которые не закрыты)
    }

    //проверяем количество выводимых комментов созданных скриптом
    @Test
    public void commentListTest() throws Exception{
        this.mockMvc.perform(get("/comment"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='idCardColumns']/div").nodeCount(4));
    }

    //проверяем количество карт комментариев созданых согласно sql скрипта
    @Test
    public void myCommentListTest() throws Exception{
        this.mockMvc.perform(get("/comment/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='idCardColumns']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='idCardColumns']/div[@data-id=1]").exists())
                .andExpect(xpath("//div[@id='idCardColumns']/div[@data-id=4]").exists());
    }

    //отправляем запрос на сохранение коментария и проверяем его отображение в списке в ответе
    //@Test
    public void addCommentWithFile() throws Exception{
        MockHttpServletRequestBuilder multipart = multipart("/comment")
                .file("file","123".getBytes(StandardCharsets.UTF_8))
                .param("stars","3")
                .param("text","new comment from test method")
                .with(csrf());
        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='idCardColumns']/div").nodeCount(5))
                .andExpect(xpath("//div[@id='idCardColumns']/div[@data-id=5]").exists())
                .andExpect(xpath("//div[@id='idCardColumns']/div[@data-id=5]/div/h5/a").string(containsString("VladimirKryat")))
                .andExpect(xpath("//div[@id='idCardColumns']/div[@data-id=5]/div/p").string(containsString("new comment from test method")));
    }
}
