package ru.lesson.springBootProject.services;

import ru.lesson.springBootProject.models.User;

public interface ActivationService {
    void newActivation(User user);
    User activate(String code);
}
