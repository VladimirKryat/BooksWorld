package ru.lesson.springBootProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.exceptions.ActivationServiceException;
import ru.lesson.springBootProject.models.Activation;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.ActivationRepository;

import java.util.UUID;

@Service
public class ActivationServiceImpl implements ActivationService {
    @Autowired
    private MailSender mailSender;
    @Autowired
    private ActivationRepository activationRepository;
    @Override
    public void newActivation(User user) {
        Activation activation = new Activation();
        activation.setUser(user);
        activation.setActivationCode(UUID.randomUUID().toString());
        activation=activationRepository.save(activation);      //сохраняем код активации для нового пользователя
        if (user.getEmail()!=null && !user.getEmail().isEmpty()){
            String message =String.format(
                    "Hello, %s! \n" +
                            "Welcome to SpringBootProject. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    activation.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Confirm email", message);
        }
    }

    @Override
    public User activate(String code) throws ActivationServiceException{
        Activation activation = activationRepository.findByActivationCode(code)
                .orElseThrow(()->new ActivationServiceException("Activation code not found"));
        User result=activation.getUser();
        activationRepository.delete(activation);
        return result;
    }

}
