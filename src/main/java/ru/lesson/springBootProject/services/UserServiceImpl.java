package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.exceptions.UserServiceException;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.UserRepository;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSender mailSender;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findByUsername(s).orElseThrow(()->new IllegalArgumentException("User not found by UserDetailsServiceImpl")));
    }

    @Override
    public User addUser(User user) throws UserServiceException{
        if (existsByUsername(user.getUsername())){
           throw new UserServiceException("Username exist");
        }
        user.setState(State.UNVERIFIED);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());


        if (user.getEmail()!=null && !user.getEmail().isEmpty()){
            String message =String.format(
                    "Hello, %s! \n" +
                            "Welcome to SpringBootProject. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Confirm email", message);
        }
        return userRepository.save(user);
    }
    @Override
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public boolean activateUser(String code) {
        Optional<User> candidateUser = userRepository.findByActivationCode(code);
        if (!candidateUser.isPresent()){
            return false;
        }
        User user = candidateUser.get();
        user.setState(State.ACTIVE);
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }


}
