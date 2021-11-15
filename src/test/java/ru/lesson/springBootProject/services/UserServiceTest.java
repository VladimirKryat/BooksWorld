package ru.lesson.springBootProject.services;

import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lesson.springBootProject.exceptions.ActivationServiceException;
import ru.lesson.springBootProject.exceptions.UserServiceException;
import ru.lesson.springBootProject.exceptions.UsernameNotUniqueException;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.UserRepository;

import java.util.Locale;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ActivationService activationService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Rule
    public ExpectedException thrown = ExpectedException.none();     //необходимо для обработки exception.message в JUnit4


    @Test
    public void addUser() {
        User user = new User();
        user.setUsername("NewUserInTest");
        user.setPassword("1234");
        user.setEmail("saxam55737@erpipo.com");
        when(userRepository.save(any())).thenReturn(user.toBuilder().userId(1L).build());
        User resultAdd = userService.addUser(user);
        Assert.assertTrue(user.getState()==State.UNVERIFIED);
        Assert.assertTrue(user.getRoles().equals(Set.of(Role.USER)));
        verify(activationService, times(1)).newActivation(any(User.class));


    }
//    данный метод проверяет вызывается ли активация пользователя и его сохранение если был введён не уникальный username
    @Test(expected = UsernameNotUniqueException.class)
    public void addUserFailureExistName(){
        User user = new User();
        user.setUsername("VladimirKryat");
        user.setPassword("1234");
        user.setEmail("saxam55737@erpipo.com");
        doReturn(true)
                .when(userRepository)
                .existsByUsername("VladimirKryat");
        User resultAdd = userService.addUser(user);
        verify(activationService, times(0)).newActivation(any());
        verify(userRepository, times(0)).save(any());

    }
    //    данный метод проверяет работу метода сохранения пользователя при некорректных данных
    @Test
    public void addUserInvalidUserdata(){
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setEmail("");
        thrown.expect(UserServiceException.class);
        thrown.expectMessage("username");
        thrown.expectMessage("password");
        thrown.expectMessage("email");
        userService.addUser(user);
        verify(activationService, times(0)).newActivation(any());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void activateUser() {
        String code = "Activate code";
        User user = User.builder()
                .userId(10L)
                .username("TestUser")
                .state(State.UNVERIFIED)
                .build();
        when(activationService.activate(code)).thenReturn(user);
        boolean result = userService.activateUser(code);
        assertTrue(result);
        assertEquals("Check state user",State.ACTIVE,user.getState());
        verify(userRepository,times(1)).save(user);
        verify(activationService,times(1)).activate(anyString());
    }

    @Test
    public void activateUserFailure() {
        String code = "Activate code";
        User user = User.builder()
                .userId(10L)
                .username("TestUser")
                .state(State.UNVERIFIED)
                .build();
        when(activationService.activate(code)).thenThrow(ActivationServiceException.class);
        boolean result = userService.activateUser(code);
        assertFalse(result);
        assertEquals("Check state user",State.UNVERIFIED,user.getState());
        verify(userRepository,times(0)).save(user);
        verify(activationService,times(1)).activate(ArgumentMatchers.contains("Activate code"));
    }
}