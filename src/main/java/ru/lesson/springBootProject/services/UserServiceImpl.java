package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lesson.springBootProject.exceptions.ActivationServiceException;
import ru.lesson.springBootProject.exceptions.AuthorServiceException;
import ru.lesson.springBootProject.exceptions.UserServiceException;
import ru.lesson.springBootProject.exceptions.UsernameNotUniqueException;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.UserRepository;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivationService activationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorService authorService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return new UserDetailsImpl(userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException("User not found")));
    }

    @Override
    public User addUser(User user) throws UserServiceException{
        if (!UserServiceUtils.isValidUserdata(user)){
            throw new UserServiceException(UserServiceUtils.messageForInvalidUserdata(user));
        }
        if (existsByUsername(user.getUsername())){
           throw new UsernameNotUniqueException("Username exist");
        }
        user.setState(State.UNVERIFIED);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);        //сохраняем нового пользователя, и получаем его объект с заполненым id
        activationService.newActivation(result);        //создаём и отправляем активационный код на почту
        return result;
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
        try {
            User user = activationService.activate(code);
            user.setState(State.ACTIVE);
            userRepository.save(user);
        } catch (ActivationServiceException exception){
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public User change(User oldUser, User newUser) {
        boolean isEmailChanged = false;
        //если введён username и он отличен от существующего
        if (newUser.getUsername()!=null&&
                !newUser.getUsername().strip().isEmpty()&&
                !newUser.getUsername().equals(oldUser.getUsername())){
            oldUser.setUsername(newUser.getUsername());
        }
        if (newUser.getPassword()!=null&&
                !newUser.getPassword().strip().isEmpty()){
            oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        //если введён email и он отличен от существующего
        if (newUser.getEmail()!=null&&
                !newUser.getEmail().strip().isEmpty()&&
                !newUser.getEmail().equals(oldUser.getEmail())){
            oldUser.setEmail(newUser.getEmail());
            oldUser.setState(State.UNVERIFIED);
            isEmailChanged=true;
        }
        User result = userRepository.save(oldUser);
        if (isEmailChanged) {
            new Thread(() -> activationService.newActivation(result)).run();
        }

        return result;
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean checkPasswordConfirm(String password, String passwordConfirm){
        return (!passwordConfirm.isEmpty() && passwordConfirm.equals(password));
    }

    @Transactional
    @Override
    public User getSubscriptions(User user) throws UserServiceException{
        User result = userRepository.findByUsername(user.getUsername()).orElseThrow(()->new UserServiceException("Invalid user data"));
        result.getSubscriptions().size();
        return result;
    }

//    добавляем подписку и в set у автора и у пользователя, для сохранения синхронизации
    @Transactional
    @Override
    public User addSubscriptions(User user, Author author)throws UserServiceException{
        User userResult = userRepository.findById(user.getUserId()).orElseThrow(()->new UserServiceException("Invalid user data"));
        try{
            Author authorResult = authorService.getSubscriptions(author);
            if (!userResult.getSubscriptions().contains(authorResult)){
                userResult.getSubscriptions().add(authorResult);
                authorResult.getSubscriptions().add(userResult);
                userResult = userRepository.save(userResult);
            }
            return userResult;
        } catch (AuthorServiceException authorServiceException){
            throw new UserServiceException(authorServiceException);
        }
    }
    @Transactional
    @Override
    public User removeSubscriptions(User user, Author author)throws UserServiceException{
        User userResult = userRepository.findById(user.getUserId()).orElseThrow(()->new UserServiceException("Invalid user data"));
        try{
            Author authorResult = authorService.getSubscriptions(author);
            if (userResult.getSubscriptions().contains(authorResult)){
                userResult.getSubscriptions().remove(authorResult);
                authorResult.getSubscriptions().remove(userResult);
                userResult = userRepository.save(userResult);
            }
            return userResult;
        } catch (AuthorServiceException authorServiceException){
            throw new UserServiceException(authorServiceException);
        }
    }

}
