package ru.lesson.springBootProject.services;

import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.models.Activation;
import ru.lesson.springBootProject.models.User;

public interface ActivationService {
    void newActivation(User user);
    User activate(String code);
}
