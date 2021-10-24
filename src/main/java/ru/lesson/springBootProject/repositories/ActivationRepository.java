package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lesson.springBootProject.models.Activation;
import ru.lesson.springBootProject.models.User;

import java.util.Optional;

public interface ActivationRepository extends JpaRepository<Activation,Long> {
    Optional<Activation> findByActivationCode(String code);
}
