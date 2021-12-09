package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.springBootProject.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("select u from User u left join fetch u.subscriptions where u.userId=:userId")
    Optional<User> findWithSubscriptions(@Param("userId") Long userId);


}
