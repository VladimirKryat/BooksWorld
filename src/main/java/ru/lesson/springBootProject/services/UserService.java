package ru.lesson.springBootProject.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lesson.springBootProject.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public User addUser(User user);
    public boolean existsByUsername(String username);
    public List<User> findAll();
    public User save(User user);

}
