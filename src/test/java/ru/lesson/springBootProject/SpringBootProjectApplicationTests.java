package ru.lesson.springBootProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//указываем проперти для тестов
@TestPropertySource("/application-test.properties")
public class SpringBootProjectApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    //    тест проверяет, что можно обратиться к корню приложения и в ответе будет содержаться соответствующие строковые значения
    @Test
    public void contextLoads() throws Exception{
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("This is a studies project of Spring Boot.")))
                .andExpect(content().string(containsString("Log In")));


    }

    @Test
    public void loginTest() throws Exception{
        this.mockMvc.perform(get("/comment"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
    @Sql(value = {"/truncate-all.sql","/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/truncate-all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void loginCorrectTest() throws Exception{
        this.mockMvc.perform(formLogin().user("VladimirKryat").password("1997"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comment"));


    }
    //составляем запрос самостоятельно для наглядности
    @Test
    public void loginBadTest() throws Exception{
        this.mockMvc.perform(post("/login")
                        .param("user","KryatVladimir")
                        .param("password", "5997"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
